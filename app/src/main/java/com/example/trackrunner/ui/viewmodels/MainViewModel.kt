package com.example.trackrunner.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.trackrunner.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainRepository : MainRepository
) : ViewModel() {
}