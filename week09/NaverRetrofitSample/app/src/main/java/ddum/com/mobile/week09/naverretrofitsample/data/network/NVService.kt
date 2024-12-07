package ddum.com.mobile.week09.naverretrofitsample.data.network

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import ddum.com.mobile.week09.naverretrofitsample.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NVService(val context: Context) {
    private val TAG = "NVService"
    private val service : INaverBookSearch

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.url))
            .addConverterFactory( GsonConverterFactory.create() )
            .build()

        service = retrofit.create(INaverBookSearch::class.java)
    }


    // Naver OpenAPI 를 이용하여 책 검색결과 반환
    suspend fun getBooks(query: String) : List<Book>? {
        val clientID = context.resources.getString(R.string.client_id)
        val clientSecret = context.resources.getString(R.string.client_secret)

        val bookRoot = service.getBooksByKeyword(query, clientID, clientSecret)
        return bookRoot.items
    }

    // Glide를 사용하여 책 이미지를 가져와 Bitmap 으로 반환 (바로 view에 넣어주지 않음)
    // Glide는 bitmap 객체만 가져옴 -> 최종적으로 viewModel에 저장 (activity 상관없이 유지)
    suspend fun getImage(url: String?) : Bitmap {
        // Bitmap 객체를 담음 (futureTarget: Glide에서 제공하는 class)
        val futureTarget : FutureTarget<Bitmap> = // bitmap 객체로 만듦
            Glide.with(context) // 문맥 정보
                .asBitmap() // bitmap으로 가져옴
                .load(url) // 읽어올 주소
                .submit() // bitmap 객체로 최종 완성

        // 이미지를 bitmap으로 가져와서 보관
        val bitmap = futureTarget.get()
        // 메모리 관리 (Glide 내부에서도 자동으로 해줌)
        Glide.with(context).clear(futureTarget)

        // bitmap 객체 반환
        return bitmap

    }

}