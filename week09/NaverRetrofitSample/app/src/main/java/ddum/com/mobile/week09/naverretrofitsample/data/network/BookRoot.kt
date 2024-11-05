package ddum.com.mobile.week09.naverretrofitsample.data.network


data class BookRoot (
    val items : List<Book>
)

data class Book(
    val title: String,
    val image: String, // 책 표지에 대한 주소 (다시 네트워크로 책 표지 이미지 가져와야함)
    val author: String,
    val publisher: String
) {
    override fun toString(): String {
        return "$title - $author"
    }
}


