package me.mfathy.airlinesbook.data.store.remote.model


import com.google.gson.annotations.SerializedName

/**
 * ErrorResponse models.
 */

data class ProcessingErrors(@SerializedName("ProcessingError")
                            val processingError: ProcessingError)


data class ProcessingError(@SerializedName("@RetryIndicator")
                           val RetryIndicator: String = "",
                           @SerializedName("Type")
                           val type: String = "",
                           @SerializedName("Description")
                           val description: String = "",
                           @SerializedName("InfoURL")
                           val infoURL: String = "",
                           @SerializedName("Code")
                           val code: Int = 0)


data class ErrorResponse(@SerializedName("ProcessingErrors")
                         val processingErrors: ProcessingErrors)


