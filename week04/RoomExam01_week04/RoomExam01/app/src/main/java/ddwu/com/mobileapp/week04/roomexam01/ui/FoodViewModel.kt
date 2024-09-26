package ddwu.com.mobileapp.week04.roomexam01.ui


import android.provider.LiveFolders
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ddwu.com.mobileapp.week04.roomexam01.data.Food

import ddwu.com.mobileapp.week04.roomexam01.data.FoodRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FoodViewModel (val foodRepo: FoodRepository) : ViewModel() {
    var allFoods  : LiveData<List<Food>> = foodRepo.allFoods.asLiveData()

    fun addFood(food: Food) = viewModelScope.launch {
        foodRepo.addFood(food)
    }

    fun findFoodByCountry(country: String) : Deferred<String> {
        val deferredFood = viewModelScope.async {
            foodRepo.getFoodByCountry(country)
        }
        return deferredFood
    }
}