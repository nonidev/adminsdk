package com.test.adminconsole.viewholder

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.adminconsole.model.UserDetailModel

import kotlinx.android.synthetic.main.row_user.view.*

/**
 * This is the viewHolder class used by UserDetailAdapter for showing view with ResourceGroup or Resource data
 *
 * @param itemView view in which data is set from GroupOrResourceModel
 * @param itemClickListener GroupOrResourcePathClickListener which is used to handle click on ResourceGroup or Resource item
 */
class UserDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val TAG = javaClass.simpleName

    private var model: UserDetailModel? = null

    internal fun bind(model: UserDetailModel) {
        this.model = model

        itemView?.apply {

            tvName.text = model.name
            Glide.with(itemView.ivUser.context).load(model.image)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemView.ivUser)
        }
    }
}