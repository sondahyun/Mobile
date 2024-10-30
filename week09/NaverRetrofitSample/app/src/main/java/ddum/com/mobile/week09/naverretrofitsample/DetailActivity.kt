package ddum.com.mobile.week09.naverretrofitsample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ddum.com.mobile.week09.naverretrofitsample.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    val detailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailBinding.root)

        // MainActivity 로부터 전달받은 이미지의 URL
        imageUrl = intent.getStringExtra("url")



        detailBinding.btnSave.setOnClickListener {

        }

        detailBinding.btnRead.setOnClickListener {

        }

        detailBinding.btnInit.setOnClickListener {

        }

        detailBinding.btnRemove.setOnClickListener {

        }
    }
}