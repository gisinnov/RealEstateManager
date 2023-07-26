package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder> {
    private List<Property> properties;
    private List<Bitmap> images;
    private Context context;

    public PropertyAdapter(Context context, List<Property> properties, List<Bitmap> images) {
        this.context = context;
        this.properties = properties;
        this.images = images;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property, parent, false);
        return new PropertyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        Property property = properties.get(position);
        Bitmap image = images.get(position);

        holder.propertyName.setText(property.getPropertyName()); // Utilisation de getPropertyName()
        holder.imageView.setImageBitmap(image);
    }


    @Override
    public int getItemCount() {
        return properties.size();
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        ImageView imageView;
        TextView propertyName;

        PropertyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            propertyName = itemView.findViewById(R.id.property_name);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                properties.remove(position);
                images.remove(position);
                notifyItemRemoved(position);
                return true;
            }
            return false;
        }
    }
}
