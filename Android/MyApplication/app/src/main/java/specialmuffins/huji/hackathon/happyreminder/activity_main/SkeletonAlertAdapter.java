package specialmuffins.huji.hackathon.happyreminder.activity_main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import specialmuffins.huji.hackathon.happyreminder.R;
import specialmuffins.huji.hackathon.happyreminder.firebase_db.entity.SkeletonAlert;

/**
 * Created by reem on 19/05/17.
 */

public class SkeletonAlertAdapter extends RecyclerView.Adapter<SkeletonAlertAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        View allView;

        public ViewHolder(View itemView) {
            super(itemView);
            allView = itemView;
            title = (TextView) allView.findViewById(R.id.tv_item_skeleton_title);
        }
    }

    private List<SkeletonAlert> skeletons;

    public SkeletonAlertAdapter(List<SkeletonAlert> skeletons) {
        this.skeletons = skeletons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_recycler_skeleton_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(skeletons.get(position).reminderTxt);
    }

    @Override
    public int getItemCount() {
        return skeletons.size();
    }
}
