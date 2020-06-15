package udit.programmer.co.activity

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.ActivityRecognition

var recognised_activity = "None"

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    val DETECTED_ACTIVITY = ".DETECTED_ACTIVITY"
    
    private lateinit var apiClient: GoogleApiClient
    private var tv = findViewById<TextView>(R.id.text_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiClient = GoogleApiClient.Builder(this)
            .addApi(ActivityRecognition.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        apiClient.connect()
    }

    override fun onConnected(bundle: Bundle?) {
        val intent = Intent(this, ActivityRecognitionServices::class.java)
        val pendingIntent =
            PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val task = ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
            apiClient,
            2000,
            pendingIntent
        )
        task.setResultCallback{

        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

}