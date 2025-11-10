package com.example.explorenow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorenow.R;
import com.example.explorenow.data.Landmark;

import java.util.ArrayList;
import java.util.List;

public class LandmarkAdapter extends RecyclerView.Adapter<LandmarkAdapter.LandmarkViewHolder> {

    private final List<Landmark> landmarks = new ArrayList<>();

    public interface OnItemClick {
        void onClick(Landmark landmark);
    }

    public OnItemClick lambdaOnClick;
    public OnItemClick lambdaOnQrClick;

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


        holder.cardView.setOnClickListener(v -> {
            if (lambdaOnClick != null) lambdaOnClick.onClick(landmark);
        });

        holder.btnGenerateQR.setOnClickListener(v -> {
            if (lambdaOnQrClick != null) lambdaOnQrClick.onClick(landmark);
        });
    }

    @Override
    public int getItemCount() {
        return landmarks.size();
    }

    public void submitList(List<Landmark> list) {
        landmarks.clear();
        landmarks.addAll(list);
        notifyDataSetChanged();
    }

    static class LandmarkViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvName, tvAddress, tvDescription;
        Button btnGenerateQR;

        public LandmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRoot);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnGenerateQR = itemView.findViewById(R.id.idBtnGenerateQR);
        }
    }
}
