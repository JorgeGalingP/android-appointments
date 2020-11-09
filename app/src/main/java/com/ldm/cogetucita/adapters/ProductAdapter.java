package com.ldm.cogetucita.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.ldm.cogetucita.BuildConfig;
import com.ldm.cogetucita.MainActivity;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.models.Product;

import java.io.File;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private MainActivity mainActivity;

    public ProductAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        public TextView nameTextView;
        public TextView descriptionTextView;
        private Context context;

        public ViewHolder(Context context,
                          View itemView) {
            super(itemView);

            // set views
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);

            // store the context
            this.context = context;

            // attach a click listener to the entire row view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Product product = mainActivity.getProductList().get(position);
                Toast.makeText(this.context, "Selected " + product.getName() + "!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // usually involves inflating a layout from XML and returning the holder
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View productView = layoutInflater.inflate(R.layout.item_product, parent, false);
        ViewHolder productViewHolder = new ViewHolder(context, productView);

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // involves populating data into the item through holder
        Product product = mainActivity.getProductList().get(position);

        // set ImageView
        ImageView imageViewImage = holder.imageView;

        int resId = holder.context.getResources().getIdentifier(product.getImage(), "drawable", holder.context.getPackageName());
        if (resId != 0){
            imageViewImage.setImageResource(resId);
        }
        else{
            imageViewImage.setImageResource(R.drawable.ic_launcher_background);
        }

        // set TextViews
        TextView textViewName = holder.nameTextView;
        textViewName.setText(product.getName());
        TextView textViewDescription = holder.descriptionTextView;
        textViewDescription.setText(product.getDescription());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mainActivity.getProductList().size();
    }
}
