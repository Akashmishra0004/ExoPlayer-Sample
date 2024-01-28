package com.loyalty.player.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import com.loyalty.player.databinding.InternetDialogBinding

class NetworkReceiver : BroadcastReceiver() {

    private var dialog: Dialog? = null

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        var status = NetworkUtil.getConnectivityStatusString(context)
        dialog = Dialog(context, android.R.style.ThemeOverlay_Material_ActionBar)
        val inflater = LayoutInflater.from(context)
        val bindingBottom = InternetDialogBinding.inflate(inflater)
        dialog?.setContentView(bindingBottom.root)

        bindingBottom.view49.setOnClickListener { v: View? ->
            try {
                if (NetworkUtil.getConnectivityStatusString(context) == true) {
                    val activity = (context as Activity)
                    activity.unregisterReceiver(this)
                    activity.finish()
                    activity.startActivity(activity.intent)
                    activity.overridePendingTransition(0, 0)
                    dialog?.dismiss()
                }
            } catch (_: Exception) {

            }
        }
        bindingBottom.view48.setOnClickListener {
            (context as Activity).startActivity(
                Intent(
                    Settings.ACTION_WIRELESS_SETTINGS
                )
            )
//            dialog?.dismiss()
        }
//        status?.let { Log.d("network", it) }

        try {

            if (NetworkUtil.getConnectivityStatusString(context) == false && dialog?.isShowing == false) {
                dialog?.show()
            } else {
                dialog?.dismiss()

            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }

}