package ddum.com.mobile.week09.naverretrofitsample

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ddum.com.mobile.week09.naverretrofitsample.data.util.FileManager
import ddum.com.mobile.week09.naverretrofitsample.databinding.ActivityDetailBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class DetailActivity : AppCompatActivity() {

    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    var imageUrl: String? = null
    var imageTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        // MainActivity 로부터 전달받은 이미지의 URL
        imageUrl = intent.getStringExtra("url")

        Glide.with(this) // 문맥 정보
            .load(imageUrl) // 웹 주소 (파일 주소 - 이미지 정보) // 네트워크 작업
            .into(detailBinding.ivBookCover) // 어느 뷰에 넣어줄지 // bitmap 변환 작업


        detailBinding.btnSave.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into( object : CustomTarget<Bitmap> (350, 350) {
                    // 잘 읽어왔을때 (저장)
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imageTime = FileManager.getCurrentTime()
                        val imageFile = File("${filesDir}/images","${imageTime}.jpg")
                        val fos = FileOutputStream(imageFile)
                        resource.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                        fos.close()
                    }
                    // 못 읽어왔을때 (끝냄)
                    override fun onLoadCleared(placeholder: Drawable?) {
                        Log.d(TAG, "Image Load Cleared!")
                    }
                })
        }

        detailBinding.btnRead.setOnClickListener {
            Glide.with(this)
                .load("${filesDir}/images/${imageTime}.jpg")
                .error(R.drawable.ic_launcher_background)
                .into(detailBinding.ivBookCover)
        }

        detailBinding.btnInit.setOnClickListener {
            Glide.with(this) // 문맥 정보
                .load(R.drawable.ic_launcher_background) // 웹 주소 (파일 주소 - 이미지 정보) // 네트워크 작업
                .into(detailBinding.ivBookCover) // 어느 뷰에 넣어줄지 // bitmap 변환 작업
        }

        detailBinding.btnRemove.setOnClickListener {
            val dir = File("${filesDir}/images")
            val files = dir.listFiles()
            files?.forEach { file ->
                if (file.isFile) {
                    file.delete() // 각 파일 삭제
                }
            }
        }
    }
}