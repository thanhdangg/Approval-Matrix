package com.thanhdang.approvalmatrix.ui.component.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thanhdang.approvalmatrix.R
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix

class MatrixAdapter(
    private var matrixList: List<ApprovalMatrix>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MatrixAdapter.MatrixViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatrixViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matrix, parent, false)
        return MatrixViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatrixViewHolder, position: Int) {
        val matrix = matrixList[position]
        holder.bind(matrix)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(matrix)
        }
    }

    override fun getItemCount(): Int = matrixList.size

    class MatrixViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(matrix: ApprovalMatrix) {
            val tvApprovalMin = itemView.findViewById<TextView>(R.id.tvApprovalMin)
            val tvApprovalMax = itemView.findViewById<TextView>(R.id.tvApprovalMax)
            val tvNumApproval = itemView.findViewById<TextView>(R.id.tvNumApproval)
            val tvMatrixName = itemView.findViewById<TextView>(R.id.tv_approval_1)
            val tvType = itemView.findViewById<TextView>(R.id.tv_approval_2)
            val layoutApprover1 = itemView.findViewById<View>(R.id.layout_approver_1)
            val layoutApprover2 = itemView.findViewById<View>(R.id.layout_approver_2)

            tvApprovalMin.text = matrix.minimumApproval.toString()
            tvApprovalMax.text = matrix.maximumApproval.toString()
            tvNumApproval.text = matrix.numberOfApproval.toString()
            tvMatrixName.text = matrix.matrixName
            tvType.text = matrix.matrixType
            when (matrix.numberOfApproval) {
                1 -> {
                    layoutApprover1.visibility = View.VISIBLE
                }
                2 -> {
                    layoutApprover1.visibility = View.VISIBLE
                    layoutApprover2.visibility = View.VISIBLE
                }
                else -> {
                    layoutApprover1.visibility = View.GONE
                    layoutApprover2.visibility = View.GONE
                }
            }
        }
    }
}