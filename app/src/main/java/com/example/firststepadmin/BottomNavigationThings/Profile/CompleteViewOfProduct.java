package com.example.firststepadmin.BottomNavigationThings.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firststepadmin.R;

import org.w3c.dom.Text;

public class CompleteViewOfProduct extends AppCompatActivity {

    TextView ProductName,ProductPrice,ProductDescription,ProductCategoryByGender,ProductCategoryByMaterial,DateOfUpload;
    ImageView ProductImage;
    Toolbar toolbar;
   String D_ProductCategoryByGender,D_ProductCategoryByMaterial,D_ProductTitle,D_Price,D_Image,D_ProductDescription,D_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_view_of_product);
        findviewByIds();
        gettingDataFromIntent();
        settingDataIntoViews();
    }

    private void findviewByIds() {
    ProductImage=findViewById(R.id.ProductImage_CompleteViewOfProduct);
    ProductName=findViewById(R.id.ProductName_CompleteViewOfProduct);
    ProductPrice=findViewById(R.id.ProductCategoryByPrice_CompleteViewProduct);
    ProductDescription=findViewById(R.id.ProductDescription_CompleteViewOfProduct);
    ProductCategoryByGender=findViewById(R.id.ProductCategoryByGender_CompleteViewOfProduct);
    ProductCategoryByMaterial=findViewById(R.id.ProductCategoryByMaterial_CompleteViewProduct);
    DateOfUpload=findViewById(R.id.Upladed_Date_PastOrder);
    toolbar=findViewById(R.id.Toolbar_complete_view_Of_Product);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });
    }

    private void gettingDataFromIntent()
    {
        Intent intent=getIntent();
        D_ProductCategoryByGender=intent.getStringExtra("ProductCategoryByGender");
        D_ProductCategoryByMaterial=intent.getStringExtra("ProductCategoryByMaterial");
        D_ProductTitle=intent.getStringExtra("ProductTitle");
        D_Price=intent.getStringExtra("ProductPrice");
        D_Image=intent.getStringExtra("ImageLocation");
        D_ProductDescription=intent.getStringExtra("ProductDescription");
        D_Date=intent.getStringExtra("ProductUploadedDate");
    }
    private void settingDataIntoViews() {
        Glide.with(getApplicationContext()).load(D_Image).into(ProductImage);
        ProductName.setText(D_ProductTitle);
        ProductPrice.setText(D_Price);
        ProductDescription.setText(D_ProductDescription);
        ProductCategoryByGender.setText(D_ProductCategoryByGender);
        ProductCategoryByMaterial.setText(D_ProductCategoryByMaterial);
        DateOfUpload.setText(D_Date);
    }
}
