package io.github.landerlyoung.awesometodo

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : Activity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var log: TextView
    private var someDialog: Lazy<Dialog> = lazy {

        Log.i(TAG, "lazy create some Dialog")

        AlertDialog.Builder(this)
                .setMessage("test finished")
                .setPositiveButton("ok", null)
                .create()
    }

    private val delegateLazyDialog by lazy {
        Dialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        log = findViewById(R.id.log) as TextView
        findViewById(R.id.run_test).setOnClickListener {
            runTest()
        }
    }

    override fun onResume() {
        super.onResume()
        runTest()
    }

    fun runTest() {
        val test = KotlinTest()
        test.run()
        log.text = test.logMsg

        someDialog.value.show()
    }

}
