
import com.google.gson.annotations.SerializedName

data class dd(
    @SerializedName("Data")
    var `data`: Data?,
    @SerializedName("Response")
    var response: String?,
    @SerializedName("Status")
    var status: String?
)