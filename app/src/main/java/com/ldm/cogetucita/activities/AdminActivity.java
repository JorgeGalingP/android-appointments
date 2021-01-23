package com.ldm.cogetucita.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ldm.cogetucita.R;
import com.ldm.cogetucita.adapters.AppointmentAdapter;
import com.ldm.cogetucita.adapters.ProductAdapter;
import com.ldm.cogetucita.models.Appointment;
import com.ldm.cogetucita.models.Product;
import com.ldm.cogetucita.models.State;
import com.ldm.cogetucita.repositories.AppointmentRepository;
import com.ldm.cogetucita.repositories.ProductRepository;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Administración de los productos");

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // init Repository
        ProductRepository productRepository = new ProductRepository(this);

        // set Products
        setProductList(productRepository.findAllProducts());
        progressBar.setVisibility(View.GONE);

        // set RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // set LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);

        // set Adapter
        ProductAdapter productAdapter = new ProductAdapter(this, getProductList());
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // go to the main activity
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}