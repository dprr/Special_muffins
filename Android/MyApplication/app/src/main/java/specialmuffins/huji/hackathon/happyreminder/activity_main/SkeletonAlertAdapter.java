package specialmuffins.huji.hackathon.happyreminder.activity_main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import specialmuffins.huji.hackathon.happyreminder.OneSkeletonActivity;
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
    private MainActivity activity;

    public SkeletonAlertAdapter(List<SkeletonAlert> skeletons, MainActivity activity) {
        this.skeletons = skeletons;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_recycler_skeleton_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.title.setText(skeletons.get(position).reminderTxt);
        holder.allView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, OneSkeletonActivity.class));
                OneSkeletonActivity.isSkeletonNew = false;
                OneSkeletonActivity.skeletonAlertToWorkWith = skeletons.get(position);
            }
        });
    }

    public void updateSkeletons(List<SkeletonAlert> newSkeletons) {
        skeletons = newSkeletons;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return skeletons.size();
    }
}
