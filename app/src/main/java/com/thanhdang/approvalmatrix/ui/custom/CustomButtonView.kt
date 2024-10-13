package com.thanhdang.approvalmatrix.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import com.google.android.material.divider.MaterialDivider
import com.thanhdang.approvalmatrix.R

class CustomButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val text1: TextView
    private val text2: TextView
    private val toggle: ToggleButton
    private val main: LinearLayout
    private val divider: View


    init {
        // Inflate the custom layout
        LayoutInflater.from(context).inflate(R.layout.button_main, this, true)

        // Bind the views
        text1 = findViewById(R.id.text1)
        text2 = findViewById(R.id.text2)
        toggle = findViewById(R.id.toggle)
        main = findViewById(R.id.main)
        divider = findViewById(R.id.divider)

        setOnClickListener {
            toggleBorderColor()
        }
    }

    private fun toggleBorderColor() {
        isSelected = !isSelected
        refreshDrawableState()
    }

    fun setTexts(text1Value: String, text2Value: String) {
        text1.text = text1Value
        text2.text = text2Value
    }
    fun setColor(status : Boolean) {
        if (status) {
            text1.setTextColor(resources.getColor(R.color.primary_100))
            text2.setTextColor(resources.getColor(R.color.primary_100))
            main.setBackgroundResource(R.drawable.bg_button_main_checked)
            toggle.setBackgroundResource(R.drawable.ic_arrow_up_primary)
            divider.setBackgroundResource(R.color.primary_100)

        }
        else {
            toggle.setBackgroundResource(R.drawable.ic_arrow_down)
            text1.setTextColor(resources.getColor(R.color.black))
            text2.setTextColor(resources.getColor(R.color.black))
            main.setBackgroundResource(R.drawable.bg_button_main_uncheck)
            divider.setBackgroundResource(R.color.default_middle)
        }
    }

}
