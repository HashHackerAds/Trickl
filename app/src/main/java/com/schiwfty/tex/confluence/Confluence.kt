package com.schiwfty.tex.confluence

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException
import java.net.ServerSocket


/**
 * Created by arran on 11/04/2017.
 */
object Confluence {
    lateinit var fullUrl: String
    val confluenceFileName = "confluence"
    val localhostUrl = "127.0.0.1:"
    lateinit var daemonPort: String
    val workingDir: File = File(Environment.getExternalStorageDirectory().path + File.separator + "cloudburst")
    val torrentRepo: File = File(workingDir.absolutePath + File.separator + "torrents")

    fun startConfluence(): Boolean {
        val mainThread = Thread{
            trickl.Trickl.main(workingDir.absolutePath)
        }
        mainThread.start()
        return true
    }

    private fun getAvailablePort(): Int {
        var s: ServerSocket? = null
        var streamPort = -1
        try {
            s = ServerSocket(0)
            streamPort = s.localPort
            return streamPort
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (s != null)
                try {
                    s.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

        }
        return streamPort
    }


}