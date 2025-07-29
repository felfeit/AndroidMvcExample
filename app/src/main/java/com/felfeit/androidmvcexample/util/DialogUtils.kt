package com.felfeit.androidmvcexample.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.felfeit.androidmvcexample.R

object DialogUtils {
    fun showLoadingDialog(context: Context): AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        val builder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
        return builder.create().apply {
            if (!isShowing) show()
        }
    }
}