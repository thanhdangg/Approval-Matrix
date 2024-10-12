package com.thanhdang.approvalmatrix.helper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix
import com.thanhdang.approvalmatrix.helper.database.Dao.MatrixDao

@Database(entities = [ApprovalMatrix::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matrixDao(): MatrixDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "ApprovalMatrix"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}