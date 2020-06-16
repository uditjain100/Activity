package udit.programmer.co.activity

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.DetectedActivity

class ActivityAdapter(var list: MutableList<DetectedActivity>) :
    RecyclerView.Adapter<ActivityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateActivities(detectedActivities: MutableList<DetectedActivity>) {
        val map = hashMapOf<Int, Int>()
        for (activity in detectedActivities) {
            map.put(activity.type, activity.confidence)
        }
        val temporaryList = mutableListOf<DetectedActivity>()
        Log.d("Ceased Meteor", "Yo man i am here 000 ${temporaryList.size}")
        for (activity in ActivityRecognitionServices().possibleActivities) {
            if (map.containsKey(activity))
                temporaryList.add(DetectedActivity(activity, map.get(activity)!!))
        }
        list.clear()
        for (activity in temporaryList) {
            list.add(activity)
        }
    }
}