package ddwu.com.mobileapp.week04.wordexam

import android.app.Application
import ddwu.com.mobileapp.week04.wordexam.ui.WordDatabase

class WordApplication: Application() {
    val wordDatabase by lazy {
        WordDatabase.getDatabase(this)
    }

    val wordDao by lazy {
        wordDatabase.wordDao()
    }
}