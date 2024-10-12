package com.thanhdang.approvalmatrix.data.local

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
)
