package matrix.com.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LowerDescriptionValue(
    val description: String,
    val value: String
)

@Serializable
data class UpperDescriptionValue(
    @SerialName("Description")
    val description: String,
    @SerialName("Value")
    val value: String
)
