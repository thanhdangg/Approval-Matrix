package com.thanhdang.approvalmatrix.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected abstract fun getViewBinding(layoutInflater: LayoutInflater): VB

    protected abstract fun initArguments()
    protected abstract fun setup()

    protected abstract fun initViews()
    protected abstract fun initData()
    protected abstract fun initActions()

    lateinit var binding: VB
    open fun setApplyWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        ViewCompat.setOnApplyWindowInsetsListener(
            binding.root
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding = getViewBinding(layoutInflater)
        setContentView(binding.root)

        setApplyWindow()

        initArguments()     //get arguments from previous
        setup()             // setup ....

        initViews()
        initActions()
        initData()      // load data from db, call API

    }

}