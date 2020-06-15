package udit.programmer.co.activity

import android.app.Activity
import android.app.IntentService
import android.content.Intent
import android.util.Log
import android.widget.TextView
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity
import kotlinx.android.synthetic.main.activity_main.text_view

class ActivityRecognitionServices : IntentService {

    constructor() : super("ActivityRecognitionServices")

    constructor(name: String) : super(name)

    override fun onHandleIntent(intent: Intent?) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            handelDetecteddActivity(ActivityRecognitionResult.extractResult(intent).probableActivities)
        }
    }

    private fun handelDetecteddActivity(probableActivities: MutableList<DetectedActivity>) {
        for (activity in probableActivities) {
            when (activity.type) {
                DetectedActivity.IN_VEHICLE -> recognised_activity = "IN_VEHICLE"
                DetectedActivity.ON_BICYCLE -> recognised_activity = "ON_BICYCLE"
                DetectedActivity.ON_FOOT -> recognised_activity = "ON_FOOT"
                DetectedActivity.RUNNING -> recognised_activity = "RUNNING"
                DetectedActivity.STILL -> recognised_activity = "STILL"
                DetectedActivity.TILTING -> recognised_activity = "TILTING"
                DetectedActivity.WALKING -> recognised_activity = "WALKING"
                DetectedActivity.UNKNOWN -> recognised_activity = "UNKNOWN"
            }
            Log.d("Ceased Meteor", "$recognised_activity :-> ${activity.confidence}")
        }
    }
}