package com.ldm.cogetucita.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.ldm.cogetucita.MainActivity;
import com.ldm.cogetucita.R;
import com.ldm.cogetucita.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private MainActivity mainActivity;

    public ProductAdapter(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView productNameTextView;
        private Context context;

        public ViewHolder(MainActivity mainActivity,
                          Context context,
                          View itemView) {
            super(itemView);

            // set views
            productNameTextView = itemView.findViewById(R.id.nameTextView);

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
        ViewHolder productViewHolder = new ViewHolder(this.mainActivity, context, productView);

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // involves populating data into the item through holder
        Product product = mainActivity.getProductList().get(position);

        // set TextView
        TextView textView = holder.productNameTextView;
        textView.setText(product.getName());
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
