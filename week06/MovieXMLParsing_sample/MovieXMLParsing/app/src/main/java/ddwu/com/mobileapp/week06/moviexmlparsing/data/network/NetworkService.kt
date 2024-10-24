package ddwu.com.mobileapp.week06.naverxmlparsing.data.network

import android.content.Context
import ddwu.com.mobileapp.week06.moviexmlparsing.R
import ddwu.com.mobileapp.week06.moviexmlparsing.data.network.util.MovieParser
import ddwu.com.mobileapp.week06.naverxmlparsing.data.Movie
import ddwu.com.mobileapp.week06.moviexmlparsing.data.network.util.NetworkUtil
import java.io.InputStream


class NetworkService(private val context: Context) {
    suspend fun getDailyBoxOffice(date: String) : List<Movie> {

        /*address 변수에 OpenAPI 서버 주소 저장 - strings.xml 활용*/
        val address : String = context.resources.getString(R.string.movie_url)

        /*HashMap 을 사용하여 key 와 targetDt 저장*/
        // key와 value값 구성
        val params = HashMap<String, String>()
        params["key"] = context.resources.getString(R.string.movie_key)
        params["targetDt"] = date // 전달 받은 data 값

        val resultStream: InputStream? = try {
            // getInputStream과 같은 함수
            NetworkUtil(context).sendRequest(NetworkUtil.GET, address, params)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return MovieParser().parse(resultStream)/*resultStream 을 MovieParser 의 parse() 함수에 전달*/
    }
}

