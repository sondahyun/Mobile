package ddwu.com.mobileapp.week12.maptest

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import ddwu.com.mobileapp.week12.maptest.databinding.ActivityMainBinding
import ddwu.com.mobileapp.week12.maptest.ui.MapViewModel
import ddwu.com.mobileapp.week12.maptest.ui.MapViewModelFactory

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivityTag"

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    private lateinit var googleMap: GoogleMap

    private val mapReadyCallback = object : OnMapReadyCallback {
        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            Log.d(TAG, "GoogleMap is ready")
            googleMap.setOnMapLongClickListener { latlng : LatLng ->
                Log.d(TAG, "롱클릭 위도: ${latlng.latitude}, " +
                        "경도: ${latlng.longitude}")
                addMarker(latlng)
            }

            // 마커 클릭
            googleMap.setOnMarkerClickListener { marker ->
                Toast.makeText(this@MainActivity, marker.tag.toString(), Toast.LENGTH_SHORT).show()
                false // true일 경우에는 이벤트 처리 종료이므로, infoWindow 미출력
            }

            // 마커 InfoWindow 클릭 이벤트 처리
            googleMap.setOnInfoWindowClickListener { marker ->
                Toast.makeText(this@MainActivity, marker.title.toString(), Toast.LENGTH_SHORT).show()
            }
         }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(mapReadyCallback)



        val mapViewModel : MapViewModel by viewModels {
            MapViewModelFactory( (application as MapApplication).mapRepository )
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(3000)
            .setMinUpdateIntervalMillis(5000)
            .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val currentLocation: Location = locationResult.locations[0]
                Log.d(TAG, "위도: ${currentLocation.latitude}, " +
                        "경도: ${currentLocation.longitude}")

                var targetLoc = LatLng(currentLocation.latitude, currentLocation.longitude)
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 17F))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 17F))
            }
        }

        binding.btnStart.setOnClickListener {
            // 위치 정보 받으려면 permission 확인 해야 함
            checkPermissions()
            // 위치 확인
            startLocationRequest()
        }

        binding.btnStop.setOnClickListener {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            var targetLoc = LatLng(37.606120, 127.041808)
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 17F))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLoc, 17F))
        }

        binding.btnGetLast.setOnClickListener {

        }

        binding.btnClear.setOnClickListener {

        }
    }

    private lateinit var markerOptions: MarkerOptions
    // marker 객체
    private var centerMarker: Marker? = null

    fun addMarker(targetLoc: LatLng) {
        markerOptions = MarkerOptions()
        markerOptions.position(targetLoc)
        markerOptions.title("marker title")
        markerOptions.snippet("marker 말풍선")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

        centerMarker = googleMap.addMarker(markerOptions)
        // 마커 터치시 InfoWindow 표시
//        centerMarker?.showInfoWindow()
        // 필요한 정보 (DB에 저장)
        centerMarker?.tag = "database_id"
    }




//    lateinit var polylineOptions : PolylineOptions  // 선의 옵션, 멤버변수로 지정
//    lateinit var currentLine : Polyline     // 지도에 추가한 선, 멤버변수로 지정

//    fun drawLine(latLng: LatLng) {
//        val newLatLng = LatLng(latLng.latitude, latLng.longitude)
//        val points = currentLine.points // 선을 구성하는 점 집합
//        points.add(newLatLng)   // 점 집합에 새로운 점 추가
//        currentLine.points = points
//        Log.d(TAG, "add line")
//    }


    override fun onPause() {
        super.onPause()
        // 위치 수신 중지
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun startLocationRequest() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback, Looper.getMainLooper())
    }


    // Permission 확인
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions() ) { permissions ->
        when {
            permissions.getOrDefault(ACCESS_FINE_LOCATION, false) ->
                Log.d(TAG, "정확한 위치 사용")
            permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) ->
                Log.d(TAG, "근사 위치 사용")
            else ->
                Log.d(TAG, "권한 미승인")
        }
    }


    private fun checkPermissions() {
        if ( checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "필요 권한 있음")
//            startLocationRequest()
        } else {
            locationPermissionRequest.launch(
                arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            )
        }
    }

}