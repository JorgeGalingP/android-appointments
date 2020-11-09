package com.ldm.cogetucita;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ldm.cogetucita.adapters.ProductAdapter;
import com.ldm.cogetucita.bbdd.AdminSQLiteOpenHelper;
import com.ldm.cogetucita.models.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ProgressBar progressBar;

    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }

        // init Database
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(this, "db", null, 1);

        // set Products
        setProductList(adminSQLiteOpenHelper.searchAllProducts(this));
        progressBar.setVisibility(View.GONE);

        // set RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // set Adapter
        productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);

        // set LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.getRecycledViewPool().setMaxRecycledViews(R.id.recyclerView, 0);

        // set Fab
        FloatingActionButton fab = findViewById(R.id.reloadFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Added example!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}