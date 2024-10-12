package com.thanhdang.approvalmatrix.ui.component.create_matrix

import android.view.LayoutInflater
import android.widget.Toast
import com.thanhdang.approvalmatrix.R
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix
import com.thanhdang.approvalmatrix.databinding.ActivityCreateMatrixBinding
import com.thanhdang.approvalmatrix.helper.database.AppDatabase
import com.thanhdang.approvalmatrix.ui.base.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityCreateMatrix : BaseActivity<ActivityCreateMatrixBinding>() {
//
    private lateinit var database: AppDatabase
    private lateinit var matrix: ApprovalMatrix

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCreateMatrixBinding {
        return ActivityCreateMatrixBinding.inflate(layoutInflater)
    }

    override fun initArguments() {
        database = AppDatabase.getInstance(this)

    }

    override fun setup() {
    }

    override fun initViews() {
    }

    override fun initData() {
    }

    override fun initActions() {

        binding.btnSave.setOnClickListener {
            val name = binding.edName.text.toString()
            val min = binding.edMin.text.toString()
            val max = binding.edMax.text.toString()
            val numApproval = binding.edNumber.text.toString()
            val type = binding.spType.selectedItem.toString()

            if (name.isEmpty() || min.isEmpty() || max.isEmpty() || numApproval.isEmpty()) {
                showToast(getString(R.string.error_empty))
                return@setOnClickListener
            }else
            {
                // save data to database
                val matrix = ApprovalMatrix(
                    matrixName = name,
                    matrixType = type,
                    minimumApproval = min.toLong(),
                    maximumApproval = max.toLong(),
                    numberOfApproval = numApproval.toInt()
                )
                saveDataToDatabase(matrix)
                finish()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun saveDataToDatabase(matrix: ApprovalMatrix) {
        CoroutineScope(Dispatchers.IO).launch {
            // save data to database
            database.matrixDao().insert(matrix)
        }
    }
}