package ddwu.com.mobileapp.week04.wordexam.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    suspend fun addWord(word: Word) {
        wordDao.insertWord(word)
    }
    suspend fun removeWord(word: String) {
        wordDao.deleteWord(word)
    }
    suspend fun modify(word: String, meaning: String) {
        wordDao.updateWord(word, meaning)
    }
    // 조건 없이 전체 단어를 검색하여 Word 엔티티 반환
    val allWords : Flow<List<Word>> = wordDao.showAllWords()

    // 단어(word)를 입력하여 의미(meaning) 반환
    suspend fun getWordMean(word: String): String {
        return wordDao.getWordMeaning(word)
    }
}