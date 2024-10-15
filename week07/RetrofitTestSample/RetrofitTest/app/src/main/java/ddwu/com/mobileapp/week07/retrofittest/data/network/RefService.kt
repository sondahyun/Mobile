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
        val retrofit : Retrofit =


        movieService =
    }

    suspend fun getMovies(key: String, date: String)  : List<Movie>?   {
//        val movieCallback = object : Callback<  > {
//            override fun onResponse(call: Call<   >, response: Response<   >) {
//                if (response.isSuccessful) {
//                    val boxOfficeRoot = response.body()
//                    val movies = boxOfficeRoot?.movieResult?.movies
//                    movies?.forEach { movie ->
//                        Log.d(TAG, movie.toString())
//                    }
//                }
//            }
//            override fun onFailure(call: Call<   >, t: Throwable) {
//                Log.d(TAG, t.stackTraceToString())
//            }
//        }

//        val movieCall : Call <   > = /* IBoxOfficeService 의 함수 호출 */
//        movieCall.enqueue(movieCallback)    // val response = movieCall.execute()

        return null // response.body()?.boxOfficeResult?.boxOfficeList
    }

}