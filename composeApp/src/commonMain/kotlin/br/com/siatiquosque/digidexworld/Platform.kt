package br.com.siatiquosque.digidexworld

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform