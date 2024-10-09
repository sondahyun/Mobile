package ddwu.com.mobileapp.week04.wordexam.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobileapp.week04.wordexam.data.Word
import ddwu.com.mobileapp.week04.wordexam.data.WordRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WordViewModel (val wordRepo: WordRepository) : ViewModel() { // viewModel 클래스 상속
    // Flow Data를 Live Data로 변경 (생명주기를 인식해서 화면이 보여질때만 반영되도록)
    var viewModel_allWords : LiveData<List<Word>> = wordRepo.allWords.asLiveData()

    fun viewModel_addWord(word: Word) = viewModelScope.launch {
        wordRepo.addWord(word)
    }
    fun viewModel_removeWord(word: String) = viewModelScope.launch {
        wordRepo.removeWord(word)
    }
    fun viewModel_modify(word: String, meaning: String) = viewModelScope.launch {
        wordRepo.modify(word, meaning)
    }
    fun viewModel_getWordMean(word: String) : Deferred<String> {
        // CoroutineScope를 viewModelScope로 지정
        // 반환값있을때 -> async사용 ==> deffered타입 이용
        val deferredWord = viewModelScope.async {
            wordRepo.getWordMean(word)
        }
        return deferredWord // 반환 -> mainactivity의 CoroutineScope안에 있는 await이 받음
    }

}