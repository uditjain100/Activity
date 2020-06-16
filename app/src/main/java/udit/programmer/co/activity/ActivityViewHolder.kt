package udit.programmer.co.activity

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.DetectedActivity
import kotlinx.android.synthetic.main.item_layout.view.*

class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(detectedActivity: DetectedActivity) {
        itemView.activity_type.text =
            ActivityRecognitionServices().getDetectedActivity(detectedActivity)
        itemView.confidence_percentage.text = detectedActivity.confidence.toString()
    }
}