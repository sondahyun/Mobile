package ddwu.com.mobileapp.week04.wordexam.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ddwu.com.mobileapp.week04.wordexam.data.WordRepository

class WordViewModelFactory(private val wordRepository: WordRepository) : ViewModelProvider.Factory {
    // ViewModel 객체를 생성하는 함수를 재정의
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        // ViewModel의 클래스 정보
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            // 만들어지는 ViewModel의 형식
            return WordViewModel(wordRepository) as T
        }
        return IllegalStateException("Unknown ViewModel class") as T
    }
}