package com.example.uiexample.activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import com.example.uiexample.R
import com.example.uiexample.ToastUtils
import kotlinx.android.synthetic.main.toolbar.*
import java.sql.Time

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        const val TIME_PICKER = "time_picker"
        const val COMMON_DIALOG = "common_dialog"
        const val CONTEXT_MENU = "context_menu"
        const val HOME_PAGE = "home_page"
    }

    public val toolbar: Toolbar
        get() = common_toolbar

    public fun Toolbar.init(title: CharSequence) {
        this.title = title
        setSupportActionBar(toolbar)
    }

    public val extras: Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
//            window.navigationBarColor = Color.TRANSPARENT
            window.statusBarColor = Color.TRANSPARENT
        } else if (Build.VERSION.SDK_INT >= 19) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
        setContentView(getLayoutId())
    }

    public abstract fun getLayoutId() : Int

    open fun setExtras() {

    }

    private fun onOptionsMenuItemSelected(menuItem: MenuItem): Unit {
        ToastUtils.get(this).setText(menuItem.title).show()
        val intent = Intent()
        setExtras()
        intent.putExtra("extras", extras)
        when(menuItem.itemId) {
            R.id.context_menu -> {
                if (this is ContextMenuActivity) return
                intent.setClass(this, ContextMenuActivity::class.java)
                startActivity(intent)
            }
            R.id.common_dialog -> {
                if (this is CommonDialogActivity) return
                intent.setClass(this, CommonDialogActivity::class.java)
                startActivity(intent)
            }
            R.id.select_dialog -> {
                if (this is TimePickerActivity) return
                intent.setClass(this, TimePickerActivity::class.java)
                startActivity(intent)
            }
            R.id.home_page -> {
                if (this is HomePageActivity) return
                intent.setClass(this, HomePageActivity::class.java)
                startActivity(intent)
            }
        }
        finish()
    }

    open fun onContextMenuItemSelected(menuItem: MenuItem): Unit {

    }

    open fun getContextMenuRes(): Int {
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.popup_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onOptionsMenuItemSelected(item)
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        if (getContextMenuRes() == 0) return
        menuInflater.inflate(getContextMenuRes(), menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        onContextMenuItemSelected(item)
        return super.onContextItemSelected(item)
    }
}