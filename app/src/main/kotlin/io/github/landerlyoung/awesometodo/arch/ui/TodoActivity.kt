package io.github.landerlyoung.awesometodo.arch.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, TodoFragment())
                    .commitNow()
        }
    }
}
