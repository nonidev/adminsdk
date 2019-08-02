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
import com.test.adminconsole.adapter.PackageListAdapter
import com.test.adminconsole.adapter.UserDetailAdapter
import com.test.adminconsole.model.PackageListModel
import com.test.adminconsole.model.UserDetailModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.floatingBtn
import kotlinx.android.synthetic.main.activity_home.swipeRefresh
import kotlinx.android.synthetic.main.activity_packages.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class PackageActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.floatingBtn ->addPackage()
        }
    }

    internal fun addPackage(){

    }

    private var packageListAdapter: PackageListAdapter? = null
    internal var packageListModel = arrayListOf<PackageListModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_packages)
        setUpRecyclerViewsWithAdapters()
        fetchSiteResourceGroupData()

        swipeRefresh.setOnRefreshListener(this)
        floatingBtn.setOnClickListener(this)

    }

    override fun onRefresh() {
        fetchSiteResourceGroupData()
    }

    private fun fetchSiteResourceGroupData() {
        swipeRefresh.isRefreshing = true
        var url:String? = "http://tarahah.com/admin-sdk/api/getApps.php"

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
        packageListAdapter = PackageListAdapter(activity = this, packageListModel = packageListModel)

        rvPackages.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = packageListAdapter
        }

    }

    private fun callAddDataToResourceGroupsList(jsonArrayGroups: JSONArray?) {
        packageListModel.clear()
        Toast.makeText(this,"Total-"+jsonArrayGroups!!.length(),Toast.LENGTH_SHORT).show();
        if (jsonArrayGroups != null)
            (0 until jsonArrayGroups.length())
                    .map { jsonArrayGroups[it] as JSONObject }
                    .forEach {
                        addDataToResourceGroupsList(jsonObject = it)
                    }
            packageListAdapter!!.notifyDataSetChanged()
    }


    private fun addDataToResourceGroupsList(jsonObject: JSONObject) {
        packageListModel.add(element = PackageListModel(jsonObject.optString("app_id"),jsonObject.optString("app_name"),jsonObject.optString("app_package")))
    }
}
