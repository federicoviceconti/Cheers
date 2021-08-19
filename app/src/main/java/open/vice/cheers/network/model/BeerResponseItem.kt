package open.vice.cheers.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BeerResponseItem(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("tagline")
    @Expose
    val tagLine: String,

    @SerializedName("first_brewed")
    @Expose
    val firstBrewed: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("image_url")
    @Expose
    val url: String
)