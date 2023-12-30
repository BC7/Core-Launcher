package com.sevenbitstudios.corelauncher;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DrawerRecyclerViewAdapter extends RecyclerView.Adapter<DrawerRecyclerViewAdapter.ViewHolder> {

    Context context;
    private static final String TAG = "DrawerRecyclerViewAdapter";

    private final List<AppInfo> appListDataSet;
    public DrawerRecyclerViewAdapter(Context context, List<AppInfo> appListData) {
        this.context = context;
        this.appListDataSet = appListData;
    }


    @Override
    public int getItemCount() {
        return appListDataSet.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        Log.d(TAG, "appListDataSet: " + appListDataSet.get(position).getName());

        String appLabel = appListDataSet.get(position).getName();
        Drawable appIcon = appListDataSet.get(position).getIcon();

        TextView textView = viewHolder.textView;
        textView.setText(appLabel);
        ImageView imageView = viewHolder.img;
        imageView.setImageDrawable(appIcon);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout rowItem;
        public TextView textView;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            rowItem = (ConstraintLayout) itemView.findViewById(R.id.drawerItem);
            textView = (TextView) itemView.findViewById(R.id.itemText);
            img = (ImageView) itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(view -> {
                int pos = getAdapterPosition();
                Context context = view.getContext();

                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(appListDataSet.get(pos).getPackageName());
                context.startActivity(launchIntent);
                Toast.makeText(view.getContext(), appListDataSet.get(pos).getName(), Toast.LENGTH_LONG).show();
            });
        }
    }
}