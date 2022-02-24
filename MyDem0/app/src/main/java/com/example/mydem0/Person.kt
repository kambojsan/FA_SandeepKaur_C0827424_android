package com.example.mydem0

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mydem0.util.FileUtils
import com.example.mydem0.util.ImageUtils

// 1
@Entity
// 2
data class Person(
    // 3
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    // 4
    var name: String = "V",
    var gender: String = "",
    var country: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
)
   {

//    fun setImage(image: Bitmap, context: Context) {
//        id?.let {
//            ImageUtils.saveBitmapToFile(context, image, generateImageFilename(it))
//            Log.i(TAG, "New photossssssssss added to the database.")
//        }
//    }
//       fun deleteImage(context: Context) {
//           id?.let {
//               FileUtils.deleteFile(context, generateImageFilename(it))
//           }
//       }
//
//    companion object {
//        fun generateImageFilename(id: Long): String {
//            return "bookmark$id.png"
//        }
   //   }
   }