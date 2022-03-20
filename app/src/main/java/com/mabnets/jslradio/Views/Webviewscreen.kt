package com.mabnets.jslradio.Views

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun Webviewscreen(urls:String){
  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxSize()
  ) {
    Column {
     AndroidView(factory = {
         WebView(it).apply {
             webViewClient=object :WebViewClient(){
                 override fun shouldOverrideUrlLoading(
                     view: WebView?,
                     request: WebResourceRequest?
                 ): Boolean {
                     return false
                 }
             }
         }
     }, update = {
         it.loadUrl("https://"+urls)
     })
    }
  }
}
