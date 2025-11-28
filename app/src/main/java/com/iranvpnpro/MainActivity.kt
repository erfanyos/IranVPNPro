package com.iranvpnpro

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtStatus = findViewById<TextView>(R.id.txtStatus)
        val listView = findViewById<ListView>(R.id.listView)

        txtStatus.text = "Loading subscription..."

        Thread {
            val data = SubscriptionLoader.loadSubscription()
            if (data == null) {
                runOnUiThread { txtStatus.text = "Error loading subscription" }
                return@Thread
            }

            val lines = data.split("\n").filter { it.trim().isNotEmpty() }

            val serversWithPing = lines.map { link ->
                val host = extractHost(link)
                val ping = PingAnalyzer.ping(host)
                Pair(link, ping)
            }.sortedBy { it.second }

            runOnUiThread {
                txtStatus.text = "Loaded ${serversWithPing.size} servers"

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    serversWithPing.map { "${it.first}\nPing: ${it.second}ms" }
                )
                listView.adapter = adapter
            }
        }.start()
    }

    private fun extractHost(link: String): String {
        return try {
            link.substringAfter("@").substringBefore(":")
        } catch (e: Exception) {
            "google.com"
        }
    }
}
