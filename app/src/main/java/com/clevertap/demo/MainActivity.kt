package com.clevertap.demo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.clevertap.android.sdk.CTInboxListener
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.demo.databinding.ActivityMainBinding
import java.lang.Exception
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity(), CTInboxListener {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MyApp.getCleverTapDefaultInstance()?.ctNotificationInboxListener = this@MainActivity
        MyApp.getCleverTapDefaultInstance()?.initializeInbox()

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
    }

    private fun raiseEvent() {
        MyApp.getCleverTapDefaultInstance()?.pushEvent(binding?.et?.text.toString())
    }

    private fun newProfile() {
        val profileUpdate = HashMap<String, Any>()
        profileUpdate["Name"] = "Jack Montana" // String
        profileUpdate["Email"] = binding?.et?.text.toString()
        MyApp.getCleverTapDefaultInstance()?.onUserLogin(profileUpdate)
    }

    private fun logOut() {
        try {
            val preferences = getSharedPreferences("WizRocket", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
            CleverTapAPI.getInstances().clear()

        } catch (e: Exception) {
            Log.e("Error", e.message.toString())
        }
    }

    override fun inboxDidInitialize() {
        with(binding) {
            this!!.buttonAppInbox.setOnClickListener {
                MyApp.getCleverTapDefaultInstance()?.showAppInbox()
            }
        }
    }

    override fun inboxMessagesDidUpdate() {
    }
}