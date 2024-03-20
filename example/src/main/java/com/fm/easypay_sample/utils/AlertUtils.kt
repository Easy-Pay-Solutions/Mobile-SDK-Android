package com.fm.easypay_sample.utils

import android.app.AlertDialog
import android.content.Context

object AlertUtils {

    fun showAlert(context: Context, message: String?) {
        val alert = AlertDialog.Builder(context)
            .setCancelable(true)
            .setMessage(message)

        alert.show()
    }

}