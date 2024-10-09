 package ddwu.com.mobileapp.week04.wordexam

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobileapp.week04.wordexam.databinding.ActivityMainBinding
import ddwu.com.mobileapp.week04.wordexam.data.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

 class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

//     val database by lazy {
//         WordDatabase.getDatabase(this)
//     }
//
//     val wordDao by lazy {
//         database.wordDao()
//     }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // 1. wordDao 객체 생성
        val wordDao = (application as WordApplication).wordDao


        val adapter = WordAdapter(ArrayList<Word>())
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        adapter.setOnWordClickListener(object: WordAdapter.OnWordClickListener {
            override fun onWordClick(view: View, pos: Int) {
                // 5. rvWords 에서 클릭한 단어로 wordDao를 사용하여  DB에서 의미 검색 후 의미 칸에 표시
                Toast.makeText(this@MainActivity, "$(adapter.words[pos])", Toast.LENGTH_SHORT).show()
                var meaning: String = wordDao.getWordMeaning("$(adapter.words[pos]")

            }
        })

        binding.rvWords.layoutManager = layoutManager
        binding.rvWords.adapter = adapter


        // 2. wordDao 객체에서 전체 word 를 가져와 rvWords(RecyclerView) 에 지정
        // Flow<List<Word>> 를 사용하여 갱신 정보를 자동 반영하도록 구성
        val rvWords : Flow<List<Word>> = wordDao.showAllWords()
        CoroutineScope(Dispatchers.Main).launch { // Coroutine을 실행할때는 scope지정해서 실행
            // List<Food>의 변경을 지속적으로 관찰하는 Flow 지정
            // collect()를 사용하여 Flow의 변경을 수집하여 확인 : List<Food>를 매개변수로 받음
            rvWords.collect { words ->
//                for (food in foods) {
//                    Log.d(TAG, food.toString())
//                }

                // recycle view에 나타냄
                adapter.words.clear()
                adapter.words.addAll(words)
                adapter.notifyDataSetChanged()
            }
        }



        // 3. 화면에 입력한 단어와 의미를 읽어와 Word 로 만든 후 wordDao 를 사용하여 DB 저장
        binding.btnSave.setOnClickListener {
            val wordName = binding.etWord.text.toString()
            val meaning = binding.etMeaning.text.toString()
            val Word = Word(wordName, meaning)
            CoroutineScope(Dispatchers.IO).launch {
                wordDao.insertWord(Word)
            }
        }

        // 4. 화면에 입력한 단어로 Word 로 만둔 후(의미는 빈문자열) wordDao 를 사용하여 DB 삭제
        binding.btnDelete.setOnClickListener {
            val Word = binding.etWord.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                wordDao.deleteWord(Word)
            }
        }


    }

}