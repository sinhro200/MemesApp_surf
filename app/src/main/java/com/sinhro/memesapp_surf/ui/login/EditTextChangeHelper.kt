package com.sinhro.memesapp_surf.ui.login

import android.widget.EditText
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class EditTextChangeHelper {

    companion object{
        private fun initLoginMask() :MaskImpl {
            val loginMask = MaskImpl.createTerminated(
                arrayOf(
                    PredefinedSlots.digit(),
                    PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
                    PredefinedSlots.hardcodedSlot('(').withTags(Slot.TAG_DECORATION),
                    PredefinedSlots.digit(),
                    PredefinedSlots.digit(),
                    PredefinedSlots.digit(),
                    PredefinedSlots.hardcodedSlot(')').withTags(Slot.TAG_DECORATION),
                    PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
                    PredefinedSlots.digit(),
                    PredefinedSlots.digit(),
                    PredefinedSlots.digit(),
                    PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
                    PredefinedSlots.digit(),
                    PredefinedSlots.digit(),
                    PredefinedSlots.hardcodedSlot(' ').withTags(Slot.TAG_DECORATION),
                    PredefinedSlots.digit(),
                    PredefinedSlots.digit()
                )
            )
            loginMask.isForbidInputWhenFilled = true
            return loginMask
        }
        private val loginMask =
            initLoginMask()

        fun applyOnLoginTextChanged(editText : EditText){
            MaskFormatWatcher(loginMask)
                .installOn(editText)
        }
    }

    /*
    private fun applyOnLoginTextChanged_mine(et : EditText){
        et.addTextChangedListener(
            object : TextWatcher {
                private lateinit var prevCS: CharSequence
                private var newString: String? = null
                override fun afterTextChanged(s: Editable?) {
                    newString?.let {
                        if (prevCS.toString() != it)
                            s?.replace(0, s.length, newString)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    prevCS = s ?: ""
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let {
                        if (s.isBlank()) {
                            newString = ""
                            return
                        }
                        val selectionPosition = et.selectionEnd

                        val digitVal = it.toString().filter { it.isDigit() }
                        val len = digitVal.length
                        val sb: StringBuilder = java.lang.StringBuilder()
                        sb.append(digitVal.elementAt(0))
                        if (len > 1) {
                            sb.append(" (")
                            sb.append(digitVal.subSequence(1, Math.min(len, 4)))
                            sb.append(")")
                        }
                        if (len > 4) {
                            sb.append(" ")
                            sb.append(digitVal.subSequence(4, Math.min(len, 7)))
                        }
                        if (len > 7) {
                            sb.append(" ")
                            sb.append(digitVal.subSequence(7, Math.min(len, 9)))
                        }
                        if (len > 9) {
                            sb.append(" ")
                            sb.append(digitVal.subSequence(9, Math.min(len, 11)))
                        }
                        newString = sb.toString()
                        et.setSelection(selectionPosition)
                    }
                }
            }
        )
    }
    */
}