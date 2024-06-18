package br.com.siatiquosque.digidex_shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform