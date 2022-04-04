package com.mabnets.jslradio.Views

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity
import android.webkit.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material.TextButton
import androidx.compose.ui.platform.LocalContext

@Composable
fun Webviewscreen(urls:String){
    val visibility = remember { mutableStateOf(false)}
    val contexts = LocalContext.current
    var mUploadMessage: ValueCallback<Uri>? = null
    var uploadMessage: ValueCallback<Array<Uri>>? = null

    val REQUEST_SELECT_FILE = 100
    val FILECHOOSER_RESULTCODE = 1

    var resultLauncher = rememberLauncherForActivityResult(contract =ActivityResultContracts.StartActivityForResult()) { result ->
        //val data: Intent? = result.data
        val intent = result.data
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // There are no request codes
            uploadMessage?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result.resultCode, intent ))
            uploadMessage = null;
        }else{
            val result: Uri? =
                if (intent == null || result.resultCode !== Activity.RESULT_OK) null else intent?.getData()
            mUploadMessage!!.onReceiveValue(result)
            mUploadMessage = null

        }
    }

  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxSize()
  ) {
   Column{
     AndroidView(factory = {
         WebView(it).apply {
             webViewClient=object :WebViewClient(){

                 override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                     try {
                         visibility.value=true
                     } catch (exception: Exception) {
                         exception.printStackTrace()
                     }

                     super.onPageStarted(view, url, favicon)
                 }

                 override fun onPageFinished(view: WebView?, url: String?) {
                     try {
                         visibility.value=false
                     } catch (exception: Exception) {
                         exception.printStackTrace()
                     }

                     super.onPageFinished(view, url)
                 }

                 override fun onReceivedError(
                     view: WebView?,
                     request: WebResourceRequest?,
                     error: WebResourceError?
                 ) {
                     super.onReceivedError(view, request, error)
                 }
             }


             webChromeClient = object : WebChromeClient() {
                 // For 3.0+ Devices (Start)
                 // onActivityResult attached before constructor
                 protected fun openFileChooser(uploadMsg: ValueCallback<Uri>?, acceptType: String?) {
                     mUploadMessage = uploadMsg
                     var i = Intent(Intent.ACTION_GET_CONTENT)
                     i.addCategory(Intent.CATEGORY_OPENABLE)
                     i.setType("/*")
                     i= Intent.createChooser(i, "File Browser")
                     resultLauncher.launch(i)

                 }

                 // For Lollipop 5.0+ Devices
                 override fun onShowFileChooser(
                     mWebView: WebView?,
                     filePathCallback: ValueCallback<Array<Uri>>?,
                     fileChooserParams: FileChooserParams
                 ): Boolean {
                     if (uploadMessage != null) {
                         uploadMessage!!.onReceiveValue(null)
                         uploadMessage = null
                     }
                     uploadMessage = filePathCallback
                     var intent: Intent? = null
                     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                         intent = fileChooserParams.createIntent()
                     }
                     resultLauncher.launch(intent)

                     return true
                 }
             }


         }
     }, update = {
         it.settings.javaScriptEnabled=true
         it.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
             if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && it.canGoBack()) {
                 it.goBack()
                 return@OnKeyListener true
             }
             false
         })

         it.loadUrl(urls)

     })
       if (visibility.value){
           pgbar(visibility.value)
       }

    }
  }


}

@Composable
fun pgbar(status:Boolean){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment=Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(if (status) 1f else 0f)
        ) {
            CircularProgressIndicator()

        }
}
