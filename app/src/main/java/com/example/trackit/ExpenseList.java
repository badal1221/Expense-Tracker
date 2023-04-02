package com.example.trackit;

public class ExpenseList {
    String date,time,mttype,mttype1;
    int amount,cbalance;

//    public ExpenseList(String mttype, String mttype1,String date, String time, int amount) {
//        this.date = date;
//        this.time = time;
//        this.mttype = mttype;
//        this.mttype1 = mttype1;
//        this.amount = amount;
//    }

    public ExpenseList(String mttype, String mttype1, String date, String time, int amount, int cbalance) {
        this.date = date;
        this.time = time;
        this.mttype = mttype;
        this.mttype1 = mttype1;
        this.amount = amount;
        this.cbalance = cbalance;
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

    public String getMttype() {
        return mttype;
    }

    public void setMttype(String mttype) {
        this.mttype = mttype;
    }

    public String getMttype1() {
        return mttype1;
    }

    public void setMttype1(String mttype1) {
        this.mttype1 = mttype1;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCbalance() {
        return cbalance;
    }

    public void setCbalance(int cbalance) {
        this.cbalance = cbalance;
    }
}
