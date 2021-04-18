package com.example.qrcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CardList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapterRecycle;
    private RecyclerView.LayoutManager mLayoutManager;
    String[] name ={
            "Tanishq Goyal","Alpesh Gupta",
            "Tanisha Mudgal","Rajesh Pareek",
            "Ronak Gupta",
    };
    String[] mobile ={
            "12345","123456",
            "12344567","12345678",
            "123456789",
    };
    String[] address ={
            "Jaipur","Delhi",
            "Kota","Ajmer",
            "Pune",
    };
    Integer[] image={
            R.drawable.ic_baseline_account_circle_24,R.drawable.ic_baseline_account_circle_24,
            R.drawable.ic_baseline_account_circle_24,R.drawable.ic_baseline_account_circle_24,
            R.drawable.ic_baseline_account_circle_24,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        recyclerView=findViewById(R.id.recyclerview);

        ArrayList<ExampleItem> saved = new ArrayList<>();
        saved.add(new ExampleItem( "Tanishq Goyal", "94560456500","Jaipur"));
        saved.add(new ExampleItem( "Ronak Gupta", "9549649773","Delhi"));
        saved.add(new ExampleItem("Tanisha Mudgal", "8461678610","Kota"));
        saved.add(new ExampleItem("Rajesh Pareek", "6560665050","Kashmir"));
        saved.add(new ExampleItem("Alpesh Gupta", "9550960466","Ajmer"));

        ArrayList<ExampleItem> fav = new ArrayList<>();
        fav.add(new ExampleItem( "Tanishq Goyal", "94560456500","Jaipur"));
        fav.add(new ExampleItem( "Ronak Gupta", "9549649773","Delhi"));
        fav.add(new ExampleItem("Tanisha Mudgal", "8461678610","Kota"));


        if(getIntent().getIntExtra("type",1)==1){
           recyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            adapterRecycle = new ExampleAdapter(fav);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapterRecycle);

        }else{
            recyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            adapterRecycle = new ExampleAdapter(fav);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapterRecycle);

        }
    }
}