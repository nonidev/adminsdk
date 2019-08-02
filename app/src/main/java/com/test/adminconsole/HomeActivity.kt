package com.test.adminconsole

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import com.test.adminconsole.R.id.rvUsers
import com.test.adminconsole.R.id.swipeRefresh
import com.test.adminconsole.adapter.UserDetailAdapter
import com.test.adminconsole.model.UserDetailModel
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class HomeActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.switchbtn -> {
                isSecond = true
                fetchSiteResourceGroupData()
            }
            R.id.floatingBtn ->callQuestionActivity()
        }
    }


    internal fun callQuestionActivity(){
        startActivity(Intent(this,QuestionSnap::class.java))
    }

    private var userDetailAdapter: UserDetailAdapter? = null
    private var isSecond : Boolean = false
    internal var userList = arrayListOf<UserDetailModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpRecyclerViewsWithAdapters()
        fetchSiteResourceGroupData()

        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(R.string.app_name)
        swipeRefresh.setOnRefreshListener(this)
        switchbtn.setOnClickListener(this)
        floatingBtn.setOnClickListener(this)

    }

    override fun onRefresh() {
        fetchSiteResourceGroupData()
    }

    private fun fetchSiteResourceGroupData() {
        swipeRefresh.isRefreshing = true
        var url:String? = null
        if(isSecond){
            url = "http://tarahah.com/freecent/application/v2/getUserData.php"
        }else{
            url = "http://tarahah.com/business/getUserData.php";
        }



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
                    Toast.makeText(applicationContext,e.toString(),Toast.LENGTH_SHORT).show()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val myResponse = response.body()!!.string()
                runOnUiThread {
                    swipeRefresh.isRefreshing = false

                    callAddDataToResourceGroupsList(JSONArray(myResponse))
                }

            }
        })
    }

    private fun setUpRecyclerViewsWithAdapters() {
        userDetailAdapter = UserDetailAdapter(activity = this, userDetailModel = userList)

        rvUsers.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = userDetailAdapter
        }

    }

    private fun callAddDataToResourceGroupsList(jsonArrayGroups: JSONArray?) {
        userList.clear()
        Toast.makeText(this,"Total-"+jsonArrayGroups!!.length(),Toast.LENGTH_SHORT).show();
        if (jsonArrayGroups != null)
            (0 until jsonArrayGroups.length())
                    .map { jsonArrayGroups[it] as JSONObject }
                    .forEach {
                        addDataToResourceGroupsList(jsonObject = it)
                    }
            userDetailAdapter!!.notifyDataSetChanged()
    }


    private fun addDataToResourceGroupsList(jsonObject: JSONObject) {
        userList.add(element = UserDetailModel(jsonObject.optString("displayName"),jsonObject.optString("photoUrl")))
    }
}
