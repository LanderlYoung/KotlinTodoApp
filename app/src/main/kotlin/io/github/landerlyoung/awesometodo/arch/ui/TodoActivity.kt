package io.github.landerlyoung.awesometodo.arch.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.github.landerlyoung.awesometodo.test.android.TestActivity

class TodoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, TodoFragment())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("test")?.let {
            it.setIcon(android.R.drawable.ic_menu_send)
            it.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.title == "test") {
            startActivity(Intent(this, TestActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
