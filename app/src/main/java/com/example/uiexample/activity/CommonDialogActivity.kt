package com.example.uiexample.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import com.example.uiexample.R
import kotlinx.android.synthetic.main.activity_common_dialog.*
import java.lang.StringBuilder

class CommonDialogActivity : BaseActivity() {
    private lateinit var inputDialogView: View
    private lateinit var account: TextView
    private lateinit var password: TextView

    private val accountBuilder = StringBuilder()
    private val passwordBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.init("对话框")
        initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_common_dialog
    }

    private fun initView() {
        common_dialog.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.show()
        }

        inputDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_input, null)
        account = inputDialogView.findViewById(R.id.account)
        password = inputDialogView.findViewById(R.id.password)

        val dialog = AlertDialog.Builder(this)
            .setView(inputDialogView)
            .setPositiveButton("确定") {dialog, which ->
                showUserInfo(account.text.toString(), password.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("取消") {dialog, which ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()

        input_dialog.setOnClickListener {
            dialog.show()
        }
    }

    private fun showUserInfo(account: String, password: String) {
        accountBuilder.clear()
        passwordBuilder.clear()
        accountBuilder.append(account)
        passwordBuilder.append(password)
        show_account.text = account
        show_password.text = password
    }

    override fun setExtras() {
        extras.putString("from_where", COMMON_DIALOG)
        extras.putString("account", accountBuilder.toString())
        extras.putString("password", passwordBuilder.toString())
    }
}