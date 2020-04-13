package com.sinhro.memesapp_surf.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import com.sinhro.memesapp_surf.CustomDebugger.CustomDebug
import com.sinhro.memesapp_surf.R
import com.sinhro.memesapp_surf.domain.User
import com.sinhro.memesapp_surf.model.Login.LoginService
import com.sinhro.memesapp_surf.model.Storage.*
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes
import java.lang.Math.min

class LoginActivity : AppCompatActivity() {
    lateinit var login_text_field_boxes: TextFieldBoxes
    lateinit var password_text_field_boxes: TextFieldBoxes
    lateinit var login_extended_edit_text: ExtendedEditText
    lateinit var password_extended_edit_text: ExtendedEditText
    lateinit var custom_button_log_in: CustomLoadingButton

    val loginService = LoginService()
    lateinit var storage: Storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViews()

        login_extended_edit_text.addTextChangedListener(
            createOnLoginTextChangedListener()
        )
        password_text_field_boxes.endIconImageButton.setOnClickListener(
            createOnEyeClickListener()
        )
        password_extended_edit_text.addTextChangedListener(
            createOnPasswordTextChangedListener()
        )
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

    private fun createOnLoginTextChangedListener() = object : TextWatcher {
        private lateinit var prevCS: CharSequence
        private var newString: String? = null
        override fun afterTextChanged(s: Editable?) {
            custom_button_log_in.setStateDefault()
            newString?.let {
                if (prevCS.toString() != it)
                    s?.replace(0, s.length, newString)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            prevCS = s ?: ""
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                if (s.isBlank()) {
                    newString = ""
                    return
                }

                val digitVal = it.toString().filter { it.isDigit() }
                val len = digitVal.length
                val sb: StringBuilder = java.lang.StringBuilder()
                sb.append(digitVal.elementAt(0))
                if (len > 1) {
                    sb.append(" (")
                    sb.append(digitVal.subSequence(1, min(len, 4)))
                    sb.append(")")
                }
                if (len > 4) {
                    sb.append(" ")
                    sb.append(digitVal.subSequence(4, min(len, 7)))
                }
                if (len > 7) {
                    sb.append(" ")
                    sb.append(digitVal.subSequence(7, min(len, 9)))
                }
                if (len > 9) {
                    sb.append(" ")
                    sb.append(digitVal.subSequence(9, min(len, 11)))
                }
                newString = sb.toString()
            }
        }
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
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        else
                            InputType.TYPE_NUMBER_VARIATION_PASSWORD
                        )
        }
    }

    private fun createOnPasswordTextChangedListener() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            custom_button_log_in.setStateDefault()
            s?.let {
                shouldShowHelp(
                    s.length < resources.getInteger(R.integer.minCountCharactersInPass)
                )
            }
        }

        private fun shouldShowHelp(isShow: Boolean) {
            if (isShow)
                password_text_field_boxes.helperText =
                    getString(R.string.PasswordErrorMinCountSymb)
            else
                password_text_field_boxes.helperText = ""
        }

        private fun shouldShowErr(isShow: Boolean) {
            if (isShow)
                password_text_field_boxes.setError(
                    getString(R.string.PasswordErrorMinCountSymb),
                    false
                )
            else
                password_text_field_boxes.removeError()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }

    private fun createOnButtonLogInClickListener() = object : View.OnClickListener {
        override fun onClick(v: View?) {
            val digitLogin = login_extended_edit_text.text.toString().filter { it.isDigit() }
            val pass = password_extended_edit_text.text.toString()

            if (digitLogin.isBlank())
                login_text_field_boxes.setError(getString(R.string.FieldCantBeEmpty), false)
            if (pass.isBlank())
                password_text_field_boxes.setError(getString(R.string.FieldCantBeEmpty), false)

            if (digitLogin.length == 11 &&
                pass.length >= resources.getInteger(R.integer.minCountCharactersInPass)
            )
                onLoginAndPassReady(digitLogin, pass)
        }
    }

    private fun onLoginAndPassReady(digitLogin: String, pass: String) {
        CustomDebug.logValue("Login", digitLogin)
        CustomDebug.logValue("Pass", pass)
        var succes = false

        custom_button_log_in.setStateLoading()
        loginService.login(
            digitLogin, pass,
            {
                saveUser(it)
                custom_button_log_in.setStateDefault()
                succes = true
                onSuccessLoginAction()
            },
            {
                SnackbarHelper.showErrorMessage(
                    custom_button_log_in,
                    getString(R.string.ErrorDuringRequest)
                )
            }
        )

        Handler().postDelayed({
            if (!succes) {
                loginService.cancel()
                SnackbarHelper.showErrorMessage(
                    custom_button_log_in,
                    getString(R.string.ErrorDuringRequest)
                )
                custom_button_log_in.setStateDefault()
            }
        }, 10000)
    }

    private fun saveUser(user : User){
        storage.save(PREF_NAME_TOKEN, user.token)
        storage.save(PREF_NAME_ID, user.userInfo.id.toString())
        storage.save(PREF_NAME_USERNAME, user.userInfo.username)
        storage.save(PREF_NAME_FIRSTNAME, user.userInfo.firstName)
        storage.save(PREF_NAME_LASTNAME, user.userInfo.lastName)
        storage.save(PREF_NAME_USERDESCRIPTION, user.userInfo.userDescription)
    }

    private fun onSuccessLoginAction() {
        val tok = storage.get(PREF_NAME_TOKEN)
        val id = storage.get(PREF_NAME_ID)
        val usrname = storage.get(PREF_NAME_USERNAME)
        val fname = storage.get(PREF_NAME_FIRSTNAME)
        val lname = storage.get(PREF_NAME_LASTNAME)
        val usrdescr = storage.get(PREF_NAME_USERDESCRIPTION)
        CustomDebug.log("{user info from shared prefs} : $tok $id $usrname $fname $lname $usrdescr ")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}