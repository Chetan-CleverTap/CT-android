package com.clevertap.demo

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.bumptech.glide.Glide

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        showPIP(context)
    }

    fun showPIP(context: Context?){
        Log.d("DEBUG_PIP", "FCMs " + this.javaClass.name)

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val dialogView: View = inflater.inflate(R.layout.pip, null)

        val button: ImageView = dialogView.findViewById(R.id.image)

        Glide
            .with(dialogView.context)
            .load("https://fastly.picsum.photos/id/60/800/1200.jpg?hmac=04HrslbMBAJtUEr4Lw9w3T9OkpDXCgYiSn3ustCHthc")
            .centerCrop()
            .into(button);

        builder.setView(dialogView)
        val alert: AlertDialog = builder.create()
        alert.getWindow()?.requestFeature(Window.FEATURE_NO_TITLE)
        alert.getWindow()?.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        alert.setCanceledOnTouchOutside(true)
        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        val params: WindowManager.LayoutParams
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        } else {
            params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }

        val window: Window? = alert.getWindow()
        window?.addFlags(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setGravity(Gravity.CENTER)
        lp.copyFrom(window?.getAttributes())
        //This makes the dialog take up the full width
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        window?.setAttributes(lp)
        button.setOnClickListener {
            alert.dismiss()
        }
        alert.show()
    }
}