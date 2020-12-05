package com.example.uiexample.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.uiexample.R
import com.example.uiexample.ToastUtils
import kotlinx.android.synthetic.main.activity_context_menu.*

class ContextMenuActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.init("上下文菜单页")
        text_1.setText("hello world!")
        registerForContextMenu(text_1)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_context_menu
    }

    override fun onContextMenuItemSelected(menuItem: MenuItem) {
        ToastUtils.get(this).setText(menuItem.title).show()
    }

    override fun getContextMenuRes(): Int {
        return R.menu.context_menu
    }

    override fun setExtras() {
        extras.putString("from_where", CONTEXT_MENU)
    }
}