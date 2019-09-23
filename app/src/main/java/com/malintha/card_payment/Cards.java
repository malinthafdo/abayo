package com.malintha.card_payment;

public class Cards {

    int id;
    String cardHolder;
    int cardNo;
    String cardT;
    int phoneNo;
    String expDate;
    int cvv;


    public Cards(int id, String cardHolder, int cardNo, String cardT, int phoneNo, String expDate, int cvv) {
        this.id = id;
        this.cardHolder = cardHolder;
        this.cardNo = cardNo;
        this.cardT = cardT;
        this.phoneNo = phoneNo;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    public  int getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public int getCardNo() {
        return cardNo;
    }

    public String getCardT() {
        return cardT;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public int getCvv() {
        return cvv;
    }
}
