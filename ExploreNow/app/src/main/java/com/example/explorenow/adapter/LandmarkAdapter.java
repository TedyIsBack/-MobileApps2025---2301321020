package com.example.explorenow.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorenow.LandmarkActivity;
import com.example.explorenow.R;
import com.example.explorenow.data.Landmark;

import java.util.ArrayList;
import java.util.List;

public class LandmarkAdapter extends RecyclerView.Adapter<LandmarkAdapter.LandmarkViewHolder> {

    private final List<Landmark> landmarks = new ArrayList<>();
    private OnQrClick qrClick;
    private OnDeleteClick deleteClick;

    public interface OnQrClick {
        void onQrClick(Landmark landmark);
    }

    public interface OnDeleteClick {
        void onDeleteClick(Landmark landmark);
    }

    public void setOnQrClick(OnQrClick click) { this.qrClick = click; }
    public void setOnDeleteClick(OnDeleteClick click) { this.deleteClick = click; }

    @NonNull
    @Override
    public LandmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_landmark, parent, false);
        return new LandmarkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LandmarkViewHolder holder, int position) {
        Landmark landmark = landmarks.get(position);

        holder.tvName.setText(landmark.name);
        holder.tvAddress.setText(landmark.address);
        holder.tvDescription.setText(landmark.description);

        if (landmark.photoUri != null) {
            holder.imgLandmark.setImageURI(Uri.parse(landmark.photoUri));
        } else {
            holder.imgLandmark.setImageResource(R.drawable.ic_launcher_background);
        }

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), LandmarkActivity.class);
            intent.putExtra("landmark_id", landmark.id);
            holder.itemView.getContext().startActivity(intent);
        });

        holder.btnQr.setOnClickListener(v -> {
            if (qrClick != null) qrClick.onQrClick(landmark);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteClick != null) deleteClick.onDeleteClick(landmark);
        });
    }

    @Override
    public int getItemCount() { return landmarks.size(); }

    public void submitList(List<Landmark> list) {
        landmarks.clear();
        landmarks.addAll(list);
        notifyDataSetChanged();
    }

    static class LandmarkViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgLandmark;
        TextView tvName, tvAddress, tvDescription;
        Button btnQr, btnDelete;

        public LandmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRoot);
            imgLandmark = itemView.findViewById(R.id.imgLandmark);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnQr = itemView.findViewById(R.id.btnQr);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
