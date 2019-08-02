package com.test.adminconsole.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.adminconsole.R
import com.test.adminconsole.model.UserDetailModel
import com.test.adminconsole.viewholder.UserDetailViewHolder


/**
 * This is the adapter class used in GroupOrResourceActivity for showing ResourceGroups and Resources list
 *
 * @param activity instance of BaseActivity
 * @param groupOrResourceModelsList list holding all ResourceGroups and Resources list
 */
class UserDetailAdapter(private val activity: AppCompatActivity, private val userDetailModel: ArrayList<UserDetailModel>) : RecyclerView.Adapter<UserDetailViewHolder>() {
    private val TAG = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailViewHolder =
            UserDetailViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false))

    override fun onBindViewHolder(holder: UserDetailViewHolder, position: Int) =
            holder.bind(model = userDetailModel[position])

    override fun getItemCount(): Int = userDetailModel.size
}
