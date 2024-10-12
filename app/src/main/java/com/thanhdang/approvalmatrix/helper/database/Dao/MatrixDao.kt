package com.thanhdang.approvalmatrix.helper.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix

@Dao
interface MatrixDao {
    @Insert
    suspend fun insert(matrix: ApprovalMatrix)

    @Query("SELECT * FROM ApprovalMatrix")
    suspend fun getAllMatrix(): List<ApprovalMatrix>

    @Query("SELECT * FROM ApprovalMatrix WHERE matrixType = :type")
    suspend fun getMatrixByType(type: String): List<ApprovalMatrix>
}