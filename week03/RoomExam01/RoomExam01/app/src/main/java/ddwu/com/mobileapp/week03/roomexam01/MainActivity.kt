package ddwu.com.mobileapp.week03.roomexam01

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.roomexam01.data.Food
import ddwu.com.mobile.roomexam01.data.FoodDatabase
import ddwu.com.mobileapp.week03.roomexam01.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class  MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    // view binding object
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    
    // 모든 activity에서 접근할 수 있도록 application 객체에 생성
//    val foodDabase by lazy {
//        FoodDatabase.getDatabase(this) // 만들어져 있으면 사용, 없으면 생성하는 static fun
//    }
//
//    val foodDao by lazy {
//        foodDabase.foodDao()
//    }


    // foodRepo 이용 (안에 FoodDatabase, FoodDao 정의 (FoodRepository) 돼있음)
    // foodDao 직접 접근 X, Repo통해서 접근
    val foodRepo by lazy {
        (application as FoodApplication).foodRepo
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // init RecyclerView
        val adapter = FoodAdapter(ArrayList<Food>())

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.foodRecyclerView.layoutManager = layoutManager
        binding.foodRecyclerView.adapter = adapter



//        // get all foods, Thread 사용
//        Thread {
//            val foods = foodDao.getAllFoods()
//            for (food in foods) {
//                Log.d(TAG, food.toString())
//            }
//        }.start()


        // coroutine 사용
        val foodFlow : Flow<List<Food>> = foodRepo.allFoods
        CoroutineScope(Dispatchers.Main).launch { // Coroutine을 실행할때는 scope지정해서 실행
            // List<Food>의 변경을 지속적으로 관찰하는 Flow 지정
            // collect()를 사용하여 Flow의 변경을 수집하여 확인 : List<Food>를 매개변수로 받음
            foodFlow.collect { foods -> // foods는 List<Food>를 의미
//                for (food in foods) {
//                    Log.d(TAG, food.toString())
//                }

                // recycle view에 나타냄
                adapter.foods.clear()
                adapter.foods.addAll(foods)
                adapter.notifyDataSetChanged()
            }
        }

        // food by country
        binding.btnShow.setOnClickListener {
            val countryName = binding.etCountry.text.toString()

//            // Thread 이용
//            Thread {
//                val foods = foodDao.getFoodsByCountry(countryName)
//                for (food in foods) {
//                    Log.d(TAG, food.toString())
//                }
//            }.start()

            // Coroutine 이용
            CoroutineScope(Dispatchers.IO).launch {
                val foods = foodRepo.showFoodByCountry(countryName)
                for (food in foods) {
                    Log.d(TAG, food.toString())
                }

            }

//            val foods =

//            adapter.foods.clear()
//            adapter.foods.addAll(foods)
//            adapter.notifyDataSetChanged()

        }


        // insert new food
        binding.btnInsert.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val countryName = binding.etCountry.text.toString()
            val food = Food(0, foodName, countryName)   // new food

//            // Thread 이용
//            Thread {
//                foodDao.insertFood(food)
//            }.start()

            //Coroutine 이용
            CoroutineScope(Dispatchers.IO).launch {
                foodRepo.addFood(food)
            }

        }

        // update food id 2
        binding.btnUpdate.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val countryName = binding.etCountry.text.toString()
            // val targetFood = Food(2, foodName, countryName)
            
//            // Thread 이용
//            Thread {
//                foodDao.updateFood(targetFood)
//            }.start()

            // Coroutine 이용
            CoroutineScope(Dispatchers.IO).launch {
                foodRepo.modifyFood(foodName, countryName)
            }
        }

        // update food id 3
        binding.btnDelete.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            // val targetFood = Food(3, foodName, "")    // delete food _id 3

//            // Thread 이용
//            Thread {
//                foodDao.deleteFood(targetFood)
//            }.start()

            // Coroutine 이용
            CoroutineScope(Dispatchers.IO).launch {
                foodRepo.removeFood(foodName)
            }

        }


    }
}