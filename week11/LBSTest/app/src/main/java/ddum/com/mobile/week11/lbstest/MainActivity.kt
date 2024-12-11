package ddum.com.mobile.week11.lbstest

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import ddum.com.mobile.week11.lbstest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTag"

    val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        // location 요청할 수 있는 client 객체
        // FusedLocationProviderClient 객체
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // request 객체 만들어 전달, callback 에서 응답 수행
        // LocationRequest 객체
        locationRequest  = LocationRequest.Builder(3000) // 기본 간격
            .setMinUpdateIntervalMillis(5000) // 최소 업데이트 간격
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY) // 배터리 고려하면서 정확하도록
            .build() // locationRequest 객체 생성

        // LocationCallback 함수 생성 (위도, 경도 수신)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                // Location: 위도, 경도 정보 닮을 수 있는 위치 정보 객체
                val currentLocation : Location = locationResult.locations[0]
                Log.d(TAG, "위도: ${currentLocation.latitude}, " + "경도: ${currentLocation.longitude}")
            }
        }

        // geocoder
        geocoder = Geocoder(this, Locale.getDefault())


        mainBinding.btnLocation.setOnClickListener {
            checkPermissions()
            startLocationRequest()
        }

        mainBinding.btnGeocoding.setOnClickListener {
            // 같은 건물이면 주소 여러개일 수 있음 (maxResult) (addresses = List<Address>
            geocoder.getFromLocation(37.6068163, 127.04238319999999, 5) { addresses ->
                // 이미 받아온거라 CoroutineScope 생략 가능
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d(TAG, addresses.get(0).getAddressLine(0).toString())
                }
            }
            geocoder.getFromLocationName("동덕여자대학교", 5) { addresses ->
                // 이미 받아온거라 CoroutineScope 생략 가능
                CoroutineScope(Dispatchers.Main).launch {
                    Log.d(TAG, "위도: ${addresses.get(0).latitude}, " +
                    "경도: ${addresses.get(0).longitude}")
                }
            }
        }

        mainBinding.btnExternal.setOnClickListener {
            callExternalMap()
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun startLocationRequest() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    fun callExternalMap() {
        val locLatLng   // 위도/경도 정보로 지도 요청 시
            = String.format("geo:%f,%f?z=%d", 37.606320, 127.041808, 17)
        val locName     // 위치명으로 지도 요청 시
                = "https://www.google.co.kr/maps/place/" + "Hawolgok-dong"
        val uri = Uri.parse(locLatLng)
        // val uri = Uri.parse(locName)

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }




    // 사용자가 선택한 permission 요청 Activity 결과 받음
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions() ) { permissions ->
        when {
            // FINE 먼저 검사
            permissions.getOrDefault(ACCESS_FINE_LOCATION, false) ->
                Log.d(TAG, "정확한 위치 사용")
            // COARSE 검사
            permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) ->
                Log.d(TAG, "근사 위치 사용")
            else ->
                // 권한 사용 불가
                Log.d(TAG, "권한 미승인") // FLAG = T or F 설정 (F면 위치 사용 코드 실행 안됨)

        }
    }


    private fun checkPermissions() {
        // 권한 가지고 있는지 확인 (checkSelfPermission(ACCESS_COARSE_LOCATION), checkSelfPermission(ACCESS_FIND_LOCATION))
        if ( checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "필요 권한 있음")
            // 위치 관련 기능 추가 위치
        } else {
            // 권한이 없을 경우 권한 요청 (locationPermissionRequest) (FIND_LOCATION은 COARSE_LOCATION과 같이 쓰임)
            // 요청 화면 (대화 상자)은 시스템 에서 만들어 줌
            locationPermissionRequest.launch(
                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            )
        }
    }
}