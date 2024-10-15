package ddwu.com.mobileapp.week06.naverparsing.data.network

import android.content.Context
import ddwu.com.mobileapp.week06.naverparsing.R
import ddwu.com.mobileapp.week06.naverparsing.data.Book
import ddwu.com.mobileapp.week06.naverparsing.data.network.util.NaverBookParser
import ddwu.com.mobileapp.week06.naverparsing.data.network.util.NetworkUtil

class NetworkService(private val context: Context) {

    fun getBooksByKeyword(keyword: String) : List<Book> {

        val result = try {


        } catch(e: Exception) {
            e.printStackTrace()
            null
        }

        return
    }

}