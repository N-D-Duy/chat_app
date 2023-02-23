package com.example.userlogin.adapter

import android.app.Dialog
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class ProgressDialogFragment: DialogFragment() {

    companion object{
        fun newInstance(): ProgressDialogFragment{
            return ProgressDialogFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val progressBar = ProgressBar(activity)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setView(progressBar)
        return builder.create()
    }
}