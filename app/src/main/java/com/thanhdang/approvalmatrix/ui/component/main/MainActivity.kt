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
import com.thanhdang.approvalmatrix.R
import com.thanhdang.approvalmatrix.databinding.ActivityMainBinding
import com.thanhdang.approvalmatrix.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initArguments() {
    }

    override fun setup() {
    }

    override fun initViews() {
//        supportActionBar?.hide()
//
//        window.decorView.systemUiVisibility = (
//                View.SYSTEM_UI_FLAG_FULLSCREEN
//                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)


    }

    override fun initData() {

    }

    override fun initActions() {
        binding.button.setOnClickListener {
            val intent = Intent(this, com.thanhdang.approvalmatrix.ui.component.create_matrix.ActivityCreateMatrix::class.java)
            startActivity(intent)
        }
    }
}