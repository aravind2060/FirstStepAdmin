package com.example.firststepadmin.BottomNavigationThings.Profile;

public class D_PastOrders {
    public String CustomerName,CustomerMobileNumber,CustomerEmail,CustomerAddress,ProductName,ProductDescription,ProductImage,NoOfOrdersPurchased,ProductSize,ProductCategoryByGender,ProductCategoryByMaterial,AmountPaid,OrderId,DateOfPurchase_DeliveryStatus;

    public D_PastOrders(String customerName, String customerMobileNumber, String customerEmail, String customerAddress, String productName, String productDescription, String productImage, String noOfOrdersPurchased, String productSize, String productCategoryByGender, String productCategoryByMaterial, String amountPaid, String orderId, String dateOfPurchase_DeliveryStatus) {
        CustomerName = customerName;
        CustomerMobileNumber = customerMobileNumber;
        CustomerEmail = customerEmail;
        CustomerAddress = customerAddress;
        ProductName = productName;
        ProductDescription = productDescription;
        ProductImage = productImage;
        NoOfOrdersPurchased = noOfOrdersPurchased;
        ProductSize = productSize;
        ProductCategoryByGender = productCategoryByGender;
        ProductCategoryByMaterial = productCategoryByMaterial;
        AmountPaid = amountPaid;
        OrderId = orderId;
        DateOfPurchase_DeliveryStatus = dateOfPurchase_DeliveryStatus;
    }

    public D_PastOrders() {
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerMobileNumber() {
        return CustomerMobileNumber;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        CustomerMobileNumber = customerMobileNumber;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getNoOfOrdersPurchased() {
        return NoOfOrdersPurchased;
    }

    public void setNoOfOrdersPurchased(String noOfOrdersPurchased) {
        NoOfOrdersPurchased = noOfOrdersPurchased;
    }

    public String getProductSize() {
        return ProductSize;
    }

    public void setProductSize(String productSize) {
        ProductSize = productSize;
    }

    public String getProductCategoryByGender() {
        return ProductCategoryByGender;
    }

    public void setProductCategoryByGender(String productCategoryByGender) {
        ProductCategoryByGender = productCategoryByGender;
    }

    public String getProductCategoryByMaterial() {
        return ProductCategoryByMaterial;
    }

    public void setProductCategoryByMaterial(String productCategoryByMaterial) {
        ProductCategoryByMaterial = productCategoryByMaterial;
    }

    public String getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        AmountPaid = amountPaid;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getDateOfPurchase_DeliveryStatus() {
        return DateOfPurchase_DeliveryStatus;
    }

    public void setDateOfPurchase_DeliveryStatus(String dateOfPurchase_DeliveryStatus) {
        DateOfPurchase_DeliveryStatus = dateOfPurchase_DeliveryStatus;
    }
}
