package com.irrigai.mobile.ui.farm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.irrigai.mobile.R;
import com.irrigai.mobile.data.local.entity.FarmEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class FarmSelectionAdapter extends RecyclerView.Adapter<FarmSelectionAdapter.ViewHolder> {

    public interface OnFarmClickListener {
        void onFarmClick(FarmEntity farm);
    }

    private final List<FarmEntity> farms;
    private final OnFarmClickListener listener;

    public FarmSelectionAdapter(List<FarmEntity> farms, OnFarmClickListener listener) {
        this.farms = farms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_farm_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FarmEntity farm = farms.get(position);
        holder.tvName.setText(farm.name != null ? farm.name : holder.itemView.getContext().getString(R.string.farm));
        // Placeholder values – can be wired to real data later
        holder.tvRobots.setText(holder.itemView.getContext().getString(R.string.farm_card_robots_placeholder));
        holder.tvLocation.setText(holder.itemView.getContext().getString(R.string.farm_card_location_placeholder));
        holder.tvLastActivity.setText(holder.itemView.getContext().getString(R.string.farm_card_last_activity_placeholder));

        // Default placeholder icon
        holder.imgFarm.setImageResource(R.drawable.ic_nav_farm);

        // Try to load specific image from assets/video for Beja / Nabel
        if (farm.name != null) {
            String lower = farm.name.toLowerCase(Locale.US);
            String baseName = null;
            if (lower.contains("beja")) baseName = "beja";
            else if (lower.contains("nabel")) baseName = "nabel";

            if (baseName != null) {
                Context ctx = holder.itemView.getContext();
                boolean loaded = loadAssetImage(ctx, "video/" + baseName + ".jpg", holder.imgFarm);
                if (!loaded) {
                    loadAssetImage(ctx, "video/" + baseName + ".png", holder.imgFarm);
                }
            }
        }

        holder.card.setOnClickListener(v -> {
            if (listener != null) listener.onFarmClick(farm);
        });
    }

    @Override
    public int getItemCount() {
        return farms.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView imgFarm;
        TextView tvName;
        TextView tvRobots;
        TextView tvLocation;
        TextView tvLastActivity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = (CardView) itemView;
            imgFarm = itemView.findViewById(R.id.imgFarm);
            tvName = itemView.findViewById(R.id.tvFarmName);
            tvRobots = itemView.findViewById(R.id.tvRobots);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvLastActivity = itemView.findViewById(R.id.tvLastActivity);
        }
    }

    private static boolean loadAssetImage(Context ctx, String assetPath, ImageView into) {
        InputStream is = null;
        try {
            is = ctx.getAssets().open(assetPath);
            Bitmap bmp = BitmapFactory.decodeStream(is);
            if (bmp != null) {
                into.setImageBitmap(bmp);
                return true;
            }
        } catch (IOException ignored) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {}
            }
        }
        return false;
    }
}

