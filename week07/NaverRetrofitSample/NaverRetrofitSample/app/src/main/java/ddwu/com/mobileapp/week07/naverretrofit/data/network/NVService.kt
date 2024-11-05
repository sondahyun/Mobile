package ddwu.com.mobileapp.week07.naverretrofit.data.network

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.target.CustomTarget
import ddwu.com.mobileapp.week07.naverretrofit.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NVService(val context: Context) {
    private val TAG = "NVService"
    private val service : INaverBookSearch

    init {
        // retrofit 객체 생성
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // retrofit 객체로 service 객체 (interface 객체) 생성
        service = retrofit.create(INaverBookSearch::class.java)
    }


    suspend fun getBooks(query: String, clientID: String, clientSecret: String) : List<Book>? {
        val bookRoot: BookRoot = service.getBooks(clientID, clientSecret, query)
        return bookRoot.bookList // List<Book> 반환
    }


//    fun getImage(url: String?) : Bitmap? {
//
//
//        return null
//    }

}