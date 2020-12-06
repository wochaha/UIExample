package com.example.uiexample.others

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uiexample.R
import com.example.uiexample.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

class OtherPageActivity : AppCompatActivity() {
    private lateinit var fruitAdapter : ArrayAdapter<String>
    private lateinit var checkboxAdapter: CheckBoxAdapter
    private val selectedFruit = arrayListOf<String>()
    val list = arrayListOf("苹果", "梨子", "香蕉", "火龙果", "石榴", "柚子")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content = list.mapIndexed { index, s ->
            val c = (index + 'A'.toInt()).toChar()
            return@mapIndexed "${c}.$s"
        }
        fruitAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, content)
        checkboxAdapter = CheckBoxAdapter(arrayListOf("_","_","_","_","_")) { isSelected, content ->
            if (isSelected) {
                ToastUtils.get(this).setText(content).show()
            }
            progress.incrementProgressBy(if (isSelected) 1 else -1)
        }
        list_view.setOnItemClickListener { parent, view, position, id ->
            updateCheckBox(list[position])
        }
        list_view.divider = null
        list_view.adapter = fruitAdapter
        checkbox_list.layoutManager = LinearLayoutManager(this)
        checkbox_list.adapter = checkboxAdapter
    }

    private fun updateCheckBox(str: String) {
        val arrays = arrayListOf<String>()
        val contains = selectedFruit.contains(str)
        if (contains) {
            selectedFruit.remove(str)
        } else {
            if (selectedFruit.size > 4) return
            selectedFruit.add(str)
        }
        selectedFruit.forEach {
            if (!it.equals("_")){
                arrays.add(it)
            }
        }
        val diff = 5 - arrays.size
        for (index in 0 until diff) {
            arrays.add("_")
        }
        checkboxAdapter.updateData(arrays)
    }
}