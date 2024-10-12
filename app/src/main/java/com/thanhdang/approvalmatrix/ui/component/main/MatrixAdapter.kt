package com.thanhdang.approvalmatrix.ui.component.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thanhdang.approvalmatrix.R
import com.thanhdang.approvalmatrix.data.local.ApprovalMatrix

class MatrixAdapter(
    private val matrixList: List<ApprovalMatrix>,
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

            tvApprovalMin.text = matrix.minimumApproval.toString()
            tvApprovalMax.text = matrix.maximumApproval.toString()
            tvNumApproval.text = matrix.numberOfApproval.toString()
        }
    }
}