package com.example.firststepadmin.BottomNavigationThings.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firststepadmin.BottomNavigationThings.Inventory.A_CompleteViewOfPurchasedProduct;
import com.example.firststepadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class A_HelpCustomersWithOrderId extends AppCompatActivity {



   SearchView searchView;
   D_PastOrders d_pastOrders;
   View linearlayout;
   ImageView imageView;
   TextView productname,productprice;
   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__help_customers_with_order_id);
        linearlayout=findViewById(R.id.Layout_For_OrderId);
         setRecyclerView();
         toolbar=findViewById(R.id.Toolbar_CustomerSupportWithOrderId);
         toolbar.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onBackPressed();
             }
         });
    }

    private void setRecyclerView()
    {
        searchView=findViewById(R.id.searchview_in_support_orderid);
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String query) {
             searchInFireBase(query);
             return true;
         }

         @Override
         public boolean onQueryTextChange(String newText) {
             return false;
         }
     });
        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), A_CompleteViewOfPurchasedProduct.class);
                intent.putExtra("DateOfPurchase",d_pastOrders.DateOfPurchase_DeliveryStatus);
                intent.putExtra("CustomerName",d_pastOrders.CustomerName);
                intent.putExtra("CustomerEmail",d_pastOrders.CustomerEmail);
                intent.putExtra("CustomerPhone",d_pastOrders.CustomerMobileNumber);
                intent.putExtra("CustomerAddress",d_pastOrders.CustomerAddress);
                intent.putExtra("ProductImage",d_pastOrders.ProductImage);
                intent.putExtra("ProductOrderId",d_pastOrders.OrderId);
                intent.putExtra("ProductCategory",d_pastOrders.ProductCategoryByMaterial);
                intent.putExtra("ProductCategoryGender",d_pastOrders.ProductCategoryByGender);
                intent.putExtra("ProductName",d_pastOrders.ProductName);
                intent.putExtra("ProductQuantity",d_pastOrders.NoOfOrdersPurchased);
                intent.putExtra("ProductSize",d_pastOrders.ProductSize);
                startActivity(intent);
            }
        });

    }

    private void searchInFireBase(String nec) {
        if (!TextUtils.isEmpty(nec))
        {
            GetDataFirebase(nec);
        }
        else
            Toast.makeText(this, "Query Cannot be empty", Toast.LENGTH_SHORT).show();

    }



   private void GetDataFirebase(String nec)
   {
       Query queryforgettingorderid=FirebaseDatabase.getInstance().getReference("Users").child("UsersNormalShoeOrders");
               queryforgettingorderid.orderByChild("OrderId").equalTo(nec).addListenerForSingleValueEvent(eventListenerforFetching);
   }
    ValueEventListener eventListenerforFetching=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists())
            {
                String CustomerName = dataSnapshot.child("CustomerName").getValue(String.class);
                String CustomerMobileNumber = dataSnapshot.child("CustomerMobileNumber").getValue(String.class);
                String CustomerEmail = dataSnapshot.child("CustomerEmail").getValue(String.class);
                String ProductName = dataSnapshot.child("ProductName").getValue(String.class);
                String ProductDescription = dataSnapshot.child("ProductDescription").getValue(String.class);
                String ProductImage = dataSnapshot.child("ProductImage").getValue(String.class);
                String NoOfOrdersPurchased = dataSnapshot.child("NoOfOrdersPurchased").getValue(String.class);
                String ProductSize = dataSnapshot.child("ProductSize").getValue(String.class);
                String ProductCategoryByGender = dataSnapshot.child("ProductCategoryByGender").getValue(String.class);
                String ProductCategoryByMaterial = dataSnapshot.child("ProductCategoryByMaterial").getValue(String.class);
                String AmountPaid = dataSnapshot.child("AmountPaid").getValue(String.class);
                String CustomerAddress = dataSnapshot.child("CustomerAddress").getValue(String.class);
                String OrderId = dataSnapshot.child("OrderId").getValue(String.class);
                String DateOfPurchase_DeliveryStatus = dataSnapshot.child("DateOfPurchase_DeliveryStatus").getValue(String.class);
                Log.e("A_HelpCustomerWithOrder","Customer Name: "+CustomerName);
                Log.e("A_HelpCustomerWithOrder",""+dataSnapshot.getValue().getClass().getSimpleName());
                d_pastOrders= new D_PastOrders(CustomerName, CustomerMobileNumber, CustomerEmail, ProductName, ProductDescription, ProductImage, NoOfOrdersPurchased, ProductSize, ProductCategoryByGender, ProductCategoryByMaterial, AmountPaid, CustomerAddress, OrderId, DateOfPurchase_DeliveryStatus);
                setDataIntoViews();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("A_HelpCustomerWithOrder","Error:"+databaseError.getMessage());

        }
    };

    private void setDataIntoViews()
    {
     linearlayout.setVisibility(View.VISIBLE);
     imageView=findViewById(R.id.ProductImage_Support_CustomerId);
     productname=findViewById(R.id.ProductTitle_Support_CustomerId);
     productprice=findViewById(R.id.ProductPrice_Support_CustomerId);

     Glide.with(getApplicationContext()).load(d_pastOrders.ProductImage).into(imageView);
     productname.setText(d_pastOrders.ProductName);
     productprice.setText(d_pastOrders.AmountPaid);
    }
}
