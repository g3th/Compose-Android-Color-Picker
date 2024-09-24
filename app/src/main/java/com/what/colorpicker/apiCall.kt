package com.what.colorpicker

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL


fun apiCall(r: Int, g: Int, b: Int): String{
    val page = "https://www.thecolorapi.com/id?rgb=${r},${g},${b}"
    try {
        val url: URL = URI.create(page).toURL()
        val connect: HttpURLConnection = url.openConnection() as HttpURLConnection
        val reader = BufferedReader(InputStreamReader(connect.inputStream))
        val json = reader.readLine()
        return json.split("name")[1].split(":\"")[1].split("\"")[0]
    } catch (e: Exception) {
        val error = "${e.message} in module: apiCall()"
        Log.d(error, e.toString())
        return "Connection Error (see Logs for Details)"
    }
}