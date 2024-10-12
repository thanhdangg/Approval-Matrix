package com.thanhdang.approvalmatrix.ui.component.create_matrix

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
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
    private var matrix: ApprovalMatrix? = null

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCreateMatrixBinding {
        return ActivityCreateMatrixBinding.inflate(layoutInflater)
    }

    override fun initArguments() {
        database = AppDatabase.getInstance(this)
        matrix = intent.getParcelableExtra("matrix")

    }

    override fun setup() {
        matrix?.let {
            binding.edName.setText(it.matrixName)
            binding.edMin.setText(it.minimumApproval.toString())
            binding.edMax.setText(it.maximumApproval.toString())
            binding.edNumber.setText(it.numberOfApproval.toString())
            binding.spType.setSelection(resources.getStringArray(R.array.spinner_values).indexOf(it.matrixType))

            binding.layutUpdateMatrix.visibility = View.VISIBLE
            binding.layutCreateMatrix.visibility = View.GONE
        }
    }

    override fun initViews() {
    }

    override fun initData() {
    }

    override fun initActions() {
        binding.btnReset.setOnClickListener {
            binding.edName.text?.clear()
            binding.edMin.text?.clear()
            binding.edMax.text?.clear()
            binding.edNumber.text?.clear()
            binding.spType.setSelection(0)
        }

        binding.btnSave.setOnClickListener {
            val name = binding.edName.text.toString()
            val min = binding.edMin.text.toString()
            val max = binding.edMax.text.toString()
            val numApproval = binding.edNumber.text.toString()
            val type = binding.spType.selectedItem.toString()


            if (name.isEmpty() || min.isEmpty() || max.isEmpty() || numApproval.isEmpty()) {
                showToast(getString(R.string.error_empty))
                return@setOnClickListener
            }
            else{
                if (matrix == null) {
                    val newMatrix = ApprovalMatrix(
                        matrixName = name,
                        matrixType = type,
                        minimumApproval = min.toLong(),
                        maximumApproval = max.toLong(),
                        numberOfApproval = numApproval.toInt()
                    )
                    saveDataToDatabase(newMatrix)
                } else {
                    val updatedMatrix = matrix!!.copy(
                        matrixName = name,
                        matrixType = type,
                        minimumApproval = min.toLong(),
                        maximumApproval = max.toLong(),
                        numberOfApproval = numApproval.toInt()
                    )
                    updateDataInDatabase(updatedMatrix)
                }
                binding.btnSave.isEnabled = false
                finish()
            }
        }
        binding.btnDelete.setOnClickListener {
            matrix?.let {
                deleteDataFromDatabase(it)
                finish()
            }
        }
        binding.btnUpdate.setOnClickListener {
            val name = binding.edName.text.toString()
            val min = binding.edMin.text.toString()
            val max = binding.edMax.text.toString()
            val numApproval = binding.edNumber.text.toString()
            val type = binding.spType.selectedItem.toString()


            if (name.isEmpty() || min.isEmpty() || max.isEmpty() || numApproval.isEmpty()) {
                showToast(getString(R.string.error_empty))
                return@setOnClickListener
            }
            else{
                val updatedMatrix = matrix!!.copy(
                    matrixName = name,
                    matrixType = type,
                    minimumApproval = min.toLong(),
                    maximumApproval = max.toLong(),
                    numberOfApproval = numApproval.toInt()
                )
                updateDataInDatabase(updatedMatrix)
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
    private fun updateDataInDatabase(matrix: ApprovalMatrix) {
        CoroutineScope(Dispatchers.IO).launch {
            database.matrixDao().update(matrix)
        }
    }
    private fun deleteDataFromDatabase(matrix: ApprovalMatrix) {
        CoroutineScope(Dispatchers.IO).launch {
            database.matrixDao().delete(matrix)
        }
    }
}