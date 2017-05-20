package io.github.landerlyoung.awesometodo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
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
    private var someDialog: Lazy<Dialog> = lazy {

        Log.i(TAG, "lazy create some Dialog")

        AlertDialog.Builder(this)
                .setMessage("test finished")
                .setPositiveButton("ok", null)
                .create()
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
    }

    override fun onResume() {
        super.onResume()
        runTest()
    }

    fun runTest() {
        val test = KotlinTest()
        test.run()
        log?.text = test.logMsg

        someDialog.value.show()
    }

}
