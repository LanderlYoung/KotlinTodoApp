package io.github.landerlyoung.awesometodo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import io.github.landerlyoung.awesometodo.arch.ui.TodoActivity
import io.github.landerlyoung.awesometodo.test.KotlinTest

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private var log: TextView? = null
    private val testSnackbar: Snackbar? by lazy {
        Log.i(TAG, "lazy create some snackbar")

        val logView = log
        if (logView != null) {
            Snackbar.make(logView, "Run test finished", Snackbar.LENGTH_SHORT)
        } else {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        log = findViewById(R.id.log) as? TextView
        findViewById(R.id.run_test)?.setOnClickListener {
            runTest()
        }

        findViewById(R.id.run_demo)?.setOnClickListener {
            val intent = Intent(this, TodoActivity::class.java)
            startActivity(intent)
        }

        runTest()
    }

    fun runTest() {
        val test = KotlinTest()
        test.run()
        log?.text = test.logMsg

        testSnackbar?.show()
    }
}
