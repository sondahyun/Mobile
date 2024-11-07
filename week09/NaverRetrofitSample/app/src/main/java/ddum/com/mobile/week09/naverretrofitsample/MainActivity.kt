package ddum.com.mobile.week09.naverretrofitsample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
        Log.d(TAG, "Internal filesDir: ${filesDir}") // 파일에 바로 접근 가능: filesDir
        Log.d(TAG, "Internal cacheDir: ${cacheDir}") // 캐시파일에 바로 접근 가능: cacheDir

        // 외부저장소
        Log.d(TAG, "External filesDir: ${getExternalFilesDir(null).toString()}")
        Log.d(TAG, "External cacheDir: ${externalCacheDir}")


        // 파일 쓰기
        val writeData = "Mobile Application!"

        // 기본 방법 - 전용 위치가 아닌 지정 위치 사용
        val writeFile = File(filesDir, "test.txt") // file 객체 생성
        val outputStream = FileOutputStream(writeFile)
        outputStream.write(writeData.toByteArray())
        outputStream.close()

//        // 추가방법 - 기본위치에 저장 (filesDir) // 무조건 files 아래에 저장
//        context.openFileOutput("text.txt", Context.MODE_PRIVATE).use { // use는 close 자동
//            it.write(writeData.toByteArray())
//        }



          // 파일 읽기
//        val newFile = File(filesDir, "test.txt")
//
//        // 기본 방법 - 전용 위치가 아닌 지정 위치 사용
//        val result = StringBuffer()
//
//        val fileReader = FileReader(newFile)
//        BufferedReader(fileReader).useLines { lines -> // (stream -> string)
//            for (line in lines) {
//                result.append(line+"\n")
//            }
//        }

        // 추가 방법 - 앱에 지정되어 있는 전용 위치를 사용
        openFileInput("test.txt").bufferedReader().useLines { lines ->
            for (line in lines) { // 한줄씩 읽어와서 출력
                Log.d(TAG, line + "\n")
            }
        }


//        // 이미지 파일 Bitmap으로 읽어오기
//        val imageFile = File( filesDir , "image.jpg")
//        val bitmap = BitmapFactory.decodeFile(imageFile.path)
//        binding.imageView.setImageBitmap(bitmap)
//
//        // 이미지 파일 Bitmap으로 저장하기
//        val imageFile1 = File( filesDir, "image.jpg")
//        val fos = FileOutputStream(imageFile1) // 이미지 쓰기 (파일 출력)
//
////        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
////        fos.close()


//        Glide.with(this)
//            .load("${filesDir}/images/image.jpg")
//            .into(binding.imageView)
//
//

        // 하위 폴더 만들기 (T/F 반환)
        FileManager.createSubDirectory( filesDir, "images" )



        adapter.setOnItemClickListener(object: BookAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val url = adapter.books?.get(position)?.image
                Log.d(TAG, url.toString())
                // 실습1. url 에 해당하는 이미지 바로 표시
                // network 작업도 하고 bitmap으로 변환하여 넣는 작업도 함
                // Glide 내부에서 자동으로 별도의 스레드에서 처리함 (스레드 자동)
//                Glide.with(this@MainActivity) // 문맥 정보
//                    .load(url) // 웹 주소 (파일 주소 - 이미지 정보) // 네트워크 작업
//                    .into(binding.imageView) // 어느 뷰에 넣어줄지 // bitmap 변환 작업
//              // placeholder, error, fallback등 로딩 에러 시 보여줄 이미지 지정 가능


                // 실습2. ViewModel을 통해 Bitmap 을 가져와 표시
                nvViewModel.setImage(url) // viewModel 사용

                // image를 url에서 가져와서 저장
                Glide.with(this@MainActivity)
                    .asBitmap()
                    .load(url)
                    .into ( object : CustomTarget<Bitmap> (3350, 350) { //pixel (or Target,SIZE_ORIGINAL)
                        // 잘 읽어 왔을 때 (저장)
                        override fun onResourceReady(
                            resource: Bitmap, // url이 Bitmap객체로 만들어짐
                            transition: Transition<in Bitmap>?
                        ) {
                            // resourse를 파일로 저장
                            val imageFile = File("${filesDir}/images", "image.jpg")
                            val fos = FileOutputStream(imageFile)
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                            fos.close()
                        }
                        // 못 읽어 왔을 때 (끝냄)
                        override fun onLoadCleared(placeholder: Drawable?) {
                            TODO("Not yet implemented")
                        }
                    }
                    )

                // 실습3. 클릭할 경우 Image 의 url 을 Intent 에 저장(key: url) 후 DetailActivity 호출

            }
        })



        binding.btnSearch.setOnClickListener{
            val query = binding.etKeyword.text.toString()
            nvViewModel.getBooks(query)
        }


    }
}