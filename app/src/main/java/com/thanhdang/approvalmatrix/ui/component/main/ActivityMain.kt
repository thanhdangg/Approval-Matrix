package com.thanhdang.approvalmatrix.ui.component.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.thanhdang.approvalmatrix.R
import com.thanhdang.approvalmatrix.TAG
import com.thanhdang.approvalmatrix.databinding.ActivityMainBinding
import com.thanhdang.approvalmatrix.ui.base.BaseActivity

class ActivityMain : BaseActivity<ActivityMainBinding>() {

    private var navController: NavController? = null

    private val viewModelMain: ViewModelMain by viewModels()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initArguments() {}

    override fun setup() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // Observe the ViewModel
        viewModelMain.isBackButtonVisible.observe(this) { isVisible ->
            Log.d(TAG.MAIN_ACTIVITY, "isBackButtonVisible: $isVisible")
            binding.btnBack.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
    }

    override fun initViews() {}

    override fun initData() {}

    override fun initActions() {
        binding.btnBack.setOnClickListener {
            navController?.navigateUp()
        }
    }

}