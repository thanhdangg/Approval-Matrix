package com.thanhdang.approvalmatrix.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ApprovalMatrix")
data class ApprovalMatrix(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val matrixName: String = "",
    val matrixType: String = "",
    val minimumApproval: Long = 0,
    val maximumApproval: Long = 0,
    val numberOfApproval: Int = 0
) : Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(matrixName)
        parcel.writeString(matrixType)
        parcel.writeLong(minimumApproval)
        parcel.writeLong(maximumApproval)
        parcel.writeInt(numberOfApproval)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApprovalMatrix> {
        override fun createFromParcel(parcel: android.os.Parcel): ApprovalMatrix {
            return ApprovalMatrix(parcel)
        }

        override fun newArray(size: Int): Array<ApprovalMatrix?> {
            return arrayOfNulls(size)
        }
    }
}
