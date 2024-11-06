package ddwu.com.mobile.week10.notitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ddwu.com.mobile.week10.notitest.databinding.ActivityAlertBinding


class AlertActivity : AppCompatActivity() {

    val alertBinding by lazy {
        ActivityAlertBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(alertBinding.root)

        alertBinding.btnAlertClose.setOnClickListener {
            finish()
        }
    }
}