package  com.mabnets.jslradio.Viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import java.util.*


class Mediaplayerviewmodel:ViewModel() {

     fun getfirstcolor(): Color{
         val random= Random()
         val color:Int=android.graphics.Color.argb(255,
             random.nextInt(256),
             random.nextInt(256),
             random.nextInt(256)
         )
         return Color(color)
     }

    fun getsecondcolor(): Color{
         val random= Random()
         val color:Int=android.graphics.Color.argb(255,
             random.nextInt(256),
             random.nextInt(256),
             random.nextInt(256)
         )
         return Color(color)
     }
}