package com.example.explorenow.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorenow.R;
import com.example.explorenow.data.Landmark;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

public class LandmarkAdapter extends ListAdapter<Landmark, LandmarkAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Landmark l);
    }

    private final LayoutInflater inflater;
    private final OnItemClickListener listener;

    public LandmarkAdapter(Context context, OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Landmark> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Landmark>() {
                @Override
                public boolean areItemsTheSame(@NonNull Landmark oldItem, @NonNull Landmark newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Landmark oldItem, @NonNull Landmark newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_landmark, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Landmark l = getItem(position);
        holder.tvName.setText(l.name);
        holder.tvDesc.setText(l.description != null ? l.description : "(no description)");
        holder.tvAddress.setText(l.address != null ? l.address : "(no address)");

        holder.itemView.setOnClickListener(v -> listener.onItemClick(l));

        holder.btnGenerateQR.setOnClickListener(v -> {
            try {
                String qrText = (l.name + "\n" + l.address + "\n" + l.description);
                BitMatrix matrix = new MultiFormatWriter().encode(qrText, BarcodeFormat.QR_CODE, 400, 400);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);

                ImageView qrImage = new ImageView(holder.itemView.getContext());
                qrImage.setImageBitmap(bitmap);

                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("QR Code")
                        .setView(qrImage)
                        .setPositiveButton("Close", null)
                        .show();
            } catch (Exception e) {
                Toast.makeText(holder.itemView.getContext(), "QR generation error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvAddress;
        Button btnGenerateQR;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDescription);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnGenerateQR = itemView.findViewById(R.id.idBtnGenerateQR);
        }
    }
}
