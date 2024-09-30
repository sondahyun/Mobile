package ddwu.com.mobileapp.week02.fooddbexam_room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database (entities = [Food::class], version=1)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao


    companion object { // static 변수, 메소드
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, FoodDatabase::class.java, "food_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}