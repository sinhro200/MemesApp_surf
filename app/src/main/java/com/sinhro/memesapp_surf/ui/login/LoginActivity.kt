package com.sinhro.memesapp_surf.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.customDebugger.CustomDebug
import com.sinhro.memesapp_surf.domain.User
import com.sinhro.memesapp_surf.model.login.LoginRequest
import com.sinhro.memesapp_surf.model.login.LoginService
import com.sinhro.memesapp_surf.storage.*
import com.sinhro.memesapp_surf.ui.SnackbarHelper
import com.sinhro.memesapp_surf.ui.main.MainActivity
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes


class LoginActivity : AppCompatActivity() {
    lateinit var login_text_field_boxes: TextFieldBoxes
    lateinit var password_text_field_boxes: TextFieldBoxes
    lateinit var login_extended_edit_text: ExtendedEditText
    lateinit var password_extended_edit_text: ExtendedEditText
    lateinit var custom_button_log_in: CustomLoadingButton

    private val loginService = LoginService()
    lateinit var storage: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViews()

        EditTextChangeHelper.applyOnLoginTextChanged(
            login_extended_edit_text
        )
        login_extended_edit_text.setOnKeyListener(createOnLoginEditTextEnterKeyListener())

        password_text_field_boxes.endIconImageButton.setOnClickListener(
            createOnEyeClickListener()
        )
        password_extended_edit_text.addTextChangedListener(
            createOnPasswordTextChangedListener()
        )
        password_extended_edit_text.setOnKeyListener(createOnPasswordEditTextEnterKeyListener())
        custom_button_log_in.setOnClickListener(
            createOnButtonLogInClickListener()
        )

        storage = Storage(applicationContext)
    }

    private fun findViews() {
        login_text_field_boxes = findViewById(R.id.login_text_field_boxes)
        password_text_field_boxes = findViewById(R.id.password_text_field_boxes)
        login_extended_edit_text = findViewById(R.id.login_extended_edit_text)
        password_extended_edit_text = findViewById(R.id.password_extended_edit_text)
        custom_button_log_in = findViewById(R.id.custom_log_in_btn)
    }

    private fun createOnLoginEditTextEnterKeyListener() = object : View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                password_extended_edit_text.requestFocus()
                return true
            }
            return false
        }
    }

    private fun createOnPasswordEditTextEnterKeyListener() = object : View.OnKeyListener{
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action == KeyEvent.ACTION_DOWN
                    && keyCode == KeyEvent.KEYCODE_ENTER) {
                        v?.let { hideKeyboardFrom(applicationContext,v)  }
                        custom_button_log_in.performClick()
                        return true
                }
            return false
        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun createOnEyeClickListener() = object : View.OnClickListener {
        private var canShowPass: Boolean =
            (password_extended_edit_text.inputType and InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == 0

        override fun onClick(v: View?) {
            canShowPass = !canShowPass
            val selectionPosition = password_extended_edit_text.selectionEnd
            if (canShowPass) {
                setInputTypeVisible(true)
                password_text_field_boxes.setEndIcon(R.drawable.eye)
            } else {
                setInputTypeVisible(false)
                password_text_field_boxes.setEndIcon(R.drawable.eye_closed)
            }
            password_extended_edit_text.setSelection(selectionPosition)
        }

        private fun setInputTypeVisible(isVisible: Boolean) {
            password_extended_edit_text.inputType =
                InputType.TYPE_CLASS_NUMBER or (
                        if (isVisible)
                            InputType.TYPE_NUMBER_VARIATION_NORMAL
                        else
                            InputType.TYPE_NUMBER_VARIATION_PASSWORD
                        )
        }
    }

    private fun createOnPasswordTextChangedListener() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s?.let {
                showHelp(
                    s.length < resources.getInteger(R.integer.minCountCharactersInPass)
                )
            }
        }

        private fun showHelp(isShow: Boolean) {
            if (isShow)
                password_text_field_boxes.helperText =
                    getString(R.string.PasswordErrorMinCountSymb)
            else
                password_text_field_boxes.helperText = ""
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    private fun createOnButtonLogInClickListener() = View.OnClickListener {
        val digitLogin =
            login_extended_edit_text.text
                .toString()
                .filter {
                    it.isDigit()
                }
        val pass =
            password_extended_edit_text.text
                .toString()

        if (digitLogin.isBlank())
            login_text_field_boxes.setError(
                getString(R.string.FieldCantBeEmpty), false
            )
        if (pass.isBlank())
            password_text_field_boxes.setError(
                getString(R.string.FieldCantBeEmpty), false
            )
        if (digitLogin.length < resources.getInteger(R.integer.countCharactersInLogin))
            login_text_field_boxes.setError(null,false)

        if (digitLogin.length == resources.getInteger(R.integer.countCharactersInLogin) &&
            pass.length >= resources.getInteger(R.integer.minCountCharactersInPass)
        )
            onLoginAndPassReady(digitLogin, pass)
    }

    private fun onLoginAndPassReady(digitLogin: String, pass: String) {
        CustomDebug.logValue("Login", digitLogin)
        CustomDebug.logValue("Pass", pass)

        custom_button_log_in.setStateLoading()
        loginService.login(
            LoginRequest(digitLogin, pass),
            {
                saveUser(it)
                custom_button_log_in.setStateDefault()
                onSuccessLoginAction()
            },
            {
                custom_button_log_in.setStateDefault()
                SnackbarHelper.showErrorMessage(
                    custom_button_log_in,
                    getString(R.string.ErrorDuringRequest)
                )
            }
        )
    }

    private fun saveUser(user: User) {
        storage.save(PREF_NAME_TOKEN, user.token)
        storage.save(PREF_NAME_ID, user.userInfo.id.toString())
        storage.save(PREF_NAME_USERNAME, user.userInfo.username)
        storage.save(PREF_NAME_FIRSTNAME, user.userInfo.firstName)
        storage.save(PREF_NAME_LASTNAME, user.userInfo.lastName)
        storage.save(PREF_NAME_USERDESCRIPTION, user.userInfo.userDescription)
    }

    private fun onSuccessLoginAction() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}