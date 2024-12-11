package ddwu.com.mobileapp.week10.newnotitest

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.security.identity.PersonalizationData
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ddwu.com.mobileapp.week10.newnotitest.databinding.ActivityMainBinding
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // 방송국 번호
    val channelID by lazy {
        resources.getString(R.string.channel_id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        // 앱이 실행 되자 마자 채널 생성 (T or F)
        createNotificationChannel()

        mainBinding.btnNoti.setOnClickListener {
            Thread {
                // 3초 대기
                sleep(3000)
                // 알림 생성
                showNotification()
            }.start()
        }

        mainBinding.btnNotiAction.setOnClickListener {
            Thread {
                sleep(3000)
                // 알림 생성
                showNotificationWithAction()
            }.start()
        }

    }

    // 채널 생성 (앱이 실행될 때)
    // chanel 생성 (API level 26 이상 에서 사용)
    private fun createNotificationChannel() {
        // 최소 버전 (O = 오레오 버전)보다 크면 channel 만듦
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Notification Channel 의 생성

            val name = "Test Channel"
            val descriptionText = "Test Channel Message"
            // 알림의 우선 순위 (알림의 중요도)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            // channel 생성
            val mChannel = NotificationChannel(channelID, name, importance)

            mChannel.description = descriptionText // description 삽입

            // Channel 을 시스템(manager)에 등록, 등록 후에는 중요도 변경 불가
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel) // 채널 생성
            // 시스템 에게 channel 이 사용 가능한 지 확인 (T or F)
            Toast.makeText(applicationContext, "${notificationManager.areNotificationsEnabled()}", Toast.LENGTH_SHORT).show()

//            notificationManager.deleteNotificationChannel(channelID)    // 채널 삭제
        }
    }


    // 알림 생성 (builder 사용)
    private fun showNotification() {
        checkNotificationPermission()

        // activity 에서 activity 띄움 (알림 누르면 AlertActivity 띄움)
        val intent = Intent(this, AlertActivity::class.java).apply {
            // 이전 알림 삭제 하고, 새로 띄우기
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // intent 를 pendingIntent 로 포장 해서 시스템 에 전달
        // Activity 포장 -> getActivity
        // BroadCast 포장 -> getBroadcast, System 포장 -> getSystem, requestCode -> pendingIntent 구분 용도
        // PendingIntent 는 변경 불가능
        // requestCode: 식별 정보
        val pendingIntent: PendingIntent
                = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Builder 객체 (Noti 만들 준비)
        val newNoti = NotificationCompat.Builder(this, channelID)
            // 필수 요소 (밑 부분 나머지는 선택)
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
        val notiManager = NotificationManagerCompat.from(this)
        // 알림 실행 (식별 아이디, noti 객체)
        val noti : Notification = newNoti.build() // 객체 생성
        notiManager.notify(100, noti)
    }


    private fun showNotificationWithAction() {
        checkNotificationPermission()

        // activity에서 br 띄움 (알림 누르면 AlertActivity 띄움)
        val intent = Intent(this, AlertBroadcastReceiver::class.java).apply {
            action = "ACTION_SNOOZE" // br의 종류 (방송 정보, 구분 위한)
            putExtra("NOTI_ID", 200) // 전달하는 값 (방송 내용)
        }

        // intent를 pendingIntent로 포장해서 시스템에 전달
        // Activity 포장 -> getActivity
        // BroadCast 포장 -> getBroadcast, System 포장 -> getSystem, requestCode -> pendingIntent 구분 용도
        // 변경 불가능
        val pendingIntent: PendingIntent
                = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Builder 객체
        val newNoti = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.drawable.ic_stat_name) // Image Asset
            .setContentTitle("알림 제목")
            .setContentText("짧은 알림 내용")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("확장시 확인할 수 있는 긴 알림 내용"))
            // 8.0 이상에서는 대신 Channel의 중요도로 설정
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent) // 시스템에 전달할 Intent
            .setAutoCancel(true) // 클릭하면 바로 닫힘
            // 알림 메시지에 버튼을 만듦 = addAction
            .addAction(R.drawable.ic_stat_name, "쉬기", pendingIntent)

        // notiManager
        val notiManager = NotificationManagerCompat.from(this)
        // 알림 실행 (식별 아이디, noti 객체)
        val noti : Notification = newNoti.build() // 객체 생성
        notiManager.notify(100, noti)
    }


    /*알림 권한 확인*/
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // 권한이 없는 경우 권한 요청
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }

    /*권한 요청 결과 확인*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(applicationContext, "사용권한 승인, 버튼 다시 클릭!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "권한 필요", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


}