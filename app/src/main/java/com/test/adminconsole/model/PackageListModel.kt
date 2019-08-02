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
class PackageListModel(var app_id: String, var app_name: String,var app_package: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
            )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.apply {
            writeString(app_id)
            writeString(app_name)
            writeString(app_package)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<PackageListModel> {
        override fun createFromParcel(parcel: Parcel): PackageListModel =
                createFromParcel(parcel = parcel)

        override fun newArray(size: Int): Array<PackageListModel?> =
                arrayOfNulls(size = size)
    }
}