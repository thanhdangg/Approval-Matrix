package com.thanhdang.approvalmatrix.ui.component.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thanhdang.approvalmatrix.TAG

class ViewModelMain : ViewModel() {
    private val _isBackButtonVisible = MutableLiveData<Boolean>()
    val isBackButtonVisible: LiveData<Boolean> get() = _isBackButtonVisible

    fun setBackButtonVisibility(isVisible: Boolean) {
        Log.d(TAG.MAIN_ACTIVITY, "setBackButtonVisibility: $isVisible")
        _isBackButtonVisible.value = isVisible
    }
}