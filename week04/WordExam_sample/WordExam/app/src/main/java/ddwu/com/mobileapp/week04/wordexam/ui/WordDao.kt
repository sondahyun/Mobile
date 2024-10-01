package ddwu.com.mobileapp.week04.wordexam.ui

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


interface WordDao {

    fun insertWord(word: Word)

    fun deleteWord(word: Word)

    fun updateWord(word: Word)

    // 조건 없이 전체 단어를 검색하여 Word 엔티티 반환
    fun showAllWords() : Flow<List<Word>>


    // 단어(word)를 입력하여 의미(meaning) 반환
    fun getWordMeaning(word: String) : String
}