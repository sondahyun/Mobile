package ddwu.com.mobileapp.week02.fooddbexam_room

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import ddwu.com.mobileapp.week02.fooddbexam_room.data.Food
import ddwu.com.mobileapp.week02.fooddbexam_room.data.FoodDao
import ddwu.com.mobileapp.week02.fooddbexam_room.data.FoodDatabase
import ddwu.com.mobileapp.week02.fooddbexam_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // 멤버변수로 생성하여 한번만 생성하면 됨
    val foodDatabase by lazy {
        FoodDatabase.getDatabase(this)
    }

    val foodDao by lazy {
        foodDatabase.foodDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        

//        // db 사용할때마다 객체 생성
//        val db : FoodDatabase = Room.databaseBuilder(
//            applicationContext, FoodDatabase::class.java, "food_db"
//        ).build()


        // 지역 변수 (매번 새로 만들어야 함) (by lazy 이용해서 멤버변수로 사용)
//        // FoodDatabase 객체 하나만 만들어서 사용 : singleton pattern
//        val db : FoodDatabase = FoodDatabase.getDatabase(this)
//
//        // Dao 사용
//        val foodDao : FoodDao = db.foodDao()


        // 별도의 스레드에서 작업, UI는 UI대로, DB는 DB대로 작업 가능
        Thread {
            var  foods: List<Food> = foodDao.getAllFoods()
            for(food in foods) {
                Log.d("MainActivity", "getAllFoods() 함수 이용하여 출력")
            }
        }.start()

        
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

//        show all foods

    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.btnShowFood -> {
//                show food by country
                Thread {
                    val foods: List<Food> = foodDao.showFoodByCountry("한국")
                    for(food in foods) {
                        Log.d("MainActivity","showFoodByCountry() 함수 이용하여 지정한 나라 음식 정보를 log에 출력")
                    }
                }.start()
            }
            R.id.btnAdd -> {
//                add food
                Thread {
                    foodDao.insertFood(Food(0, "떡볶이", "한국" ))
                    Log.d("MainActivity","떡볶이, 한국 insert")
                }.start()
            }
            R.id.btnModify -> {
//                modify food
                Thread {
                    var foodnum: Int = foodDao.updateFood(Food(1, "마라탕", "중국" ))
                    Log.d("MainActivity","수정한 갯수: ${foodnum}")
                }.start()
            }
            R.id.btnRemove -> {
//                remove food
                Thread {
                    var foodnum: Int = foodDao.deleteFood(Food(2, "떡볶이", "한국" ))
                    Log.d("MainActivity","삭제한 갯수: ${foodnum}")
                }.start()
            }
        }
    }




}