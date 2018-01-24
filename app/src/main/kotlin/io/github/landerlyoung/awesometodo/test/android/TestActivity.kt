package io.github.landerlyoung.awesometodo.test.android

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import io.github.landerlyoung.awesometodo.paging.PagingActivity
import io.github.landerlyoung.awesometodo.test.KotlinTest
import org.jetbrains.anko.*

class TestActivity : AppCompatActivity() {
    companion object {
        const val TAG = "TestActivity"
    }

    private lateinit var log: TextView
    private val testSnackbar: Snackbar? by lazy {
        Log.i(TAG, "lazy create some snackbar")

        Snackbar.make(log, "Run test finished", Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(UI {
            verticalLayout {
                lparams(matchParent, matchParent)
                padding = dip(16)

                log = textView {
                    lparams(matchParent, 0)
                    (layoutParams as LinearLayout.LayoutParams).weight = 1.toFloat()
                    textSize = 16.toFloat()
                }

                button("Run Test") {
                    lparams(matchParent, wrapContent)

                    setOnClickListener {
                        runTest()
                    }
                }

                button("Paging Activity") {
                    lparams(matchParent, wrapContent)

                    setOnClickListener {
                        startActivity(Intent(this@TestActivity, PagingActivity::class.java))
                    }
                }
            }
        }.view)

        runTest()
    }

    fun runTest() {
        val test = KotlinTest()
        test.run()
        log.text = test.logMsg
        testSnackbar?.show()
    }
}
