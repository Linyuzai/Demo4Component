package com.bettershine.landscape2.common.view

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.linyuzai.component.R

/**
 * Created by linyuzai on 2017/11/15.
 */
class LoadingDialog : Dialog {
    constructor(context: Context) : super(context)

    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    class Builder(private val context: Context) {
        private var message: String? = null
        private var isShowMessage = true
        private var isCancelable = false
        private var isCancelOutside = false

        fun setMessage(message: String): LoadingDialog.Builder {
            this.message = message
            return this
        }

        fun setShowMessage(isShowMessage: Boolean): LoadingDialog.Builder {
            this.isShowMessage = isShowMessage
            return this
        }

        fun setCancelable(isCancelable: Boolean): LoadingDialog.Builder {
            this.isCancelable = isCancelable
            return this
        }

        fun setCancelOutside(isCancelOutside: Boolean): LoadingDialog.Builder {
            this.isCancelOutside = isCancelOutside
            return this
        }

        fun create(): LoadingDialog {
            val inflater = LayoutInflater.from(this.context)
            val view = inflater.inflate(R.layout.dialog_loading, null as ViewGroup?)
            val loadingDialog = LoadingDialog(this.context, R.style.LoadingDialog)
            val msgText = view.findViewById<View>(R.id.tipTextView) as TextView
            if (this.isShowMessage) {
                msgText.text = this.message
            } else {
                msgText.visibility = 8
            }

            loadingDialog.setContentView(view)
            loadingDialog.setCancelable(this.isCancelable)
            loadingDialog.setCanceledOnTouchOutside(this.isCancelOutside)
            return loadingDialog
        }
    }
}