package com.example.networktest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListeners()
        initData()
    }

    open protected fun initData() {

    }

    open protected fun initListeners() {

    }

    /*protected fun showToast(msg: String) {
            runOnUiThread { toast(msg) }
        }*/


    protected abstract fun getLayoutId(): Int
}
