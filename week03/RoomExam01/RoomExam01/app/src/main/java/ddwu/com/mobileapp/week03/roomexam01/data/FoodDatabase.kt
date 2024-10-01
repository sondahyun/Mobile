package ddwu.com.mobile.roomexam01.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Food::class], version = 1) // entity를 속성으로 지정
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao // 객체 가져올 수 있는 abstract 함수 => 자동 구현됨

    
    // singletonPattern 이용 : 객체 하나만 생성 (자원 절약)
    companion object { // 객체 만들지 않고 사용 가능한 static 변수와 함수
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder( // db 객체 생성 해줌
                    context.applicationContext, FoodDatabase::class.java, "food_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}