package com.example.homeappdemo.util.extensions

import java.text.DecimalFormat


fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.toLowerCase().capitalize() }


fun String.formatNumber():String{
    return try {
        DecimalFormat("#,##0").format(java.lang.Double.parseDouble(this)).toString()
    } catch (ex:java.lang.Exception){
        "0"
    }
}