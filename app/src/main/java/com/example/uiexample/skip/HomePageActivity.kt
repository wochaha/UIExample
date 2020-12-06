package com.example.uiexample.skip

import android.os.Bundle
import com.example.uiexample.BaseActivity
import com.example.uiexample.R
import com.example.uiexample.ToastUtils
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.init("首页")
        initView()
        handleExtras()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home_page
    }

    private fun initView() {
        selected_time.text = "选择的时间: "
        user_account.text = "账号: "
        user_password.text = "密码: "
    }

    private fun handleExtras() {
        val extrasIntent = intent ?: return
        val bundle = extrasIntent.getBundleExtra("extras") ?: return
        val fromWhere = bundle.getString("from_where") ?: return
        handleFromWhere(fromWhere, bundle)
    }

    private fun handleFromWhere(from: String, extras:Bundle) {
        when(from) {
            CONTEXT_MENU -> {
                ToastUtils.get(this).setText("来自上下文菜单页面")
            }
            COMMON_DIALOG -> {
                ToastUtils.get(this).setText("来自输入框页面")
                refreshUserInfo(extras)
            }
            TIME_PICKER -> {
                ToastUtils.get(this).setText("来自时间选择页面")
                refreshTime(extras)
            }
        }
    }

    private fun refreshUserInfo(extras: Bundle) {
        val ac = extras.getString("account") ?: return
        val ps = extras.getString("password") ?: return
        user_account.text = "账号: $ac"
        user_password.text = "密码: $ps"
    }

    private fun refreshTime(extras: Bundle) {
        val date = extras.getString("date") ?: return
        val time = extras.getString("time") ?: return
        selected_time.text = "选择的时间: ${date}${time}"
    }
}