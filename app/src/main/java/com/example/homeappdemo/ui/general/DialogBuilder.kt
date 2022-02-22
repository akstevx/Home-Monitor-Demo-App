package com.example.homeappdemo.ui.general

import android.app.Activity
import androidx.core.content.ContextCompat
import com.example.awesomedialog.*
import com.example.homeappdemo.R

fun showDialog(
    activity: Activity,
    title: String = "Successful",
    body: String,
    positiveText: String = "Continue",
    action: () -> Unit
) {
    AwesomeDialog.build(activity)
        .title(
            title,
            titleColor = ContextCompat.getColor(activity.applicationContext, R.color.white)
        )
        .body(
            body,
            color = ContextCompat.getColor(activity.applicationContext, R.color.white)
        )
        .icon(R.drawable.successful_ic)
        .background(R.drawable.layout_rounded_green)
        .onPositive(
            positiveText,
            buttonBackgroundColor = R.drawable.layout_rounded_white,
            textColor = ContextCompat.getColor(activity.applicationContext, R.color.primaryColor)
        ) {
            action()
        }
}

fun showNegativeDialog(
    activity: Activity,
    body: String,
    action: () -> Unit
) {
    AwesomeDialog.build(activity)
        .title(
            activity.applicationContext.getString(R.string.error),
            titleColor = ContextCompat.getColor(activity.applicationContext, R.color.hint_color)
        )
        .body(body)
        .icon(R.drawable.error_ic)
        .background(R.drawable.layout_rounded_white)
        .onNegative(
            "cancel",
            buttonBackgroundColor = R.drawable.layout_rounded_red,
            textColor = ContextCompat.getColor(activity.applicationContext, R.color.white)
        ) {
            action()
        }
}

fun showDialogWithNegative(
    activity: Activity,
    body: String,
    action: () -> Unit
) {
    AwesomeDialog.build(activity)
        .title(
            activity.applicationContext.getString(R.string.are_you),
            titleColor = ContextCompat.getColor(activity.applicationContext, R.color.hint_color)
        )
        .body(body)
        .background(R.drawable.layout_rounded_white)
        .onPositive(
            activity.applicationContext.getString(R.string.cont),
            buttonBackgroundColor = R.drawable.layout_rounded_red,
            textColor = ContextCompat.getColor(activity.applicationContext, R.color.white)
        ) {
            action()
        }
        .onNegative(
            activity.applicationContext.getString(R.string.english),
            buttonBackgroundColor = R.drawable.layout_rounded_green,
            textColor = ContextCompat.getColor(activity.applicationContext, R.color.white)
        ) { }
}

fun showLanguageDialog(
    activity: Activity,
    body: String,
    englishAction: () -> Unit,
    frenchAction: () -> Unit
) {
    AwesomeDialog.build(activity)
        .title(
            activity.applicationContext.getString(R.string.are_you),
            titleColor = ContextCompat.getColor(activity.applicationContext, R.color.hint_color)
        )
        .body(body)
        .background(R.drawable.layout_rounded_white)
        .onPositive(
            activity.applicationContext.getString(R.string.french),
            buttonBackgroundColor = R.drawable.layout_rounded_red,
            textColor = ContextCompat.getColor(activity.applicationContext, R.color.white)
        ) {
            frenchAction()
        }
        .onNegative(
            activity.applicationContext.getString(R.string.english),
            buttonBackgroundColor = R.drawable.layout_rounded_green,
            textColor = ContextCompat.getColor(activity.applicationContext, R.color.white)
        ) {
            englishAction()
        }
}