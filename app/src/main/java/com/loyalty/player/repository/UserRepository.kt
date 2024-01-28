package com.loyalty.player.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.loyalty.player.api.UserAPI
import com.loyalty.player.ui.splash.response.SplashResponse
import com.loyalty.player.utils.Constant
import com.loyalty.player.utils.NetworkResult
import com.loyalty.player.utils.printLog
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {


    //Splash
    private val splashLiveData = MutableLiveData<NetworkResult<SplashResponse>>()
    val splashResponseLiveData: LiveData<NetworkResult<SplashResponse>>
        get() = splashLiveData

    suspend fun splashLayout(
        city: String
    ) {
        try {
            splashLiveData.postValue(NetworkResult.Loading())
            val response = userAPI.splash(
                city, "NO", Constant.version, Constant.platform
            )
            splashLayoutResponse(response)
        } catch (e: Exception) {
            splashLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    private fun splashLayoutResponse(response: Response<SplashResponse>) {
        if (response.isSuccessful && response.body() != null) {
            splashLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            splashLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            splashLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}