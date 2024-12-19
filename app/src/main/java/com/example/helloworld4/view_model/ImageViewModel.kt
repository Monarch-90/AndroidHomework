package com.example.helloworld4.view_model

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ImageViewModel : ViewModel() {
    fun copyImageToLocalStorage(context: Context, uri: Uri): Uri? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, "local_image_${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(file)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}