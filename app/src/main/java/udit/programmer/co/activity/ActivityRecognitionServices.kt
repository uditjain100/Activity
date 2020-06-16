package udit.programmer.co.activity

import android.app.IntentService
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import com.google.android.gms.location.ActivityRecognitionResult
import com.google.android.gms.location.DetectedActivity
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


class ActivityRecognitionServices : IntentService {

    var recognised_activity = "None"

    val possibleActivities = mutableListOf(
        DetectedActivity.STILL,
        DetectedActivity.ON_FOOT,
        DetectedActivity.WALKING,
        DetectedActivity.RUNNING,
        DetectedActivity.IN_VEHICLE,
        DetectedActivity.ON_BICYCLE,
        DetectedActivity.TILTING,
        DetectedActivity.UNKNOWN
    )

    constructor() : super("ActivityRecognitionServices")

    constructor(name: String) : super(name)

    override fun onHandleIntent(intent: Intent?) {
        if (ActivityRecognitionResult.hasResult(intent)) {
            val result = ActivityRecognitionResult.extractResult(intent)
            val detectedActivities = result.probableActivities
            PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putString(
                    MainActivity().DETECTED_ACTIVITY,
                    detectedActivitiesToJson(detectedActivities)
                )
                .apply()
        }
    }

    fun getDetectedActivity(detectedActivity: DetectedActivity): String {
        when (detectedActivity.type) {
            DetectedActivity.IN_VEHICLE -> recognised_activity = "IN_VEHICLE"
            DetectedActivity.ON_BICYCLE -> recognised_activity = "ON_BICYCLE"
            DetectedActivity.ON_FOOT -> recognised_activity = "ON_FOOT"
            DetectedActivity.RUNNING -> recognised_activity = "RUNNING"
            DetectedActivity.STILL -> recognised_activity = "STILL"
            DetectedActivity.TILTING -> recognised_activity = "TILTING"
            DetectedActivity.WALKING -> recognised_activity = "WALKING"
            DetectedActivity.UNKNOWN -> recognised_activity = "UNKNOWN"
        }
        Log.d("Ceased Meteor", "$recognised_activity :-> ${detectedActivity.confidence}")
        return recognised_activity
    }

    fun detectedActivitiesToJson(detectedActivitiesList: MutableList<DetectedActivity>): String {
        return Gson().toJson(
            detectedActivitiesList,
            object : TypeToken<MutableList<DetectedActivity?>?>() {}.type
        )
    }

    fun detectedActivitiesFromJson(jsonArray: String): MutableList<DetectedActivity> {
        var list = Gson().fromJson<MutableList<DetectedActivity>>(
            jsonArray,
            object : TypeToken<MutableList<DetectedActivity>>() {}.type
        )
//        Log.d("Ceased Meteor" , "00 Yo man i am here ${list.size}")
        if (list.isNullOrEmpty()) list = mutableListOf()
        return list
    }

}