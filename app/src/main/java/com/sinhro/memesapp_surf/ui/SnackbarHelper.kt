package com.sinhro.memesapp_surf.ui

import android.content.Context
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.TextView
import com.sinhro.memesapp_surf.R


class SnackbarHelper {

    companion object{
        fun showErrorMessage(
            view : View,
            text : String
        ){
            val snackbar= Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            val snackBarView = snackbar.view
            snackBarView.setBackgroundResource(R.drawable.snackbar_custom)
            snackbar.show()
        }
    }


}