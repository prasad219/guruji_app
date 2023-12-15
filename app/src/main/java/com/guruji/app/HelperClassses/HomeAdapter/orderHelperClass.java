
package com.guruji.app.HelperClassses.HomeAdapter;

public class orderHelperClass {


   String orderid, orderstatus, ordername, orderphone, orderaddress, orderEmail;

   public orderHelperClass(String orderid, String orderstatus, String ordername, String orderphone, String orderaddress, String orderEmail) {
      this.orderid = orderid;
      this.orderstatus = orderstatus;
      this.ordername = ordername;
      this.orderphone = orderphone;
      this.orderaddress = orderaddress;
      this.orderEmail = orderEmail;
   }

   public String getOrderid() {
      return orderid;
   }

   public String getOrderstatus() {
      return orderstatus;
   }

   public String getOrdername() {
      return ordername;
   }

   public String getOrderphone() {
      return orderphone;
   }

   public String getOrderaddress() {
      return orderaddress;
   }

   public String getOrderEmail() { return orderEmail; }
}

