package udit.programmer.co.activity

import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var activityRecognitionClient: ActivityRecognitionClient
    val DETECTED_ACTIVITY = ".DETECTED_ACTIVITY"
    lateinit var adapter: ActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityRecognitionClient = ActivityRecognitionClient(this)
        requestUpdatesHandler()

        var detectedActivities = ActivityRecognitionServices().detectedActivitiesFromJson(
            PreferenceManager.getDefaultSharedPreferences(this).getString(DETECTED_ACTIVITY, "")!!
        )

        this.adapter = ActivityAdapter(detectedActivities)
        recycler_layout.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        recycler_layout.adapter = this.adapter

        refresh_btn.setOnClickListener {
            requestUpdatesHandler()
            detectedActivities = ActivityRecognitionServices().detectedActivitiesFromJson(
                PreferenceManager.getDefaultSharedPreferences(this).getString(DETECTED_ACTIVITY, "")!!
            )
            PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this)
            updateDetectedActivityList();
        }
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
        updateDetectedActivityList();
    }

    override fun onPause() {
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
        super.onPause()
    }

    private fun requestUpdatesHandler() {
        val intent = Intent(this, ActivityRecognitionServices::class.java)
        val pendingIntent =
            PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        activityRecognitionClient.requestActivityUpdates(3000, pendingIntent)
            .addOnSuccessListener { updateDetectedActivityList() }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals(DETECTED_ACTIVITY))
            updateDetectedActivityList()
    }

    private fun updateDetectedActivityList() {
        val detectedActivities = ActivityRecognitionServices().detectedActivitiesFromJson(
            PreferenceManager.getDefaultSharedPreferences(this)
                .getString(DETECTED_ACTIVITY, "")!!
        )
        Log.d("Ceased Meteor", " Yo man i am here ${detectedActivities.size}")
        this.adapter.updateActivities(detectedActivities)
    }

}