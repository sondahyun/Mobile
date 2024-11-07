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
    val image: String, // 이미지의 인터넷 주소를 가져옴 (네트워크에 변환 요청, 이미지 가져와야 함) (inputstream -> bitmap)
    val author: String,
    val publisher: String,
)




// Book의 toString() 참고
/*
    override fun toString(): String {
        return "$title - $author"
    }
*/

