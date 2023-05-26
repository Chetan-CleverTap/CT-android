package com.clevertap.demo

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.clevertap.android.sdk.*
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener
import com.clevertap.demo.databinding.ActivityMainBinding


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
                logOut()
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

    private fun raiseEvent() {

        MyApp.getCleverTapDefaultInstance()?.promptForPushPermission(true)

//        MyApp.getCleverTapDefaultInstance()?.pushEvent(binding?.et?.text.toString())
    }

    private fun newProfile() {
        val profileUpdate = HashMap<String, Any>()
        profileUpdate["Name"] = "Jack Montana" // String
        profileUpdate["Identity"] = binding?.et?.text.toString()

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
            val preferences = getSharedPreferences("WizRocket", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
//                MyApp.getCleverTapDefaultInstance().clear()

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



