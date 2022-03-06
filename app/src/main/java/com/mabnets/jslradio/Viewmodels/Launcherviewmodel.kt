package com.mabnets.jslradio.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Launcherviewmodel :ViewModel(){

    private  val _isloading= MutableStateFlow(false)
    val isloading=_isloading.asStateFlow()

    init{
        viewModelScope.launch {
            delay(3000)
            _isloading.value=false
        }
    }
}