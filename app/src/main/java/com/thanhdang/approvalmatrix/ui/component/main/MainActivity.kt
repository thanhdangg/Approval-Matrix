package com.thanhdang.approvalmatrix.ui.component.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanhdang.approvalmatrix.R
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix
import com.thanhdang.approvalmatrix.databinding.ActivityMainBinding
import com.thanhdang.approvalmatrix.helper.database.AppDatabase
import com.thanhdang.approvalmatrix.ui.base.BaseActivity
import com.thanhdang.approvalmatrix.ui.component.create_matrix.ActivityCreateMatrix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity<ActivityMainBinding>(), OnItemClickListener {

    private val matrixList = mutableListOf<ApprovalMatrix>()
    private lateinit var adapter: MatrixAdapter
    private lateinit var database: AppDatabase

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initArguments() {
        database = AppDatabase.getInstance(this)
    }

    override fun setup() {
        setupRecyclerView()
        fetchDataFromDatabase()
    }

    override fun initViews() {
    }

    override fun initData() {

    }

    override fun initActions() {
        binding.btnCreateMatrix.setOnClickListener {
            val intent = Intent(this, ActivityCreateMatrix::class.java)
            startActivity(intent)
        }
    }
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchDataFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val matrixList = database.matrixDao().getAllMatrix()
            withContext(Dispatchers.Main) {
                adapter = MatrixAdapter(matrixList, this@MainActivity)
                binding.recyclerView.adapter = adapter
            }
        }
    }
    override fun onItemClick(matrix: ApprovalMatrix) {
        val intent = Intent(this, ActivityCreateMatrix::class.java).apply {
            putExtra("matrix", matrix)
        }
        startActivity(intent)
    }

}