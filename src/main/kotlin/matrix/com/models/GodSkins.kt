package matrix.com.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GodSkin(
    @SerialName("godIcon_URL")
    val godIconURL: String,

    @SerialName("godSkin_URL")
    val godSkinURL: String,

    @SerialName("god_id")
    val godID: Int,

    @SerialName("god_name")
    val godName: String,

    val obtainability: String,

    @SerialName("price_favor")
    val priceFavor: Int,

    @SerialName("price_gems")
    val priceGems: Int,

    @SerialName("ret_msg")
    val retMsg: String?,

    @SerialName("skin_id1")
    val skinId1: Int,

    @SerialName("skin_id2")
    val skinId2: Int,

    @SerialName("skin_name")
    val skinName: String
)
