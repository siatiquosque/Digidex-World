package br.com.siatiquosque.digidexworld

import android.os.Build
import br.com.siatiquosque.digidexworld.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()