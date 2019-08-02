package com.test.adminconsole.adapter

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.adminconsole.R
import com.test.adminconsole.model.PackageListModel
import com.test.adminconsole.model.UserDetailModel
import com.test.adminconsole.viewholder.PackageListViewHolder
import com.test.adminconsole.viewholder.UserDetailViewHolder


/**
 * This is the adapter class used in GroupOrResourceActivity for showing ResourceGroups and Resources list
 *
 * @param activity instance of BaseActivity
 * @param groupOrResourceModelsList list holding all ResourceGroups and Resources list
 */
class PackageListAdapter(private val activity: AppCompatActivity, private val packageListModel: ArrayList<PackageListModel>) : RecyclerView.Adapter<PackageListViewHolder>() {
    private val TAG = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageListViewHolder =
            PackageListViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_packages, parent, false))

    override fun onBindViewHolder(holder: PackageListViewHolder, position: Int) =
            holder.bind(model = packageListModel[position])

    override fun getItemCount(): Int = packageListModel.size
}
