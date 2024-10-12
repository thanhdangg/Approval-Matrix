package com.thanhdang.approvalmatrix.helper.database.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix

@Dao
interface MatrixDao {
    @Insert
    suspend fun insert(matrix: ApprovalMatrix)

    @Query("SELECT * FROM ApprovalMatrix")
    suspend fun getAllMatrix(): List<ApprovalMatrix>

    @Update
    suspend fun update(matrix: ApprovalMatrix)

    @Delete
    suspend fun delete(matrix: ApprovalMatrix)

    @Query("SELECT * FROM ApprovalMatrix WHERE matrixType = :type")
    suspend fun getMatrixByType(type: String): List<ApprovalMatrix>
}