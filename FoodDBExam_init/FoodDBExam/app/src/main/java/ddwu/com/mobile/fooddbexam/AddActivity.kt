package ddwu.com.mobile.fooddbexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ddwu.com.mobile.fooddbexam.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    val addBinding by lazy {
        ActivityAddBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addBinding.root)

    }
}