package ddwu.com.mobileapp.week04.roomexam01.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ddwu.com.mobileapp.week04.roomexam01.data.FoodRepository

// ViewModel이 사용하려는 Repository
class FoodViewModelFactory(private val foodRepository: FoodRepository) : ViewModelProvider.Factory   {
    // ViewModel 객체를 생성하는 함수를 재정의
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // ViewModel의 클래스 정보
        if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            // 만들어지는 ViewModel의 형식
            return FoodViewModel(foodRepository) as T
        }
        return IllegalArgumentException("Unknown ViewModel class") as T
    }
}
