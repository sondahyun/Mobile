package ddwu.com.mobileapp.week10.newnotitest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlertBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) { // intent 들어옴

        Toast.makeText(context, "휴식중...", Toast.LENGTH_LONG ).show()

        val notiId = intent?.getIntExtra("NOTI_ID", 0)      // intent 에 기록한 값 확인
        Log.d("AlertBroadcastReceiver", "Notification ID: ${notiId}")

//        /*알림을 선택하면 MainActivity 실행*/
//        val intent = Intent (context, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        context?.startActivity(intent)

    }
}