package ddwu.com.mobileapp.week04.wordexam.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Word(
    val word: String,
    var meaning: String
) {
    override fun toString(): String {
        return word
    }
}
