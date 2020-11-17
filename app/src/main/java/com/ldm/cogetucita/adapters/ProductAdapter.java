package com.ldm.cogetucita.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ldm.cogetucita.activities.ProductActivity;
import com.ldm.cogetucita.activities.RegistryActivity;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ProductActivity productActivity;

    public ProductAdapter(ProductActivity productActivity){
        this.productActivity = productActivity;
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
                Product product = productActivity.getProductList().get(position);

                Intent intent = new Intent(productActivity, RegistryActivity.class);
                intent.putExtra("id", product.getId().toString()); // product id parameter

                productActivity.startActivity(intent);
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
        Product product = productActivity.getProductList().get(position);

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
        return productActivity.getProductList().size();
    }
}
