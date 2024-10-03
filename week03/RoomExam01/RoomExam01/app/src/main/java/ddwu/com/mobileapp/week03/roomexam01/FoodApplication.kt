package ddwu.com.mobileapp.week03.roomexam01

import android.app.Application
import ddwu.com.mobile.roomexam01.data.FoodDatabase
import ddwu.com.mobileapp.week03.roomexam01.data.FoodRepository

// 기존 application 상속 받아서 사용
// repository를 모든 activity에서 사용할 수 있도록 repo객체를 전체에서 사용할 수 있도록 바꿈
class FoodApplication: Application() {

    // 모든 activity에서 foodRepo라는 객체 사용 가능
    // FoodRepository 접근 가능 -> Dao 접근 가능
    val foodRepo by lazy {
        val database = FoodDatabase.getDatabase(this) // 만들어져 있으면 사용, 없으면 생성하는 static fun
        FoodRepository(database.foodDao())
    }

}