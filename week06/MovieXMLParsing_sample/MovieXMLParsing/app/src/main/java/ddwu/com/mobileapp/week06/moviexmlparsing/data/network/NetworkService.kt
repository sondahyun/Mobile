package ddwu.com.mobileapp.week06.naverxmlparsing.data.network

import android.content.Context
import ddwu.com.mobileapp.week06.naverxmlparsing.data.Movie
import ddwu.com.mobileapp.week06.moviexmlparsing.data.network.util.NetworkUtil
import java.io.InputStream


class NetworkService(private val context: Context) {
    suspend fun getDailyBoxOffice(date: String) : List<Movie> {

        /*address 변수에 OpenAPI 서버 주소 저장 - strings.xml 활용*/
        val address : String

        /*HashMap 을 사용하여 key 와 targetDt 저장*/
        val params : HashMap<String, String>?

        val resultStream: InputStream? = try {
            NetworkUtil(context).sendRequest(NetworkUtil.GET, address, params)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return /*resultStream 을 MovieParser 의 parse() 함수에 전달*/
    }
}

