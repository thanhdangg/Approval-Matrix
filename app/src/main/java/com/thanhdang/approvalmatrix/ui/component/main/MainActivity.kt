package com.thanhdang.approvalmatrix.ui.component.main

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix
import com.thanhdang.approvalmatrix.databinding.ActivityMainBinding
import com.thanhdang.approvalmatrix.helper.database.AppDatabase
import com.thanhdang.approvalmatrix.ui.base.BaseActivity
import com.thanhdang.approvalmatrix.ui.component.create_matrix.CreateMatrixActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity<ActivityMainBinding>(), OnItemClickListener {

    private val matrixList = mutableListOf<ApprovalMatrix>()
    private lateinit var adapter: MatrixAdapter
    private lateinit var database: AppDatabase
    private var isDefaultFiltered = false
    private var isTransferFiltered = false

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initArguments() {
        database = AppDatabase.getInstance(this)
    }

    override fun setup() {
        setupRecyclerView()
//        fetchAllDataFromDatabase()
    }

    override fun initViews() {
        binding.btnTransfer.setTexts("Transfer", "Transfer")
    }

    override fun initData() {

    }

    override fun initActions() {
        binding.btnDefault.setOnClickListener {
            isDefaultFiltered = !isDefaultFiltered
            filterMatrixList()
            binding.btnDefault.setColor(isDefaultFiltered)
            Log.d("MainActivity", "Default Filtered: $isDefaultFiltered")
        }

        binding.btnTransfer.setOnClickListener {
            isTransferFiltered = !isTransferFiltered
            filterMatrixList()
            binding.btnTransfer.setColor(isTransferFiltered)
            Log.d("MainActivity", "Transfer Filtered: $isTransferFiltered")
        }

        binding.btnCreateMatrix.setOnClickListener {
            val intent = Intent(this, CreateMatrixActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MatrixAdapter(matrixList, this)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterMatrixList() {
        CoroutineScope(Dispatchers.IO).launch {
            val allMatrices = database.matrixDao().getAllMatrix()
            val filteredList = allMatrices.filter { matrix ->
                (isTransferFiltered || matrix.matrixType == "Default") &&
                        (isDefaultFiltered || matrix.matrixType == "Transfer Online")
            }
            Log.d("MainActivity", "Filtered List: $filteredList")
            withContext(Dispatchers.Main) {
                matrixList.clear()
                matrixList.addAll(filteredList)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun fetchAllDataFromDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val matrixList = database.matrixDao().getAllMatrix()
            Log.d("MainActivity", "Matrix List: $matrixList")
            withContext(Dispatchers.Main) {
                adapter = MatrixAdapter(matrixList, this@MainActivity)
                binding.recyclerView.adapter = adapter
            }
        }
    }

    override fun onItemClick(matrix: ApprovalMatrix) {
        val intent = Intent(this, CreateMatrixActivity::class.java).apply {
            putExtra("matrix", matrix)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        if (isDefaultFiltered || isTransferFiltered) {
            filterMatrixList()
        }
    }

}