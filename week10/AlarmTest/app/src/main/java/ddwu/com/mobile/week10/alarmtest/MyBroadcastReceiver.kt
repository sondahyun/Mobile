package ddwu.com.mobile.week10.alarmtest

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("MyBr", "Alarm!!!")

        // activity에서 activity 띄움 (알림 누르면 AlertActivity 띄움)
        val newIntent = Intent(context, MainActivity::class.java).apply {
            // 이전 알림 삭제하고 새로 띄우기
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val channelID = context?.resources?.getString(R.string.channel_id)!!

        // intent를 pendingIntent로 포장해서 시스템에 전달
        // Activity 포장 -> getActivity
        // BroadCast 포장 -> getBroadcast, System 포장 -> getSystem, requestCode -> pendingIntent 구분 용도
        // 변경 불가능
        val pendingIntent: PendingIntent
                = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_IMMUTABLE)

        // Builder 객체
        val newNoti = NotificationCompat.Builder(context!!, channelID)
            .setSmallIcon(R.drawable.ic_stat_name) // Image Asset
            .setContentTitle("알림 제목")
            .setContentText("짧은 알림 내용")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("확장시 확인할 수 있는 긴 알림 내용"))
            // 8.0 이상에서는 대신 Channel의 중요도로 설정
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // 시스템에 전달할 Intent
            .setAutoCancel(true) // 클릭하면 바로 닫힘

        // notiManager
        val notiManager = NotificationManagerCompat.from(context)
        // 알림 실행 (식별 아이디, noti 객체)
        val noti : Notification = newNoti.build() // 객체 생성

        notiManager.notify(100, noti)
    }

}