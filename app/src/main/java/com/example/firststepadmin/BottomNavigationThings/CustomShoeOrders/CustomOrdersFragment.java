package com.example.firststepadmin.BottomNavigationThings.CustomShoeOrders;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firststepadmin.BottomNavigationThings.Inventory.A_CompleteViewOfPurchasedProduct;
import com.example.firststepadmin.BottomNavigationThings.Profile.CompleteViewOfProduct;
import com.example.firststepadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class CustomOrdersFragment extends Fragment {
    interface OnCardViewItemClickListener
    {
        void onItemClickListenerOfCardView(int position);
    }

    public CustomOrdersFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MyAdapterCustom myAdapter;
    ArrayList<D_CustomShoesData> arrayListCustomOrders=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_custom_orders, container, false);
        setRecyclerView(view);
        setSwipeRefreshLayout(view);
        getDataFormFireBase();
        return view;
     }

    public void setSwipeRefreshLayout(View view)
    {
        swipeRefreshLayout=view.findViewById(R.id.swipe_custom);
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
            Collections.shuffle(arrayListCustomOrders);
            myAdapter.notifyDataSetChanged();
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };
    private void setRecyclerView(View view) {
        recyclerView=view.findViewById(R.id.RecyclerView_CustomOrders);
        myAdapter = new MyAdapterCustom(arrayListCustomOrders);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnCardViewItemClickListener(new OnCardViewItemClickListener() {
            @Override
            public void onItemClickListenerOfCardView(int position) {
                Intent intent=new Intent(getContext(), A_CompleteViewOfPurchasedProduct.class);
                intent.putExtra("ProductImage",R.drawable.cusshoe);
                intent.putExtra("DateOfPurchase",arrayListCustomOrders.get(position).DateOfPurchase_DeliveryStatus);
                intent.putExtra("ProductName",arrayListCustomOrders.get(position).productTitle);
                intent.putExtra("ProductPrice",arrayListCustomOrders.get(position).productPrice);
                intent.putExtra("ProductCategory","Carbon");
                intent.putExtra("ProductCategoryByGender","Men/Women");
                intent.putExtra("ProductQuantity",arrayListCustomOrders.get(position).Quantity);
                intent.putExtra("CustomerName",arrayListCustomOrders.get(position).CustomerName);
                intent.putExtra("CustomerPhone",arrayListCustomOrders.get(position).CustomerMobileNumber);
                intent.putExtra("CustomerEmail",arrayListCustomOrders.get(position).CustomerEmail);
                intent.putExtra("CustomerAddress",arrayListCustomOrders.get(position).CustomerAddress);
                intent.putExtra("ProductSize",arrayListCustomOrders.get(position).Size);
                intent.putExtra("ProductOrderId",arrayListCustomOrders.get(position).OrderId);
                startActivity(intent);
            }
        });
    }



    class MyAdapterCustom extends RecyclerView.Adapter<MyAdapterCustom.ViewHolderClass>
    {
        ArrayList<D_CustomShoesData> arrayList;
        OnCardViewItemClickListener onCardViewItemClickListener;
        public MyAdapterCustom(ArrayList<D_CustomShoesData> arrayList)
        {
           this.arrayList=arrayList;
        }
        public void setOnCardViewItemClickListener(OnCardViewItemClickListener onCardViewItemClickListener1)
        {
            this.onCardViewItemClickListener=onCardViewItemClickListener1;
        }
        @NonNull
        @Override
        public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolderClass(LayoutInflater.from(getContext()).inflate(R.layout.single_view_for_label_of_shoe,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
           holder.CustomerName.setText(arrayList.get(position).CustomerName);
           holder.ProductImage.setImageResource(R.drawable.cusshoe);
           holder.NoOfOrders.setText("NoOfOrders:"+arrayList.get(position).Quantity);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        private class ViewHolderClass extends RecyclerView.ViewHolder
       {
          TextView CustomerName,NoOfOrders;
          ImageView ProductImage;
           public ViewHolderClass(@NonNull View itemView)
           {
                super(itemView);
             CustomerName=itemView.findViewById(R.id.SingleViewForLabelOfShoe_Name);
             NoOfOrders=itemView.findViewById(R.id.SingleViewForLabelOfShoe_Price);
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
    private void getDataFormFireBase()
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child("UsersCustomShoeOrders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        String productTitle=dataSnapshot1.child("ProductTitle").getValue(String.class);
                        String productDescription=dataSnapshot1.child("productDescription").getValue(String.class);
                        String productPrice=dataSnapshot1.child("productPrice").getValue(String.class);
                        String imageLocation=dataSnapshot1.child("imageLocation").getValue(String.class);
                        String Quantity=dataSnapshot1.child("Quantity").getValue(String.class);
                        String Size=dataSnapshot1.child("Size").getValue(String.class);
                        String OrderId=dataSnapshot1.child("OrderId").getValue(String.class);
                        String DateOfPurchase_DeliveryStatus=dataSnapshot1.child("DateOfPurchase_DeliveryStatus").getValue(String.class);
                        String CustomerName=dataSnapshot1.child("ProductTitle").getValue(String.class);
                        String CustomerMobileNumber=dataSnapshot1.child("CustomerMobileNumber").getValue(String.class);
                        String CustomerEmail=dataSnapshot1.child("CustomerEmail").getValue(String.class);
                        String CustomerAddress=dataSnapshot1.child("CustomerAddress").getValue(String.class);
                        arrayListCustomOrders.add(new D_CustomShoesData(productTitle,productDescription,productPrice,imageLocation,Quantity,Size,OrderId,DateOfPurchase_DeliveryStatus,CustomerName,CustomerMobileNumber,CustomerEmail,CustomerAddress));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myAdapter.notifyDataSetChanged();
    }
}
