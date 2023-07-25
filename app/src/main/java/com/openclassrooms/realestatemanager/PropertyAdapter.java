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
    private List<PropertyModel> properties;
    private List<Bitmap> images;
    private Context context;

    public PropertyAdapter(Context context, List<PropertyModel> properties, List<Bitmap> images) {
        this.context = context;
        this.properties = properties;
        this.images = images;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_property, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position) {
        PropertyModel property = properties.get(position);
        Bitmap image = images.get(position);

        holder.propertyName.setText(property.getPropertyName());
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
            // Get the position of the item clicked
            int position = getAdapterPosition();
            // Check if position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Remove the property and image from the list
                properties.remove(position);
                images.remove(position);
                // Notify the adapter about the removed item
                notifyItemRemoved(position);
                return true;
            }
            return false;
        }
    }
}
