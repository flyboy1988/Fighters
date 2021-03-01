package com.vosouq.vsq.utils

import java.io.File
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object CreateResponseBody {
    private fun createRequestBody(file: File): RequestBody {
        return file.asRequestBody("multipart/form-data".toMediaType())
    }

    fun createPart(file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData("file", file.name, createRequestBody(file))
    }

    fun createPartFromString(descriptionString: String): RequestBody {
        return descriptionString.toRequestBody("multipart/form-data".toMediaType());
    }

}
