package com.loyalty.player.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.*
import android.net.Uri
import android.os.Vibrator
import android.provider.Settings
import android.text.*
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import okhttp3.internal.and
import java.net.MalformedURLException
import java.net.URL
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Exception
import kotlin.Float
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.Throws
import kotlin.apply
import kotlin.let

@Suppress("DEPRECATION")
class Constant {


    public interface IntentKey {
        companion object {
            var IS_SESSION_EXPIRE = "is_session_expire"
            var REMAING_TIME = "remaing_time"
        }
    }

    companion object {
        const val platform = "ANDROID"
        const val version = "11.3"
        const val status = "success"
        const val SUCCESS_CODE = 10001

        var DISPLAY = 1

        fun onShareClick(context: Context, shareUrl: String, shareMessage: String) {
            /*System.out.println("ShareUrl"+shareURL+"fkfg"+shareMessage);*/
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "$shareMessage $shareUrl")
            sendIntent.type = "text/plain"
            context.startActivity(sendIntent)
        }

        @Throws(Exception::class)
        fun getHash(text: String): String {
            val mdText = MessageDigest.getInstance("SHA-512")
            val byteData = mdText.digest(text.toByteArray())
            val sb = StringBuffer()
            for (i in byteData.indices) {
                sb.append(((byteData[i] and 0xff) + 0x100).toString(16).substring(1))
            }
            return sb.toString()
        }

        fun getDeviceId(context: Activity): String {
            return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        }

        fun extractYoutubeId(s: String): String? {
            return try {
                var query: String? = null
                try {
                    query = URL(s).query
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
                val param = query!!.split("&").toTypedArray()
                var id: String? = null
                for (row in param) {
                    val param1 = row.split("=").toTypedArray()
                    if (param1[0] == "v") {
                        id = param1[1]
                    }
                }
                id
            } catch (e: Exception) {
                ""
            }
        }

        fun convertDpToPixel(dp: Float, context: Context): Int {
            val resources = context.resources
            val metrics = resources.displayMetrics
            val px = dp * (metrics.densityDpi / 160f)
            return px.toInt()
        }

        fun removeTrailingZeroFormatter(d: Float): String? {
            return if (d == d.toLong().toFloat()) String.format("%d", d.toLong()) else {
                val df = DecimalFormat("#0.00")
                df.format(d.toDouble())
            }
        }

        fun openMap(context: Context, lat: String, lang: String) {
            val strUri = "http://maps.google.com/maps?q=loc:$lat,$lang (Label which you want)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
            context.startActivity(intent)
        }

        fun hideKeyboard(activity: Activity) {
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            if (inputMethodManager.isAcceptingText) {
                inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0
                )
            }
        }

        fun shareData(activity: Activity, title: String, shareUrl: String) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, title)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareUrl)
            activity.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        @SuppressLint("SimpleDateFormat")
        fun changeDateFormat(date: String?): String? {
            return if (date != null && date != "") {
                try {
                    val sdf = SimpleDateFormat("dd-MM-yyyy")
                    val displayFormat = SimpleDateFormat("dd MMM, yyyy")
                    val d = displayFormat.parse(date)

                    d?.let { sdf.format(it) }
                } catch (e: Exception) {
                    e.printStackTrace()
                    date
                }
            } else {
                ""
            }
        }

        //AppBar Hide
        fun appBarHide(activity: Activity) {
            activity.window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
                statusBarColor = Color.TRANSPARENT
            }
        }

        fun setMargins(view: View, left: Int, top: Int, right: Int, bottom: Int) {
            if (view.layoutParams is MarginLayoutParams) {
                val p = view.layoutParams as MarginLayoutParams
                p.setMargins(left, top, right, bottom)
                view.requestLayout()
            }
        }

        fun convertTime(timeString: Int): Int {
            return timeString * 60 * 1000
        }

        fun dateFormatter(milliseconds: String): String {
            return SimpleDateFormat("dd MMM yyyy").format(Date(milliseconds.toLong())).toString()
        }

        // Vibrates the device for 100 milliseconds.
        fun vibrateDevice(activity: Activity) {
            val v: Vibrator = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            v.vibrate(100)
        }

        fun toCamelCase(s: String): String? {
            if (s.isEmpty()) {
                return s
            }
            val parts = s.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            var camelCaseString = ""
            for (part in parts) {
                camelCaseString = camelCaseString + toProperCase(part) + " "
            }
            return camelCaseString
        }

        fun toProperCase(s: String): String {
            return s.substring(0, 1).uppercase(Locale.getDefault()) +
                    s.substring(1).lowercase(Locale.getDefault())
        }

    }


    interface SharedPreference {
        companion object {
            var GIFT_CARD_DETAILS = "gift_card_details"
            const val WALK_THROUGH = "walk"
            const val USER_NAME = "user_name"
            const val USER_TOKEN = "user_token"
            const val CHECK_BOX = "status"
            const val FACEBOOK_USER_ID = "facebook_user_id"
            const val USER_GENDER = "user_gender"
        }
    }

}