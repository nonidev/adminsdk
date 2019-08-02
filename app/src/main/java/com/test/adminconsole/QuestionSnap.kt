package com.test.adminconsole

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.widget.Toast
import com.test.adminconsole.R.id.swipeRefresh
import kotlinx.android.synthetic.main.activity_question_snap.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class QuestionSnap : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        fetchRandomQuestion()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_snap)


        onRefresh()
        swipeRefresh.setOnRefreshListener(this)
    }


    private fun fetchRandomQuestion() {
        swipeRefresh.isRefreshing = true
        var url:String? = "http://dev.examaura.com/api/v1/test-series/random-question.php"

        val request = Request.Builder()
                .url(url)
                .get()
                .build()

        val client = OkHttpClient()

        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    swipeRefresh.isRefreshing = false
                    Toast.makeText(applicationContext,e.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val myResponse = response.body()!!.string()
                runOnUiThread {
                    swipeRefresh.isRefreshing = false
                    var jsonObject : JSONObject = JSONObject(myResponse)

                    tvquestion.text = jsonObject.getString("question")
                    var jsonArray : JSONArray = jsonObject.getJSONArray("options");

                    option1.text = jsonArray.getJSONObject(0).getString("option_name")
                    option2.text = jsonArray.getJSONObject(1).getString("option_name")
                    option3.text = jsonArray.getJSONObject(2).getString("option_name")
                    option4.text = jsonArray.getJSONObject(3).getString("option_name")


                    tvanswers.text = jsonArray.getJSONObject(0).getString("iscorrect")+","+
                            jsonArray.getJSONObject(1).getString("iscorrect")+","+
                            jsonArray.getJSONObject(2).getString("iscorrect")+","+
                            jsonArray.getJSONObject(3).getString("iscorrect")
                }

            }
        })
    }
}
