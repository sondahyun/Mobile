package ddwu.com.mobile.fooddbexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.fooddbexam.databinding.ActivityRemoveBinding

class RemoveActivity : AppCompatActivity() {

    val removeBinding by lazy {
        ActivityRemoveBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(removeBinding.root)
    }
}