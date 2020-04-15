package com.sinhro.memesapp_surf.customDebugger

import android.util.Log

class CustomDebug private constructor() {

    companion object {
        private val customDebug = CustomDebug()
        fun log(text: String) = customDebug.log(text)
        fun logValue(name: String, value: String) = customDebug.logValue(name, value)
    }

    private fun log(text: String) {
        if (SHOULD_DEBUG) {
            customPrint(CUSTOM_DEBUGGER_TAG, text)
        }
    }

    private fun logValue(name: String, value: String) {
        if (SHOULD_DEBUG) {
            customPrint(CUSTOM_DEBUGGER_TAG, "$name [$value]")
        }
    }

    private fun customPrint(tag: String, text: String) {
        Log.i(tag, text)
    }
}