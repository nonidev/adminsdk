package com.test.adminconsole.model

import android.os.Parcel
import android.os.Parcelable

/**
 * This is the model class which stores ResourceGroup or Resource data
 *
 * @param isGroup true for ResourceGroupModel and false for ResourceModel
 * @param resourceGroupModel ResourceGroupModel which has ResourceGroup data
 * @param resourceModel ResourceModel which has Resource data
 */
class UserDetailModel(var name: String, var image: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
            )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.apply {
            writeString(name)
            writeString(image)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<UserDetailModel> {
        override fun createFromParcel(parcel: Parcel): UserDetailModel =
                createFromParcel(parcel = parcel)

        override fun newArray(size: Int): Array<UserDetailModel?> =
                arrayOfNulls(size = size)
    }
}