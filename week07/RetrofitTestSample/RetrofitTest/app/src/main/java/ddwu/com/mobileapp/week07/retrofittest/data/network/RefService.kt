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
    val movieService: IBoxOfficeService // IBoxOfficeService의 객체

    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.kobis_url)) // url 입력
            .addConverterFactory(GsonConverterFactory.create()) // 가져온 json을 DTO로 parsing -> converter 이용해서 변환 -> gson 이용
            .build()

        // retrofit에 interface 구현 시킴 : IBoxOfficeService 객체 생성
        // IBoxOfficeService 내의 함수 사용 가능
        // Call<Root> 반환
        movieService = retrofit.create(IBoxOfficeService::class.java)
    }

    // suspend는 suspend안에서만 실행 가능함
    suspend fun getMovies(key: String, date: String)  : List<Movie>   {
//        // 응답이 날아왔을 때 호출 (결과 받음), Call 타입으로 반환 날아오면 CallBack 호출
//        val movieCallback = object : Callback<Root> { // 객체
//            // 서버로부터 응답 제대로 날아옴 (성공)
//            override fun onResponse(call: Call<Root>, response: Response<Root>) { // Call을 요청할때 사용, 결과: Response<Root>
//                if (response.isSuccessful) {
//                    val boxOfficeRoot = response.body() // body(): Root 값
//                    val movies = boxOfficeRoot?.movieResult?.movieList // List<Movie> 튀어나옴
//                    movies?.forEach { movie ->
//                        Log.d(TAG, movie.toString())
//                    }
//                }
//            }
//            // 서버로부터 응답 제대로 날아오지 않음
//            override fun onFailure(call: Call<Root>, t: Throwable) {
//                Log.d(TAG, t.stackTraceToString())
//            }
//        }
//        // 호출할 수 있는 service 만듦
//        val movieCall : Call<Root> = movieService.getDailyBoxOffice("json", key, date) /* IBoxOfficeService 의 함수 호출 */
//
//        // 비동기: enqueue (언젠가 Call<Root> 날아오면 movieCallback 실행)
//        movieCall.enqueue(movieCallback)
          //val response = movieCall.execute() // 동기


        // coroutine (Retrofit에서 Coroutine 자체 지원)
        // suspend 함수 호출
        val root : Root = movieService.getDailyBoxOffice("json", key, date)
        return root.movieResult.movieList

        // return null // response.body()?.boxOfficeResult?.boxOfficeList
    }

}