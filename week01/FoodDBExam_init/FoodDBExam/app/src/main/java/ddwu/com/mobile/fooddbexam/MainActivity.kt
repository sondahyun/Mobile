package ddwu.com.mobile.fooddbexam

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.fooddbexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

//    lateinit var helper : FoodDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

//        helper = FoodDBHelper(this)

        binding.btnSelect.setOnClickListener{

        }

        binding.btnAdd.setOnClickListener{

        }

        binding.btnUpdate.setOnClickListener{

        }

        binding.btnRemove.setOnClickListener{

        }

    }

    fun addFood() {

    }

    fun modifyFood() {

    }

    fun deleteFood() {

    }

    fun showFoods() {

    }

}