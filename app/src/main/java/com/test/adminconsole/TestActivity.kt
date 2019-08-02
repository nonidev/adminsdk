package com.test.adminconsole

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adminsdk.listeners.AppClickListener
import com.adminsdk.listeners.OnRequestListener
import com.adminsdk.model.AppsModel
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity(), AppClickListener, OnRequestListener {
    override fun onInit() {
        Toast.makeText(this,"init",Toast.LENGTH_LONG).show()

    }

    override fun onError(responseCode: Int, message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()

    }

    override fun onSuccess(responseCode: Int, message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    override fun onAppClick(appsModel: AppsModel?) {
        Toast.makeText(this,appsModel!!.app_package,Toast.LENGTH_LONG).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        appLayout.initiate(this,this)
        appLayout.loadApps();
    }
}
