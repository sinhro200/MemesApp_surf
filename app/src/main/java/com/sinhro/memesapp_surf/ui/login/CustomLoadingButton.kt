package com.sinhro.memesapp_surf.ui.login

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import com.sinhro.memesapp_surf.R

class CustomLoadingButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes : Int = 0
) :
    RelativeLayout(context, attributeSet,defStyle,defStyleRes) {
    lateinit var login_button: Button
    lateinit var loading_iv: ImageView
    lateinit var buttonText:String
    var loadingAnimation : Animation

    init {
        LayoutInflater.from(context).inflate(
            R.layout.view_custom_button, this, true)
        findViews()

        attributeSet?.let {
            initComponents(context.obtainStyledAttributes(it,
                R.styleable.CustomLoadingButton, 0, 0))
        }
        loadingAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_center)
        loadingAnimation.interpolator = LinearInterpolator()

        setStateDefault()
    }

    private fun initComponents(typedArray : TypedArray){
        val textBtn = resources.getText(
            typedArray.getResourceId(
                R.styleable.CustomLoadingButton_text_button,
                R.string.log_in_button
            )
        )
        val img_src = resources.getDrawable(
            typedArray.getResourceId(
                R.styleable.CustomLoadingButton_src_image_state_loading,
                R.drawable.loading
            ),
            resources.newTheme()
        )
        buttonText = textBtn.toString()
        loading_iv.setImageDrawable(img_src)

        typedArray.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        setOnClickButton(l?:return)
    }

    private fun findViews(){
        login_button = findViewById(R.id.log_in_btn_custom_button_view)
        loading_iv = findViewById(R.id.loader_iv_custom_button_view)
    }


    fun setOnClickButton(onClickListener: OnClickListener) {
        login_button.setOnClickListener(onClickListener)
    }

    fun setStateLoading() {
        login_button.isActivated = false
        login_button.isEnabled = false
        login_button.visibility = View.VISIBLE
        login_button.text = ""

        loading_iv.visibility = View.VISIBLE
        loading_iv.startAnimation(loadingAnimation)

    }

    fun setStateDefault() {
        login_button.isEnabled = true
        login_button.isActivated = true
        login_button.visibility = View.VISIBLE
        login_button.text = buttonText

        loading_iv.visibility = View.INVISIBLE
        loading_iv.clearAnimation()
    }
}