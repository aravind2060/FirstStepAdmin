package com.example.firststepadmin.BottomNavigationThings.Inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.example.firststepadmin.R;

public class A_CompleteViewOfPurchasedProduct extends AppCompatActivity {


    TextView CustomerName,CustomerPhone,CustomerAddress,CustomerEmail,ProductName,ProductMaterial,ProductOrderId;
    ImageView ProductImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__complete_view_of_purchased_product);
        findViewByIds();
        getDataFromIntent();

    }

    private void findViewByIds() {
        Toolbar toolbar=findViewById(R.id.Toolbar_CompleteViewOfPurchasedProduct);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        CustomerName=findViewById(R.id.CustomerName_CompleteViewOfPurchasedOrder);
        CustomerPhone=findViewById(R.id.CustomerPhone_CompleteViewOfPurchasedOrder);
        CustomerAddress=findViewById(R.id.CustomerAddress_CompleteViewOfPurchasedOrder);
        CustomerEmail=findViewById(R.id.CustomerEmail_CompleteViewOfPurchasedOrder);
        ProductName=findViewById(R.id.ProductName_CompleteViewOfPurchasedProduct);
        ProductMaterial=findViewById(R.id.ProductCategoryByGender_CompleteViewOfPurchasedProduct);
        ProductOrderId=findViewById(R.id.ProductOrderId_CompleteViewOfPurchasedProduct);
        ProductImage=findViewById(R.id.Image_CompleteViewOfPurchasedProduct);
    }

    private void getDataFromIntent()
    {
        Intent intent=getIntent();
        String date=intent.getStringExtra("DateOfPurchase");
        String  customername=intent.getStringExtra("CustomerName");
        String customerphone=intent.getStringExtra("CustomerPhone");
        String customeremail=intent.getStringExtra("CustomerEmail");
        String customeraddress=intent.getStringExtra("CustomerAddress");
        String image=intent.getStringExtra("ProductImage");
        String orderid=intent.getStringExtra("ProductOrderId");
        String productmaterial=intent.getStringExtra("ProductCategory");
        String productgender=intent.getStringExtra("ProductCategoryGender");
        String productname=intent.getStringExtra("ProductName");
        String productquantity=intent.getStringExtra("ProductQuantity");
        String productSize=intent.getStringExtra("ProductSize");


        CustomerName.setText(customername);
        CustomerEmail.setText(customeremail);
        CustomerPhone.setText(customerphone);
        CustomerAddress.setText(customeraddress);
        Glide.with(getApplicationContext()).load(image).into(ProductImage);
        ProductOrderId.setText(orderid+" - "+orderid+"  DateOfPurchase:"+date);
        ProductName.setText(productname+" Size:-"+productSize+"  Quantity:-"+productquantity);
        ProductMaterial.setText(productgender+" Category-"+productmaterial);
    }


}
