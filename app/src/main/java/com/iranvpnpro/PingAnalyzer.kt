package com.iranvpnpro

import java.net.InetAddress

object PingAnalyzer {

    fun ping(host: String): Int {
        return try {
            val start = System.currentTimeMillis()
            val address = InetAddress.getByName(host)
            val reachable = address.isReachable(800)
            if (!reachable) return 9999
            (System.currentTimeMillis() - start).toInt()
        } catch (e: Exception) {
            9999
        }
    }
}
