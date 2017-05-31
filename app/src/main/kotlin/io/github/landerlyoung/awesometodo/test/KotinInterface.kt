package io.github.landerlyoung.awesometodo.test

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-31
 * Time:   20:07
 * Life with Passion, Code with Creativity.
 * </pre>
 */

interface Fruit {
    fun liquid(): String {
        return "${this.javaClass.simpleName} juice"
    }

    fun price(): Int
}

interface Beverage {
    fun liquid(): String {
        return "CockCola";
    }
}

open class Apple : Fruit {
    override fun price(): Int = 100
}

open class Pear : Fruit {
    override fun price(): Int = 10
}

class ApplePear : Apple(), Beverage {
    override fun liquid(): String {
        return super<Beverage>.liquid()
    }

    fun nonNullCallJava(name: String, app: Application, pref: SharedPreferences, context: Context): Unit {
        (app.baseContext.getSystemService(name) as ActivityManager)
                .isLowRamDevice
    }
}

