package ddwu.com.mobileapp.week03.roomexam01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.roomexam01.data.Food
import ddwu.com.mobileapp.week03.roomexam01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    // view binding object
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

//    val foodDabase by lazy {
//        FoodDatabase.getDatabase(this)
//    }

//    val foodDao by lazy {
//        foodDabase.foodDao()
//    }

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


        // get all foods



        // food by country
        binding.btnShow.setOnClickListener {

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

        }

        // update food id 2
        binding.btnUpdate.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val countryName = binding.etCountry.text.toString()
            val targetFood = Food(2, foodName, countryName)

        }

        // update food id 3
        binding.btnDelete.setOnClickListener {
            val targetFood = Food(3, "", "")    // delete food _id 3
        }


    }
}