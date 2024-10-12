package com.thanhdang.approvalmatrix.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import com.thanhdang.approvalmatrix.R

class CustomButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val text1: TextView
    private val text2: TextView
    private val toggle: ToggleButton

    init {
        // Inflate the custom layout
        LayoutInflater.from(context).inflate(R.layout.button_main, this, true)

        // Bind the views
        text1 = findViewById(R.id.text1)
        text2 = findViewById(R.id.text2)
        toggle = findViewById(R.id.toggle)

        // Set the click listener for the button
        setOnClickListener {
            toggleBorderColor()
            // Perform any action specific to this button
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

}
