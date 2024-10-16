package com.thanhdang.approvalmatrix.ui.component.create_matrix

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.thanhdang.approvalmatrix.R
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix
import com.thanhdang.approvalmatrix.databinding.FragmentCreateMatrixBinding
import com.thanhdang.approvalmatrix.helper.database.AppDatabase
import com.thanhdang.approvalmatrix.ui.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentCreateMatrix : BaseFragment<FragmentCreateMatrixBinding>() {

    private lateinit var database: AppDatabase
    private var matrix: ApprovalMatrix? = null
    private var popupWindow: PopupWindow? = null

    override fun getBindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentCreateMatrixBinding {
        return FragmentCreateMatrixBinding.inflate(inflater, container, attachToParent)
    }


    override fun initData(bundle: Bundle?) {
        database = AppDatabase.getInstance(requireContext())
        matrix = arguments?.getParcelable("matrix")
    }

    override fun initViews() {
        matrix?.let {
            binding.edName.setText(it.matrixName)
            binding.edMin.setText(it.minimumApproval.toString())
            binding.edMax.setText(it.maximumApproval.toString())
            binding.edNumber.setText(it.numberOfApproval.toString())
            binding.spType.setSelection(
                resources.getStringArray(R.array.spinner_values).indexOf(it.matrixType)
            )
            binding.layoutUpdateMatrix.visibility = View.VISIBLE
            binding.layoutCreateMatrix.visibility = View.GONE
            binding.tvTitle.text = getString(R.string.update_matrix)
        }
        setFocusChangeListener(binding.edName)
        setFocusChangeListener(binding.edMin)
        setFocusChangeListener(binding.edMax)
        setFocusChangeListener(binding.edNumber)
        setFocusChangeListener(binding.spType)
    }

    override fun initActions() {


        binding.spType.setOnTouchListener { _, _ ->
            val items = resources.getStringArray(R.array.spinner_values).toList()
            showPopupWindow(binding.spType, items)
            true
        }
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

            handlingSaveZoneAlert()
            handelForRangeOfApproval()

            if (name.isEmpty() || min.isEmpty() || max.isEmpty() || numApproval.isEmpty()) {
                showToast(getString(R.string.error_empty))
                return@setOnClickListener
            } else {
                if (min.toLong() > max.toLong()) {
                    showToast(getString(R.string.error_min_greater_than_max))
                    return@setOnClickListener
                }

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
                // back to previous fragment
                findNavController().navigate(R.id.action_fragmentCreateMatrix_to_fragmentListMatrix)

            }
        }
        binding.btnDelete.setOnClickListener {
            matrix?.let {
                deleteDataFromDatabase(it)
                // back to previous fragment
                findNavController().navigate(R.id.action_fragmentCreateMatrix_to_fragmentListMatrix)

            }
        }
        binding.btnUpdate.setOnClickListener {
            val name = binding.edName.text.toString()
            val min = binding.edMin.text.toString()
            val max = binding.edMax.text.toString()
            val numApproval = binding.edNumber.text.toString()
            val type = binding.spType.selectedItem.toString()

            handlingSaveZoneAlert()
            handelForRangeOfApproval()


            if (name.isEmpty() || min.isEmpty() || max.isEmpty() || numApproval.isEmpty()) {
                showToast(getString(R.string.error_empty))
                return@setOnClickListener
            } else {
                if (min.toLong() >= max.toLong()) {
                    showToast(getString(R.string.error_min_greater_than_max))
                    return@setOnClickListener
                }

                val updatedMatrix = matrix!!.copy(
                    matrixName = name,
                    matrixType = type,
                    minimumApproval = min.toLong(),
                    maximumApproval = max.toLong(),
                    numberOfApproval = numApproval.toInt()
                )
                updateDataInDatabase(updatedMatrix)
                // back to previous fragment
                findNavController().navigate(R.id.action_fragmentCreateMatrix_to_fragmentListMatrix)

            }
        }
    }
    private fun showPopupWindow(anchorView: View, items: List<String>) {
        if (popupWindow != null && popupWindow!!.isShowing) {
            popupWindow!!.dismiss()
        }

        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.popup_spinner_items, null)
        val listView = popupView.findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        popupWindow =
            PopupWindow(popupView, anchorView.width, WindowManager.LayoutParams.WRAP_CONTENT, true)
        popupWindow!!.showAsDropDown(anchorView)

        listView.setOnItemClickListener { _, _, position, _ ->
            // Handle item click
            val selectedItem = items[position]
            (anchorView as AppCompatSpinner).setSelection(position)
            popupWindow!!.dismiss()
        }
    }
    private fun handelForRangeOfApproval() {
        val minText = binding.edMin.text.toString()
        val maxText = binding.edMax.text.toString()

        if (minText.isEmpty() || maxText.isEmpty()) {
            return
        }

        val min = minText.toLong()
        val max = maxText.toLong()

        if (min > max) {
            binding.edMin.hint = getString(R.string.error_min_greater_than_max)
            binding.edMax.hint = getString(R.string.error_max_less_than_min)
            showToast(getString(R.string.error_min_greater_than_max))

            binding.btnSave.isEnabled = false
            binding.btnUpdate.isEnabled = false
            return

        } else {
            binding.btnSave.isEnabled = true
            binding.btnUpdate.isEnabled = true
        }
    }

    private fun handlingSaveZoneAlert() {
        val hintTextColor =
            ContextCompat.getColorStateList(requireContext(), R.color.red)
        when {
            binding.edName.text.toString().isEmpty() -> {
                binding.edName.hint = getString(R.string.empty_matrix_name)
                binding.edName.setHintTextColor(hintTextColor)
            }

            binding.edMin.text.toString().isEmpty() -> {
                binding.edMin.hint = getString(R.string.empty_min)
                binding.edMin.setHintTextColor(hintTextColor)
            }

            binding.edMax.text.toString().isEmpty() -> {
                binding.edMax.hint = getString(R.string.empty_max)
                binding.edMax.setHintTextColor(hintTextColor)
            }

            binding.edNumber.text.toString().isEmpty() -> {
                binding.edNumber.hint = getString(R.string.empty_number_of_approval)
                binding.edNumber.setHintTextColor(hintTextColor)
            }
            else -> {
                binding.btnSave.isEnabled = true
            }
        }
    }
    private fun setFocusChangeListener(view: View) {
        view.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
//                showKeyboard(view)
                view.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_has_focus))
            } else {
                view.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.bg_stroke_border
                    )
                )
            }
        }
        if (view.id == binding.edMin.id || view.id == binding.edMax.id) {
            view.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    handelForRangeOfApproval()
                    view.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_stroke_border
                        )
                    )
                } else {
                    view.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.bg_has_focus
                        )
                    )
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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