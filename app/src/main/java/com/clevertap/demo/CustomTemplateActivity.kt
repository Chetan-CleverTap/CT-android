package com.clevertap.demo

import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.clevertap.android.sdk.displayunits.DisplayUnitListener
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit
import com.clevertap.demo.databinding.ActivityCustomTemplateBinding
import com.clevertap.templates.TemplateRenderer
import com.clevertap.templates.nd.NativeDisplayListener

class CustomTemplateActivity : BaseActivity(), NativeDisplayListener, DisplayUnitListener {

    private var binding: ActivityCustomTemplateBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApp.getCleverTapDefaultInstance()?.pushEvent("ND Stories")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_template)

        MyApp.getCleverTapDefaultInstance()?.setDisplayUnitListener(this)

        with(binding) {
            this!!.buttonGifPush.setOnClickListener {
                MyApp.getCleverTapDefaultInstance()?.pushEvent("GIF push")
            }
        }

        with(binding) {
            this!!.buttonProgressbarPush.setOnClickListener {
                MyApp.getCleverTapDefaultInstance()?.pushEvent("Progressbar push")
            }
        }

        with(binding) {
            this!!.buttonPipVideo.setOnClickListener {
                MyApp.getCleverTapDefaultInstance()?.pushEvent("PIP Video")
            }
        }

        with(binding) {
            this!!.buttonCustomButton.setOnClickListener {
                MyApp.getCleverTapDefaultInstance()?.pushEvent("Custom Button")
            }
        }

        with(binding) {
            this!!.buttonTest.setOnClickListener {
                MyApp.getCleverTapDefaultInstance()?.pushEvent("Test Button Click")
            }
        }
    }

    override fun onSuccess(id: String?) {
        clevertapDefaultInstance!!.pushDisplayUnitViewedEventForID(id)
    }

    override fun onFailure(id: String?) {
    }

    override fun onClick(resId: Int, id: String?, deepLink: String?) {
        clevertapDefaultInstance!!.pushDisplayUnitClickedEventForID(id)
    }

    override fun onDisplayUnitsLoaded(units: ArrayList<CleverTapDisplayUnit>?) {
        for (i in 0 until units!!.size) {
            val unit = units[i]
            if (unit.customExtras["nd_id"].equals("nd_pip_video")) {
                TemplateRenderer.getInstance().showNativeDisplay(
                    R.id.pip_fragment,
                    supportFragmentManager,
                    unit.jsonObject,
                    this
                )
            } else if (unit.customExtras["nd_id"].equals("nd_custom_button")) {
                TemplateRenderer.getInstance().animateButton(
                    applicationContext,
                    binding!!.root as ViewGroup?,
                    unit.jsonObject,
                    this
                )
            } else if (unit.customExtras["nd_id"].equals("nd_stories")) {
                binding?.recyclerViewStory?.adapter = TemplateRenderer.getInstance().displayStories(
                    this,
                    unit.jsonObject,
                    true
                )
                binding?.recyclerViewStory?.adapter?.notifyDataSetChanged()
            }
        }
    }
}