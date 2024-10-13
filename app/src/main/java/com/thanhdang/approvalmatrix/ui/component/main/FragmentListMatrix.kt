package com.thanhdang.approvalmatrix.ui.component.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanhdang.approvalmatrix.R
import com.thanhdang.approvalmatrix.TAG
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix
import com.thanhdang.approvalmatrix.databinding.FragmentListMatrixBinding
import com.thanhdang.approvalmatrix.helper.database.AppDatabase
import com.thanhdang.approvalmatrix.ui.base.BaseFragment
import com.thanhdang.approvalmatrix.ui.component.create_matrix.FragmentCreateMatrix
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FragmentListMatrix : BaseFragment<FragmentListMatrixBinding>(), OnItemClickListener {

    private val matrixList = mutableListOf<ApprovalMatrix>()
    private lateinit var adapter: MatrixAdapter
    private lateinit var database: AppDatabase
    private var isDefaultFiltered = false
    private var isTransferFiltered = false

    private val viewModelMain: ViewModelMain by activityViewModels()

    override fun getBindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentListMatrixBinding {
        return FragmentListMatrixBinding.inflate(inflater, container, attachToParent)
    }

    override fun initData(bundle: Bundle?) {
        database = AppDatabase.getInstance(requireContext())
        setupRecyclerView()

    }

    override fun initViews() {
        binding.btnTransfer.setTexts("Transfer", "Transfer")
    }

    override fun initActions() {
        binding.btnDefault.setOnClickListener {
            isDefaultFiltered = !isDefaultFiltered
            filterMatrixList()
            binding.btnDefault.setColor(isDefaultFiltered)
            Log.d(TAG.MAIN_ACTIVITY, "Default Filtered: $isDefaultFiltered")
        }

        binding.btnTransfer.setOnClickListener {
            isTransferFiltered = !isTransferFiltered
            filterMatrixList()
            binding.btnTransfer.setColor(isTransferFiltered)
            Log.d(TAG.MAIN_ACTIVITY, "Transfer Filtered: $isTransferFiltered")
        }

        binding.btnCreateMatrix.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentListMatrix_to_fragmentCreateMatrix)
            viewModelMain.setBackButtonVisibility(true)
        }
    }
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
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
            Log.d(TAG.MAIN_ACTIVITY, "Filtered List: $filteredList")
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
            Log.d("ActivityMain", "Matrix List: $matrixList")
            withContext(Dispatchers.Main) {
                adapter = MatrixAdapter(matrixList, this@FragmentListMatrix)
                binding.recyclerView.adapter = adapter
            }
        }
    }
    override fun onItemClick(matrix: ApprovalMatrix) {
        val bundle = Bundle().apply {
            putParcelable("matrix", matrix)
        }
        findNavController().navigate(R.id.action_fragmentListMatrix_to_fragmentCreateMatrix, bundle)
        viewModelMain.setBackButtonVisibility(true)
    }

    override fun onResume() {
        super.onResume()
        viewModelMain.setBackButtonVisibility(false)
        if (isDefaultFiltered || isTransferFiltered) {
            filterMatrixList()
        }
    }
}