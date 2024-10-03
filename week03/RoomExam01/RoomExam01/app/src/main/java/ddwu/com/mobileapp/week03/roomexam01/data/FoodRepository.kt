package ddwu.com.mobileapp.week03.roomexam01.data

import ddwu.com.mobile.roomexam01.data.Food
import ddwu.com.mobile.roomexam01.data.FoodDao
import kotlinx.coroutines.flow.Flow

// FoodDao는 Repo만 접근 가능하도록: private
class FoodRepository (private val foodDao: FoodDao) {
    val allFoods: Flow<List<Food>> = foodDao.getAllFoods()

    // 외부에서 간접적으로 사용할 수 있도록
    suspend fun showFoodByCountry(country: String) : List<Food> {
        return foodDao.getFoodsByCountry(country)
    }

    // 외부에서 간접적으로 사용할 수 있도록
    suspend fun addFood(food: Food) {
        foodDao.insertFood(food)
    }

    // 외부에서 간접적으로 사용할 수 있도록
    suspend fun modifyFood(food: String, country: String) {
        return foodDao.updateFood(food, country)
    }

    // 외부에서 간접적으로 사용할 수 있도록
    suspend fun removeFood(food: String) {
        return foodDao.deleteFood(food)
    }


}