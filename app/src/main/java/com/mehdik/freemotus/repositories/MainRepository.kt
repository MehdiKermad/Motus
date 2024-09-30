package com.mehdik.freemotus.repositories

import android.content.Context
import com.mehdik.freemotus.sources.AssetsSource

object MainRepository {
    // TODO List of words needs to be handle here and read only once from the file
    fun readAssetFile(
        context: Context,
        fileName: String
    ) = AssetsSource.readAssetFile(context, fileName)
}