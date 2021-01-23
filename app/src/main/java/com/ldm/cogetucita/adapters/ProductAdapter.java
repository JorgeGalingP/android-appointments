package com.ldm.cogetucita.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ldm.cogetucita.activities.AdminActivity;
import com.ldm.cogetucita.activities.ProductActivity;
import com.ldm.cogetucita.activities.RegistryActivity;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.models.Product;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolFamily;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Activity activity;
    private List<Product> productList;

    public ProductAdapter(Activity activity,
                          List<Product> productList){
        this.activity = activity;
        this.productList = productList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        public TextView nameTextView;
        public TextView descriptionTextView;
        public TextView idTextView;
        public TextView nameAdminTextView;
        public TextView descriptionAdminTextView;
        public TextView priceTextView;
        public TextView imageTextView;
        private Context context;

        public ViewHolder(Context context,
                          View itemView) {
            super(itemView);

            // set views
            if (context instanceof ProductActivity){
                imageView = itemView.findViewById(R.id.imageView);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            }
            if (context instanceof AdminActivity){
                idTextView = itemView.findViewById(R.id.idTextView);
                nameAdminTextView = itemView.findViewById(R.id.nameTextView);
                descriptionAdminTextView = itemView.findViewById(R.id.descriptionTextView);
                priceTextView = itemView.findViewById(R.id.priceTextView);
                imageTextView = itemView.findViewById(R.id.imageTextView);
            }

            // store the context
            this.context = context;

            // attach a click listener to the entire row view
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION
                    && this.context instanceof ProductActivity) {
                Product product = productList.get(position);

                Intent intent = new Intent(activity, RegistryActivity.class);
                intent.putExtra("id", product.getId().toString()); // product id parameter

                activity.startActivity(intent);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // usually involves inflating a layout from XML and returning the holder
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View productView = null;

        if (context instanceof ProductActivity){
            productView = layoutInflater.inflate(R.layout.item_product, parent, false);
        }
        if (context instanceof AdminActivity){
            productView = layoutInflater.inflate(R.layout.item_admin_product, parent, false);
        }

        return new ViewHolder(context, productView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // involves populating data into the item through holder
        Product product = productList.get(position);

        if (holder.context instanceof ProductActivity) {
            // set TextViews
            TextView textViewName = holder.nameTextView;
            textViewName.setText(product.getName());
            TextView textViewDescription = holder.descriptionTextView;
            textViewDescription.setText(product.getDescription());

            // set ImageView
            ImageView imageViewImage = holder.imageView;

            // set AssetManager
            AssetManager assetManager = holder.context.getAssets();
            InputStream inputStream = null;
            try {
                inputStream = assetManager.open(product.getImage());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // get Bitmap from product's image
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            if (bitmap != null) {
                imageViewImage.setImageBitmap(bitmap);
            } else {
                imageViewImage.setImageResource(R.drawable.ic_launcher_background);
            }
        }

        if (holder.context instanceof AdminActivity) {
            // set TextViews
            TextView textViewId = holder.idTextView;
            textViewId.setText("P-" + product.getId());

            TextView textViewName = holder.nameAdminTextView;
            textViewName.setText(product.getName());

            TextView textViewDescription = holder.descriptionAdminTextView;
            textViewDescription.setText(product.getDescription());

            TextView textViewPrice = holder.priceTextView;
            textViewPrice.setText(product.getPrice() + " euros");

            TextView textViewImage = holder.imageTextView;
            textViewImage.setText(product.getImage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
