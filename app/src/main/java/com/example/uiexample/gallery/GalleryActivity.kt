package com.example.uiexample.gallery

import android.os.Bundle
import android.view.Menu
import com.example.uiexample.R
import com.example.uiexample.ToastUtils
import com.example.uiexample.BaseActivity
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.init("故乡图册")
        initView()
    }

    private fun initView() {
        configImageWithDecoration(DataCenter.initLoad())

        previous_page.setOnClickListener {
            val data = DataCenter.previous()
            if (data == null) {
                ToastUtils.get(this).setText("已经是第一页了").show()
                return@setOnClickListener
            }
            configImageWithDecoration(data)
        }

        next_page.setOnClickListener {
            val data = DataCenter.next()
            if (data == null) {
                ToastUtils.get(this).setText("已经是最后一页了").show()
                return@setOnClickListener
            }
            configImageWithDecoration(data)
        }
    }

    private fun configImageWithDecoration(data: GalleryMetaData) {
        image_view.setImageResource(data.resId)
        decoration.text = resources.getString(data.decoration)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_gallery
    }

    override fun getContextMenuRes(): Int {
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }
}