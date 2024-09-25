package ddwu.com.mobileapp.week04.roomexam01

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobileapp.week04.roomexam01.data.Food
import ddwu.com.mobileapp.week04.roomexam01.databinding.ActivityMainBinding
import ddwu.com.mobileapp.week04.roomexam01.ui.FoodViewModel
import ddwu.com.mobileapp.week04.roomexam01.ui.FoodViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    // view binding object
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // 객체 생성: Activity의 변경 등에 상관없이 유지
    val foodViewModel: FoodViewModel by viewModels {
        FoodViewModelFactory( (application as FoodApplication).foodRepo )
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

        val adapter = FoodAdapter(ArrayList<Food>())

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.foodRecyclerView.layoutManager = layoutManager
        binding.foodRecyclerView.adapter = adapter

        // get all foods
        val foodRepo = (application as FoodApplication).foodRepo

//        val foodFlow = foodRepo.allFoods
//        CoroutineScope(Dispatchers.Main).launch {
//            foodFlow.collect { foods ->
//                adapter.foods.clear()
//                adapter.foods.addAll(foods)
//                adapter.notifyDataSetChanged()
//            }
//        }


        foodViewModel.allFoods.observe( this, {
            foods ->
            adapter.foods = foods
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Observing!!!")
        })

        // food by country
        binding.btnShow.setOnClickListener {
            val country = binding.etCountry.text.toString()

            CoroutineScope(Dispatchers.Main).launch {
                val deffFood = foodViewModel.findFoodByCountry(country)
                val foodName = deffFood.await()
                Log.d(TAG, foodName)
            }

//            CoroutineScope(Dispatchers.IO).launch {
//                val foods = foodRepo.getFoodByCountry(country)
//                for (food in foods) {
//                    Log.d(TAG, food.toString())
//                }
//            }

        }

        binding.btnInsert.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val countryName = binding.etCountry.text.toString()
            val food = Food(0, foodName, countryName)   // new food

//            CoroutineScope(Dispatchers.IO).launch {
//                foodRepo.addFood(food)
//            }
            foodViewModel.addFood( food )

        }

        // update food
        binding.btnUpdate.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val countryName = binding.etCountry.text.toString()
            val food = Food(0, foodName, countryName)

            CoroutineScope(Dispatchers.IO).launch {
//                foodRepo.modifyFood(food)
                foodRepo.modifyFoodCountryByFood(food)
            }

        }

        // delete food
        binding.btnDelete.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val food = Food(0, foodName, "")
            CoroutineScope(Dispatchers.IO).launch {
//                foodRepo.removeFood(food)
                foodRepo.removeFoodByName(food)       // 음식 이름으로 삭제
            }
        }

    }
}