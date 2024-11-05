package ddum.com.mobile.week09.naverretrofitsample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import ddum.com.mobile.week09.naverretrofitsample.data.util.FileManager
import ddum.com.mobile.week09.naverretrofitsample.databinding.ActivityMainBinding
import ddum.com.mobile.week09.naverretrofitsample.ui.BookAdapter
import ddum.com.mobile.week09.naverretrofitsample.ui.NVViewModel
import ddum.com.mobile.week09.naverretrofitsample.ui.NVViewModelFactory
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class MainActivity : AppCompatActivity() {

    val TAG = "MAIN_ACTIVITY_TAG"

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = BookAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvBooks.layoutManager = layoutManager
        binding.rvBooks.adapter = adapter

        val nvViewModel : NVViewModel by viewModels {
            NVViewModelFactory( (application as NVApplication).nvRepository )
        }

        nvViewModel.books.observe(this) { books ->
            adapter.books = books
            adapter.notifyDataSetChanged()
        }

        // drawable: bitmap 객체
        // Glide를 이용해서 bitmap 객체를 가져와서
        // bitmap 객체를 viewModel에 보관
        nvViewModel.drawable.observe(this) { drawable ->
            binding.imageView.setImageBitmap(drawable)
        }


        // 필요할 경우 파일 디렉토리 생성
        // 내부저장소 전용위치에 images 하위 디렉토리 생성
        Log.d(TAG, "Internal filesDir: ${filesDir}")
        Log.d(TAG, "Internal cacheDir: ${cacheDir}")

        val writeData = "Mobile Application!"

        //  기본 방법 - 전용 위치가 아닌 지정 위치 사용
        val writeFile = File(filesDir, "test.txt")
        val outputStream = FileOutputStream(writeFile)
        outputStream.write(writeData.toByteArray())
        outputStream.close()

        val newFile = File(filesDir, "test.txt")

        // 기본 방법
        val result = StringBuffer()

        val fileReader = FileReader(newFile)
        BufferedReader(fileReader).useLines { lines ->
            for (line in lines) {
                result.append(line+"\n")
            }
        }

        // 추가 방법
//        context.openFileInput("test.txt").bufferedReader().useLines{lines ->
//            for (line in lines) {
//                result.append(line+"\n")
//            }
//        }
        adapter.setOnItemClickListener(object: BookAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val url = adapter.books?.get(position)?.image
                Log.d(TAG, url.toString())
                // 실습1. url 에 해당하는 이미지 바로 표시
                // network 작업도 하고 bitmap으로 변환하여 넣는 작업도 함
                // Glide 내부에서 자동으로 별도의 스레드에서 처리함
//                Glide.with(this@MainActivity) // Glide는 스레드 자동으로 사용
//                    .load(url) // 웹 주소
//                    .into(binding.imageView) // 어느 뷰에 넣어줄지

                // 실습2. ViewModel을 통해 Bitmap 을 가져와 표시
//                nvViewModel.setImage(url) // viewModel 사용


                // 실습3. 클릭할 경우 Image 의 url 을 Intent 에 저장(key: url) 후 DetailActivity 호출

            }
        })



        binding.btnSearch.setOnClickListener{
            val query = binding.etKeyword.text.toString()
            nvViewModel.getBooks(query)
        }


    }
}