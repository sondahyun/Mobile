package ddwu.com.mobileapp.week04.roomexam01

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

    
    // ViewModel 객체 생성: Activity의 변경 등에 상관없이 유지
    // ViewModel 객체 생성 위해서는 viewModels delegate(대리) 해야함
    // ViewModel 전부 재정의 하지 않아도 됨 (자동 구현)
    val foodViewModel: FoodViewModel by viewModels {
        // ViewModel이 사용하는 Repo를 매개변수로 전달해야함
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



//        // get all foods
//        // repo 직접 접근
//        val foodRepo = (application as FoodApplication).foodRepo



        
        // repo에 직접 접근
        // Flow Data는 화면이 사라져도 데이터 관찰
//        val foodFlow = foodRepo.allFoods
//        CoroutineScope(Dispatchers.Main).launch {
//            foodFlow.collect { foods -> // 데이터 바뀔때마다 수집
//                adapter.foods.clear()
//                adapter.foods.addAll(foods)
//                adapter.notifyDataSetChanged()
//            }
//        }

        // viewModel에서 allFoods호출 -> LiveData type
        // Flow->collect, LiveData->Observer(관찰)
        // allFoods가 화면이 보일때만 관찰함 (Flow는 계속 데이터 변경 관찰)
        foodViewModel.allFoods.observe( this, Observer { foods -> //Livedata type
            adapter.foods = foods // adapter에 연결
            adapter.notifyDataSetChanged() // 화면 데이터 값 변경됐다고 알림
            Log.d(TAG, "Observing!!!")
        })




        // food by country
        binding.btnShow.setOnClickListener {
            val country = binding.etCountry.text.toString()

            CoroutineScope(Dispatchers.Main).launch {
                // Dispatchers.Main: coroutine내에서 화면 요소에 접근하려면 메인 쓰레드안에서 coroutine이 동작해야함
                val deffFood = foodViewModel.findFoodByCountry(country)
                val foodName = deffFood.await() // await 사용하려면 coroutine내에서 사용해야함
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

            // Repo 직접 사용
//            CoroutineScope(Dispatchers.IO).launch {
//                foodRepo.addFood(food)
//            }


            // CoroutineScope은 ViewModelScope로 정의 돼있음
            foodViewModel.addFood( food )

        }

        // update food
        binding.btnUpdate.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val countryName = binding.etCountry.text.toString()
            val food = Food(0, foodName, countryName)

//            // Repo 직접 사용
//            CoroutineScope(Dispatchers.IO).launch {
//                foodRepo.modifyFoodCountryByFood(food)
//            }

            // ViewModel 사용해서 Repo접근
            foodViewModel.modifycountryByName(food)
        }

        // delete food
        binding.btnDelete.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val food = Food(0, foodName, "")
//            // Repo 직접 사용
//            CoroutineScope(Dispatchers.IO).launch {
//                foodRepo.removeFoodByName(food)       // 음식 이름으로 삭제
//            }

            // ViewModel 사용해서 Repo접근
            foodViewModel.removeFoodByName(foodName)
        }

    }
}