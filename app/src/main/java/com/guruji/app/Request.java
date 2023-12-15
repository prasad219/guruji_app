package com.guruji.app;

class Request {
    public String email;
    public String name;
    public String total;
    public String prodName;
    public String date;
    public String time;
    public String address;
    public String phoneNo;
    public String status;

    public Request(){

    }

    public Request(String email, String name, String total, String prodName, String date, String time, String address, String phoneNo, String status) {
        this.email = email;
        this.name = name;
        this.total = total;
        this.prodName = prodName;
        this.date = date;
        this.time = time;
        this.address = address;
        this.phoneNo = phoneNo;
        this.status = status="0";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
