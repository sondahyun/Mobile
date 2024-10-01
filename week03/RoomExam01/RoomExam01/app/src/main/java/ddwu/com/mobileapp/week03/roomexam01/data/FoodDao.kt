package ddwu.com.mobile.roomexam01.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodDao {
    @Insert // 편의 메소드는 매개변수로 entity 사용
    fun insertFood(food: Food)
    @Update // Update와 Delete는 pk기준으로 작업
    fun updateFood(food: Food)
    @Delete
    fun deleteFood(food: Food)
    @Query("SELECT * FROM food_table")
    fun getAllFoods() : List<Food>
    @Query("SELECT * FROM food_table WHERE country = :country")
    fun getFoodsByCountry(country: String) : List<Food>

}