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

//    val foodDatabase by lazy {
//        FoodDatabase.getDatabase(this)
//    }

//    val foodDao by lazy {
//        foodDatabase.foodDao()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        val db : FoodDatabase = Room.databaseBuilder(
            applicationContext, FoodDatabase::class.java, "food_db"
        ).build()

        val foodDao : FoodDao = db.foodDao()
        
        Thread { //별도의 스레드에서 작업, UI는 UI대로, DB는 DB대로 작업 가능
            foodDao.insertFood(Food(0, "떡볶이", "한국" ))
            Log.d("MainActivity","떡볶이, 한국 insert")
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
            }
            R.id.btnAdd -> {
//                add food
            }
            R.id.btnModify -> {
//                modify food
            }
            R.id.btnRemove -> {
//                remove food
            }
        }
    }




}