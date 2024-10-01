package ddwu.com.mobileapp.week02.roomexam01.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName= "food_table")
data class Food( // data class
    @PrimaryKey (autoGenerate = true) // 기본키
    val _id: Int, // value (상수)
    var food: String?, // variable
    var country: String?, // variable

    // override toString()
    override fun toString() : String {
        return "$_id - $food ($country)"
    }
)