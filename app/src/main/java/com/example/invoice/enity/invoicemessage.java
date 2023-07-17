package com.example.invoice.enity;


import java.io.Serializable;

public class invoicemessage implements Serializable {
    public String code;
    public int state;
    public String number;
    public String time;
    public String category;
    public Float price;
    public invoicemessage(){
    }
    public invoicemessage(String code,String number,String time,String category,Float price,int state){
        this.code=code;
        this.number=number;
        this.time=time;
        this.category=category;
        this.price=price;
        this.state=state;
    }
    public String getCode(){
        return this.code;
    }
    public String getNumber(){
        return this.number;
    }
    public String getTime(){
        String dateString=this.time;
        String year = dateString.substring(0, 4);
        String month = dateString.substring(4, 6);
        String day = dateString.substring(6, 8);
        String time=year+"年"+month+"月"+day+"日";
        return time;
    }
    public String getPrice(){
        return this.price.toString();
    }
    public String getType(){
        return this.category;
    }
    @Override
    public String toString() {
        return "invoicemessage{" +
                "code='" + code + '\'' +
                ", number='" + number + '\'' +
                ", time='" + time + '\'' +
                ", category='" + category + '\'' +
                ", price='" + price + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}