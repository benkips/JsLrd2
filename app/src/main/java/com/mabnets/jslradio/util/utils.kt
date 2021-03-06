package com.mabnets.jslradio.util

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkBuilder
import com.google.android.material.snackbar.Snackbar
import com.mabnets.jslradio.MainActivity
import com.mabnets.jslradio.R

fun Context.gotoscreen(screen:String): PendingIntent {
    val REQUEST_CODE=675646
    val taskDetailIntent = Intent(
        Intent.ACTION_VIEW,
        "https://com.mabnets.jslradio/${screen}".toUri(),
        this,
        MainActivity::class.java
    )
    val pendingintent: PendingIntent = TaskStackBuilder.create(this).run {
        addNextIntentWithParentStack(taskDetailIntent)
        getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    return pendingintent
}
fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()

}

fun Context.getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
    var drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        drawable = DrawableCompat.wrap(drawable).mutate()
    }

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    ) ?: return null
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}
fun Context.showmessages(
    Title: String,
    message: String,
    retry: (() -> Unit)? = null
) {
    AlertDialog.Builder(this).apply {
        setTitle(Title)
        setMessage(message)
        setCancelable(false)
        setPositiveButton("Ok") { _, _ -> retry?.invoke() }
    }.show()
}

fun Context.showPermissionRequestExplanation(
    permission: String,
    message: String,
    retry: (() -> Unit)? = null
) {
    AlertDialog.Builder(this).apply {

        setTitle("$permission Required")
        setMessage(message)
        setPositiveButton("Ok") { _, _ -> retry?.invoke() }
    }.show()
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
fun Context.permissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
