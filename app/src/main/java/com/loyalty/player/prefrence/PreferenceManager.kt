package com.loyalty.player.prefrence

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.CITY
import com.loyalty.player.utils.Constants.PREFS_TOKEN_FILE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {
    private var editor: SharedPreferences.Editor? = null

    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)


    fun getCityName(): String {
        return prefs.getString(CITY, "").toString()
    }

    //City Name Coming Soon
    fun cityNameCinema(cityCinema: String) {
        editor = prefs.edit()
        editor?.putString(CITY, cityCinema)
        editor?.apply()
    }



    fun clearData(requireActivity: Activity) {
        editor = prefs.edit()
        editor?.apply()
        editor?.commit()
//        val intent = Intent(requireActivity, LoginActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//        requireActivity.startActivity(intent)
//        requireActivity.finish()
    }
}