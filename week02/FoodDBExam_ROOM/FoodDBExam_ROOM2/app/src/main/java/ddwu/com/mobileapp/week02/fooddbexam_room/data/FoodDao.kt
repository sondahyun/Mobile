package ddwu.com.mobileapp.week02.fooddbexam_room.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodDao {
    @Insert
    fun insertFood(vararg food: Food)

    @Update
    fun updateFood(food: Food) : Int

    @Delete
    fun deleteFood(food: Food) : Int

    @Query("SELECT * FROM food_table")
    fun getAllFoods(): List<Food>

    }