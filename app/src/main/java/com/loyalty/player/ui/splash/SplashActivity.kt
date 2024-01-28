package com.loyalty.player.ui.splash

//import com.loyalty.player.ui.login.LoginActivity
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.loyalty.player.R
import com.loyalty.player.databinding.ActivitySplashBinding
import com.loyalty.player.prefrence.PreferenceManager
import com.loyalty.player.ui.dailogs.LoaderDialog
import com.loyalty.player.ui.player.supportClass.argument.PlayerParams
import com.loyalty.player.ui.player.supportClass.argument.VideoSubtitle
import com.loyalty.player.ui.player.supportClass.extension.startPlayer
import com.loyalty.player.ui.splash.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var preferences: PreferenceManager
    private var binding: ActivitySplashBinding? = null
    private val authViewModel: SplashViewModel by viewModels()
    private var loader: LoaderDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater, null, false)
        setContentView(binding?.root)

        manageFunctions()
    }

    private fun manageFunctions() {
        if (isConnected()) {
            val runnable = Runnable {
                startPlayer(getPlayerParam())
            }
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(runnable, 3000)
        } else {
            networkDialog()
        }

    }

    private fun networkDialog() {
        val networkDialog = Dialog(this)
        networkDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        networkDialog.setContentView(R.layout.internet_dialog)
        networkDialog.show()
        val window: Window? = networkDialog.window
        val wlp: WindowManager.LayoutParams? = window?.attributes
        wlp?.gravity = Gravity.CENTER
        wlp?.flags = wlp?.flags?.and(WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv())
        window?.attributes = wlp
        networkDialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        )

        val restart = networkDialog.findViewById<View>(R.id.view49)
        val network = networkDialog.findViewById<View>(R.id.view48)
        network?.setOnClickListener { v: View? ->
            val intent2 = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent2)
        }
        restart?.setOnClickListener { v: View? ->
            recreate()
        }
    }

    private fun isConnected(): Boolean {
        var connected = false
        try {
            val cm =
                applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return connected
    }

    private fun getPlayerParam(): PlayerParams {
        val subtitleList = mutableListOf<VideoSubtitle>().apply {
            add(
                VideoSubtitle(
                    title = "English",
                    url = "https://amara.org/en/subtitles/sbpf8fMnSckL/en/7/download/Big%20Buck%20Bunny.en.vtt"
                )
            )
        }

        return PlayerParams(
            url = "https://5b44cf20b0388.streamlock.net:8443/vod/smil:bbb.smil/playlist.m3u8",
            subtitles = subtitleList
        )

    }
}