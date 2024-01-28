package com.loyalty.player.ui.login

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.loyalty.player.databinding.ActivityLoginBinding
import com.loyalty.player.prefrence.PreferenceManager
import com.loyalty.player.ui.dailogs.LoaderDialog
import com.loyalty.player.ui.splash.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var preferences: PreferenceManager
    private var binding: ActivityLoginBinding? = null
    private val authViewModel: SplashViewModel by viewModels()
    private var loader: LoaderDialog? = null


    //internet Check
    private var broadcastReceiver: BroadcastReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater, null, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(binding?.root)

    }
}