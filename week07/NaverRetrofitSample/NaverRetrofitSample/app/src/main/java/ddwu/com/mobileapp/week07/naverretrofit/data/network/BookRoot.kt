package ddwu.com.mobileapp.week07.naverretrofit.data.network

import com.google.gson.annotations.SerializedName


// BookRoot
data class BookRoot(
    @SerializedName("items")
    val bookList: List<Book>,
)

// Book dto (item 저장)
data class Book(
    val title: String,
    val image: String,
    val author: String,
    val publisher: String,
)




// Book의 toString() 참고
/*
    override fun toString(): String {
        return "$title - $author"
    }
*/

