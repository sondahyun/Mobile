package ddwu.com.mobileapp.week07.retrofittest.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


// @Get:  kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json
// @Query:  key
// @Query:  targetDt

interface IBoxOfficeService {
    // path를 알려줌
    @GET("kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.{type}")
    fun getDailyBoxOffice(
        // path상의 값을 바꾸고 싶을 때
        @Path("type") type: String,
        @Query("key") key: String,
        @Query("targetDt") targetDate: String
        // 네이버: @Header("ID")
    ) : Call<Root>
// DTO의 root가 튀어나옴
// 비동기 방식으로 요청을 만듦

}