package ddwu.com.mobileapp.week07.retrofittest.data.network

import android.content.Context
import android.util.Log
import ddwu.com.mobileapp.week07.retrofittest.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RefService(val context: Context) {
    val TAG = "RefService"
    val movieService: IBoxOfficeService

    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.kobis_url)) // url 입력
            .addConverterFactory(GsonConverterFactory.create()) // 가져온 json을 DTO로 parsing -> converter 이용해서 변환 -> gson 이용
            .build()

        // interface 구현 : IBoxOfficeService 객체 생성
        // Call<Root> 반환
        movieService = retrofit.create(IBoxOfficeService::class.java)
    }

    suspend fun getMovies(key: String, date: String)  : List<Movie>?   {
        // 응답이 날아왔을 때 호출 (결과 받음)
        val movieCallback = object : Callback<Root> { // 객체
            // 서버로부터 응답 제대로 날아옴 (성공)
            override fun onResponse(call: Call<Root>, response: Response<Root>) { // 결과: Response<Root>
                if (response.isSuccessful) {
                    val boxOfficeRoot = response.body() // body(): Root 값
                    val movies = boxOfficeRoot?.movieResult?.movieList
                    movies?.forEach { movie ->
                        Log.d(TAG, movie.toString())
                    }
                }
            }
            // 서버로부터 응답 제대로 날아오지 않음
            override fun onFailure(call: Call<Root>, t: Throwable) {
                Log.d(TAG, t.stackTraceToString())
            }
        }

        val movieCall : Call<Root> = movieService.getDailyBoxOffice("json", key, date)/* IBoxOfficeService 의 함수 호출 */

        // 비동기: enqueue
        movieCall.enqueue(movieCallback)    // val response = movieCall.execute()

        return null // response.body()?.boxOfficeResult?.boxOfficeList
    }

}