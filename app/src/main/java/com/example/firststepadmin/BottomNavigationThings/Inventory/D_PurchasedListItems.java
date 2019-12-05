package com.example.firststepadmin.BottomNavigationThings.Inventory;

public class D_PurchasedListItems {

     String CustomerName,CustomerMobileNumber,CustomerEmail,ProductName,ProductDescription,ProductImage,NoOfOrdersPurchased,ProductSize,ProductCategoryByGender,ProductCategoryByMaterial,AmountPaid, CustomerAddress,OrderId, DateOfPurchaseDeliveryStatus;

    public D_PurchasedListItems(String customerName, String customerMobileNumber, String customerEmail, String productName, String productDescription, String productImage, String noOfOrdersPurchased, String productSize, String productCategoryByGender, String productCategoryByMaterial, String amountPaid, String address, String orderId, String dateOfPurchase_DeliveryStatus) {
        CustomerName = customerName;
        CustomerMobileNumber = customerMobileNumber;
        CustomerEmail = customerEmail;
        ProductName = productName;
        ProductDescription = productDescription;
        ProductImage = productImage;
        NoOfOrdersPurchased = noOfOrdersPurchased;
        ProductSize = productSize;
        ProductCategoryByGender = productCategoryByGender;
        ProductCategoryByMaterial = productCategoryByMaterial;
        AmountPaid = amountPaid;
        CustomerAddress = address;
        OrderId = orderId;
        DateOfPurchaseDeliveryStatus = dateOfPurchase_DeliveryStatus;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getCustomerMobileNumber() {
        return CustomerMobileNumber;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public String getNoOfOrdersPurchased() {
        return NoOfOrdersPurchased;
    }

    public String getProductSize() {
        return ProductSize;
    }

    public String getProductCategoryByGender() {
        return ProductCategoryByGender;
    }

    public String getProductCategoryByMaterial() {
        return ProductCategoryByMaterial;
    }

    public String getAmountPaid() {
        return AmountPaid;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getDeliveryStatus() {
        return DateOfPurchaseDeliveryStatus;
    }
}
