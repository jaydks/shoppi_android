package com.example.shoppi_android

import android.content.Context
import android.util.Log

class AssetLoader(private val context: Context) {

    fun getJsonString(filename: String) : String? {
        return kotlin.runCatching {  // 성공/실패로 나누어지는 경우 runCatching함수와 함께 처리 가능
            loadAsset(filename)
        }.getOrNull()
    }
     private fun loadAsset(filename: String) : String {
        return context.assets.open(filename).use { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            String(bytes)
        }
    }
}