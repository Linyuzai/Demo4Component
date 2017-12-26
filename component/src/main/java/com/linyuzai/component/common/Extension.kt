package com.linyuzai.component.common

/**
 * Created by linyuzai on 2017/12/26.
 * @author linyuzai
 */

fun Int.isLeap(): Boolean = this % 4 == 0 && this % 100 != 0 || this % 400 == 0

fun Int.toHexString(length: Int = 2): String = this.toString(16).toUpperCase().addZero(length)

fun Int.toDecString(length: Int = 2): String = this.toString().addZero(length)

fun Float.isInteger(): Boolean = this - this.toInt() == 0f

fun String.addZero(length: Int = 2): String {
    var rst = this
    if (this.length < length)
        for (index in 0 until length - this.length)
            rst = "0$rst"
    return rst
}

fun Int.addZero(length: Int = 2): String = this.toString().addZero(length)