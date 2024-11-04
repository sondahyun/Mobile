package ddwu.com.mobileapp.week07.retrofittest.data.network

import com.google.gson.annotations.SerializedName

/*data class 를 사용하여 DTO 작성*/

data class Root(
    @SerializedName("boxOfficeResult")
    val movieResult: BoxOfficeResult,
)

data class BoxOfficeResult(
    val boxOfficeType: String,
    val showRange: String,
    @SerializedName("dailyBoxOfficeList")
    val movieList: List<Movie>,
)

// 필요 없는 항목은 없애도 됨
// 클래스 이름 마음대로 바꿔도 됨
// 변수 이름은 json의 value와 같아야 함. 바꾸려면 @사용
// @: 컴파일러를 위한 주석
data class Movie(
    val rank: String,
    @SerializedName("movieNm")
    val title: String,
    @SerializedName("openDt")
    val openDate: String,
)

