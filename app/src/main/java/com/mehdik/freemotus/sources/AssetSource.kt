package com.mehdik.freemotus.sources

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object AssetsSource {
    fun readAssetFile(
        context: Context,
        fileName: String
    ): String {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.use { it.readText() }
    }
}
