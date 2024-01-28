package com.loyalty.player.ui.splash.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loyalty.player.repository.UserRepository
import com.loyalty.player.ui.splash.response.SplashResponse
import com.loyalty.player.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
//Splash
    val liveDataScope: LiveData<NetworkResult<SplashResponse>>
    get() = userRepository.splashResponseLiveData

    fun splash(city: String) {
        viewModelScope.launch {
            userRepository.splashLayout(city)
        }
    }

}