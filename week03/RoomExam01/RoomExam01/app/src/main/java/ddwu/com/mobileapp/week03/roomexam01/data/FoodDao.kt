package ddwu.com.mobile.roomexam01.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert // 편의 메소드는 매개변수로 entity 사용
    suspend fun insertFood(food: Food) 
    // suspend: coroutine에서 사용 가능한 함수
    // suspend: 한번만 실행 (함수를 잠깐 멈춰도 됨)(실행 도중 중단 가능)

    @Update // Update와 Delete는 pk기준으로 작업
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("SELECT * FROM food_table")
    fun getAllFoods() : Flow<List<Food>>
    // observer: 계속 관찰해야함 (Flow로 감싸야함)

    @Query("SELECT * FROM food_table WHERE country = :country")
    suspend fun getFoodsByCountry(country: String) : List<Food>

}