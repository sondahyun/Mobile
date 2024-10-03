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


class FoodViewModel (val foodRepo: FoodRepository) : ViewModel() { // viewModel 클래스 상속
    // Flow Data를 Live Data로 변경 (생명주기를 인식해서 화면이 보여질때만 반영되도록)
    var allFoods  : LiveData<List<Food>> = foodRepo.allFoods.asLiveData()

    // CoroutineScope(DISPATCHER.ID).launch대신에 ViewModel은 viewModelScope사용
    fun addFood(food: Food) = viewModelScope.launch { 
        foodRepo.addFood(food)
    }

    fun findFoodByCountry(country: String) : Deferred<String> {
        // CoroutineScope를 viewModelScope로 지정
        val deferredFood = viewModelScope.async {
            foodRepo.getFoodByCountry(country)
        }
        return deferredFood
    }
}