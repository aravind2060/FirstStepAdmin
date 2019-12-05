package com.example.firststepadmin.BottomNavigationThings.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firststepadmin.BottomNavigationThings.UploadNewProducts.D_MetadataOfUploadingProducts;
import com.example.firststepadmin.BottomNavigationThings.UploadNewProducts.D_ShoesDataFromInternet;
import com.example.firststepadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class A_PastUploadedProducts extends AppCompatActivity {

    interface OnCardViewItemClickListener
    {
        void onItemClickListenerOfCardView(int position);
    }
    Toolbar toolbar;
    TextView noOfpastOrders;
    RecyclerView recyclerView;
    ArrayList<D_ShoesDataFromInternet> arrayListForPastOrders=new ArrayList<>();
    ArrayList<D_MetadataOfUploadingProducts>arraylistdMetadataOfUploadingProducts=new ArrayList<>();
    MyAdapterForPastOrders myAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    DatabaseReference databaseReferencefordata= FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReferenceforkey=FirebaseDatabase.getInstance().getReference("Admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PastUploadedOrders");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__past_uploaded_products);
        setRecyclerView();
        getSeriesOfMyOrdersData();
        getNoOfAddressOfPastOrders();
        findViewByIds();
        setSwipeRefreshLayout();
    }

    public void setSwipeRefreshLayout()
    {
        swipeRefreshLayout=findViewById(R.id.swipe_myOrders);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        mHandler.sendEmptyMessage(0);
                    }
                }, 1000);
            }
        });
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            Collections.shuffle(arrayListForPastOrders);
            myAdapter.notifyDataSetChanged();
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };
    private void setRecyclerView() {
        recyclerView=findViewById(R.id.MyOrders_RecyclerView);
        myAdapter = new MyAdapterForPastOrders(arrayListForPastOrders);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnCardViewItemClickListener(new OnCardViewItemClickListener() {
            @Override
            public void onItemClickListenerOfCardView(int position) {
                Intent intent=new Intent(getApplicationContext(), CompleteViewOfProduct.class);
                intent.putExtra("ImageLocation",arrayListForPastOrders.get(position).ImageLocation);
                intent.putExtra("ProductTitle",arrayListForPastOrders.get(position).ProductTitleOfShoe);
                intent.putExtra("ProductPrice",arrayListForPastOrders.get(position).ProductPriceOfShoe);
                intent.putExtra("ProductDescription",arrayListForPastOrders.get(position).ProductDescriptionOfShoe);
                intent.putExtra("ProductCategoryByMaterial",arraylistdMetadataOfUploadingProducts.get(position).ProductCategoryByMaterial);
                intent.putExtra("ProductCategoryByGender",arraylistdMetadataOfUploadingProducts.get(position).ProductCategoryByGender);
                intent.putExtra("ProductUploadedDate",arraylistdMetadataOfUploadingProducts.get(position).UploadedDate);
                startActivity(intent);
            }
        });
    }

    private void findViewByIds() {
        toolbar = findViewById(R.id.Toolbar_MyOrders);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        noOfpastOrders = findViewById(R.id.NumberOfPastOrders);
    }
    private void getNoOfAddressOfPastOrders()
    {
        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PastUploadedOrders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    noOfpastOrders.setText(dataSnapshot.getChildrenCount()+" Past Orders");
                }
                else
                {
                    noOfpastOrders.setText("0 "+"Past Orders");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    class MyAdapterForPastOrders extends RecyclerView.Adapter<MyAdapterForPastOrders.ViewHolderClass>
    {
        ArrayList<D_ShoesDataFromInternet> arrayList;
        OnCardViewItemClickListener onCardViewItemClickListener;
        MyAdapterForPastOrders(ArrayList<D_ShoesDataFromInternet> arrayList1)
        {
            this.arrayList=arrayList1;
        }
        public void setOnCardViewItemClickListener(OnCardViewItemClickListener onCardViewItemClickListener1)
        {
            this.onCardViewItemClickListener=onCardViewItemClickListener1;
        }
        @NonNull
        @Override
        public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolderClass(LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_view_for_label_of_shoe,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
            holder.Name.setText(arrayList.get(position).ProductTitleOfShoe);
            holder.Price.setText(arrayList.get(position).ProductPriceOfShoe);
            Glide.with(getApplicationContext()).load(arrayList.get(position).ImageLocation).into(holder.ProductImage);

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        private class ViewHolderClass extends RecyclerView.ViewHolder
        {
            TextView Name,Price;
            ImageView ProductImage;
            public ViewHolderClass(@NonNull View itemView) {
                super(itemView);
                Name=itemView.findViewById(R.id.SingleViewForLabelOfShoe_Name);
                Price=itemView.findViewById(R.id.SingleViewForLabelOfShoe_Price);
                ProductImage=itemView.findViewById(R.id.SingleViewForLabelOfShoe_ImageView);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCardViewItemClickListener!=null)
                        {
                            int position=getAdapterPosition();
                            if (position!=RecyclerView.NO_POSITION)
                            {
                                onCardViewItemClickListener.onItemClickListenerOfCardView(position);
                            }
                        }
                    }
                });

            }
        }
    }


    private void getSeriesOfMyOrdersData() {
      databaseReferenceforkey.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              if (dataSnapshot.exists())
              {
                 for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                 {
                   String pl=dataSnapshot1.child("ProductLink").getValue(String.class);
                     Log.e("A_PastUploadedProducts","ProductLink:"+pl);
                     String pgender=dataSnapshot1.child("ProductCategoryByGender").getValue(String.class);
                     String pmaterial=dataSnapshot1.child("ProductCategoryByMaterial").getValue(String.class);
                     String d=dataSnapshot1.child("UploadedDate").getValue(String.class);
                     arraylistdMetadataOfUploadingProducts.add(new D_MetadataOfUploadingProducts(null,pgender,pmaterial,d));
                     databaseReferencefordata.child(pgender).child(pmaterial).child(pl).addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if (dataSnapshot.exists())
                           {
                               String productDescriptionOfShoe=dataSnapshot.child("ProductDescriptionOfShoe").getValue(String.class);
                               String ProductPrice=dataSnapshot.child("ProductPriceOfShoe").getValue(String.class);
                               String productTitle=dataSnapshot.child("ProductTitleOfShoe").getValue(String.class);
                               String imageLocation=dataSnapshot.child("ImageLocation").getValue(String.class);
                               D_ShoesDataFromInternet dShoesDataFromInternet=new D_ShoesDataFromInternet(productTitle,ProductPrice,productDescriptionOfShoe,imageLocation);
                               arrayListForPastOrders.add(dShoesDataFromInternet);
                           }
                           else
                           {
                               Log.e("A_PastUploadedProducts","Does not found exact datasnapshot in users");
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
                 }
              }
              else
              {
                  Log.e("A_PastUploadedProducts","Datasnapshot of key does not exist");
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
      myAdapter.notifyDataSetChanged();
    }
}
