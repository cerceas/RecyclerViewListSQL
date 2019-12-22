package com.example.listviewandalertdialogwithsql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LandmarksAdapter extends RecyclerView.Adapter<LandmarksAdapter.ViewHolder> {
    private ArrayList<Landmarks> landmarks;

    public LandmarksAdapter(Context context,ArrayList<Landmarks> list){
    landmarks=list;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Icons;
        TextView Title;
        TextView Details;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

             Title=itemView.findViewById(R.id.TextViewTitle);
            Details=itemView.findViewById(R.id.TextViewDetails);
            Icons=itemView.findViewById(R.id.ImageViewRizal);

        }
    }

    @NonNull
    @Override
    public LandmarksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LandmarksAdapter.ViewHolder holder, int position) {
         holder.itemView.setTag(landmarks.get(position));

         holder.Title.setText(landmarks.get(position).getLandmarks());
            holder.Details.setText(landmarks.get(position).getID());

            if(landmarks.get(position).getPreferences().equals("Rizal")){
                holder.Icons.setImageResource(R.drawable.rizal);
            }else if(landmarks.get(position).getPreferences().equals("National Museum of Arts")){
                holder.Icons.setImageResource(R.drawable.nationalmuseumoffinearts);
            }else if(landmarks.get(position).getPreferences().equals("National Museum of History")){
                holder.Icons.setImageResource(R.drawable.nationalmuseumofhistory);
            }

    }

    @Override
    public int getItemCount() {
        return landmarks.size();
    }
}
