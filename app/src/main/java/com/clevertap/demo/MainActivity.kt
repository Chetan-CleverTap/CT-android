package com.clevertap.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.clevertap.android.pushtemplates.PTConstants
import com.clevertap.android.sdk.*
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.clevertap.android.sdk.inapp.CTLocalInApp
import com.clevertap.android.sdk.inapp.CTLocalInApp.InAppType
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener
import com.clevertap.demo.databinding.ActivityMainBinding
import org.json.JSONObject


class MainActivity : BaseActivity(), CTInboxListener, CTPushNotificationListener,
    InAppNotificationButtonListener, DisplayUnitListener, PushPermissionResponseListener {

    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        MyApp.getCleverTapDefaultInstance()?.ctPushNotificationListener = this
        Log.d("DEBUG_ANDROID_S", "onCreate " + this.javaClass.name)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MyApp.getCleverTapDefaultInstance()?.getAllInboxMessages()

        MyApp.getCleverTapDefaultInstance()?.ctNotificationInboxListener = this@MainActivity

        MyApp.getCleverTapDefaultInstance()?.initializeInbox()
        MyApp.getCleverTapDefaultInstance()?.setDisplayUnitListener(this)

        with(binding) {
            this!!.buttonLogout.setOnClickListener {
//                logOut()
                requestOverlayPermission()
            }
        }

        with(binding) {
            this!!.buttonCreateNewProfile.setOnClickListener {
                newProfile()
            }
        }

        with(binding) {
            this!!.buttonRaiseEvent.setOnClickListener {
                raiseEvent()
            }
        }

        with(binding) {
            this!!.buttonPushProfile.setOnClickListener {
                updateProfile()
            }
        }

        MyApp.getCleverTapDefaultInstance()?.setInAppNotificationButtonListener(this);
        MyApp.getCleverTapDefaultInstance()?.registerPushPermissionNotificationResponseListener(this);

        // MyApp.getCleverTapDefaultInstance()?.promptForPushPermission(true)
    }

    fun dismissNotification(intent: Intent?, applicationContext: Context){
        intent?.extras?.apply {
            var autoCancel = true
            var notificationId = -1

            getString("actionId")?.let {
                Log.d("ACTION_ID", it)
                autoCancel = getBoolean("autoCancel", true)
                notificationId = getInt("notificationId", -1)
            }
            /**
             * If using InputBox template, add ptDismissOnClick flag to not dismiss notification
             * if pt_dismiss_on_click is false in InputBox template payload. Alternatively if normal
             * notification is raised then we dismiss notification.
             */
            val ptDismissOnClick = intent.extras!!.getString(PTConstants.PT_DISMISS_ON_CLICK,"")

            if (autoCancel && notificationId > -1 && ptDismissOnClick.isNullOrEmpty()) {
                val notifyMgr: NotificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notifyMgr.cancel(notificationId)
            }
        }
    }

    private fun requestOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        val myIntent: Intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        myIntent.data = Uri.parse("package:$packageName")
        startActivityForResult(myIntent, 1)
    }

    @SuppressLint("RestrictedApi")
    private fun raiseEvent() {

        val jsonObject: JSONObject = CTLocalInApp.builder()
            .setInAppType(InAppType.HALF_INTERSTITIAL)
            .setTitleText("Get Notified")
            .setMessageText("Please enable notifications on your device to use Push Notifications.")
            .followDeviceOrientation(true)
            .setPositiveBtnText("Allow")
            .setNegativeBtnText("Cancel")
            .setFallbackToSettings(true)
            .setBackgroundColor(Constants.WHITE)
            .setBtnBorderColor(Constants.BLUE)
            .setTitleTextColor(Constants.BLUE)
            .setMessageTextColor(Constants.BLACK)
            .setBtnTextColor(Constants.WHITE)
            .setImageUrl("https://icons.iconarchive.com/icons/treetog/junior/64/camera-icon.png")
            .setBtnBackgroundColor(Constants.BLUE)
            .build()
//        MyApp.getCleverTapDefaultInstance()?.promptPushPrimer(jsonObject)
        MyApp.getCleverTapDefaultInstance()?.promptForPushPermission(false)

//        MyApp.getCleverTapDefaultInstance()?.pushEvent(binding?.et?.text.toString())
    }

    private fun newProfile() {
        val profileUpdate = HashMap<String, Any>()
        profileUpdate["Name"] = "Jack Montana" // String
        profileUpdate["Identity"] != null
//
        profileUpdate["City"] = "Mumbai"

        profileUpdate["rec_2"] = arrayOf("CT000001", "CT000003")
        profileUpdate["rec_1"] = arrayOf("CT000005", "CT000007", "CT000001", "CT000003")
        profileUpdate["Gender1"] = "M"
        profileUpdate["rating"] = arrayOf(1, 2, 3, 4)
        profileUpdate["rating-string"] = arrayOf("3", "4", "5")
        profileUpdate["City"] = arrayOf("Delhi", "Mumbai", "Chennai")

        MyApp.getCleverTapDefaultInstance()?.onUserLogin(profileUpdate)
    }

    private fun updateProfile () {
        val profileUpdate = HashMap<String, Any>()
        profileUpdate["City"] = "Delhi"
        MyApp.getCleverTapDefaultInstance()?.pushProfile(profileUpdate)
    }

    private fun logOut() {
        try {
//            val preferences = getSharedPreferences("WizRocket", Context.MODE_PRIVATE)
//            val editor = preferences.edit()
//            editor.clear()
//            editor.apply()
//                MyApp.getCleverTapDefaultInstance().clear()

//             Testing(applicationContext)

            startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "*/*"
            }, 100)
//            val chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
//            chooseFile.addCategory(Intent.CATEGORY_OPENABLE)
//            chooseFile.type = "application/octet-stream"
//            startActivityForResult(
//                Intent.createChooser(chooseFile, "Choose a file"),
//                100
//            )
        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }

    override fun inboxDidInitialize() {
        with(binding) {
            this!!.buttonAppInbox.setOnClickListener {
                MyApp.getCleverTapDefaultInstance()?.showAppInbox()

                MyApp.getCleverTapDefaultInstance()?.allInboxMessages
            }
        }
    }

/*    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("DEBUG_ANDROID_S", "onNewIntent MainActivity")
        CleverTapAPI.getDefaultInstance(applicationContext)
            ?.pushNotificationClickedEvent(intent?.extras)
        Toast.makeText(this, "On new intent called on main activity", Toast.LENGTH_LONG).show()
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            Testing(applicationContext, data?.data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun inboxMessagesDidUpdate() {
    }

    override fun onNotificationClickedPayloadReceived(payload: HashMap<String, Any>?) {
        Log.d("DEBUG_ANDROID_S", "Clicked callback" + this.javaClass.name)
    }

    override fun onInAppButtonClick(payload: HashMap<String, String>?) {
        if (payload != null) {
            Log.d("In-app Click", "payload$payload")
        }
    }

    override fun onDisplayUnitsLoaded(units: ArrayList<CleverTapDisplayUnit>?) {
        Log.d("NativeDisplay", "payload$units")
        MyApp.getCleverTapDefaultInstance()?.pushDisplayUnitViewedEventForID(units!![0].unitID)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPushPermissionResponse(accepted: Boolean) {
        if(accepted){

            CleverTapAPI.createNotificationChannelGroup(
                this,
                "YourGroupId",
                "YourGroupName"
            )

            CleverTapAPI.createNotificationChannel(
                applicationContext, "test", "test", "test",
                NotificationManager.IMPORTANCE_MAX, "YourGroupId", true
            )

            CleverTapAPI.createNotificationChannel(applicationContext,"sound",
                "Game of Thrones","Game Of Thrones",NotificationManager.IMPORTANCE_MAX,
                true,"ring.mp3")

            CleverTapAPI.createNotificationChannel(applicationContext,"sound1",
                "Game of Thrones","Game Of Thrones",NotificationManager.IMPORTANCE_MAX,
                true,"ring1.wav")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (MyApp.getCleverTapDefaultInstance() != null) {
            MyApp.getCleverTapDefaultInstance()?.unregisterPushPermissionNotificationResponseListener(this)
        }
    }
}



