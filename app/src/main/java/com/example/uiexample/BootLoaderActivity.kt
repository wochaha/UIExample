package com.example.uiexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.uiexample.custom.CustomViewActivity
import com.example.uiexample.gallery.GalleryActivity
import com.example.uiexample.music.ui.MusicActivity
import com.example.uiexample.skip.HomePageActivity
import kotlinx.android.synthetic.main.activity_boot_loader.*

class BootLoaderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.init("启动界面")
        initView()
    }

    private fun initView() {
        to_gallery.setOnClickListener {
            val intent = Intent(this,GalleryActivity::class.java)
            startActivity(intent)
        }

        to_skip.setOnClickListener {
            val intent = Intent(this,HomePageActivity::class.java)
            startActivity(intent)
        }

        to_custom_view.setOnClickListener {
            val intent = Intent(this,CustomViewActivity::class.java)
            startActivity(intent)
        }

        to_media_player.setOnClickListener {
            val intent = Intent(this,MusicActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_boot_loader
    }

    override fun getContextMenuRes(): Int {
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }
}