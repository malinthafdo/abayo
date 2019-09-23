package com.malintha.card_payment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME ="PaymentDatabase";
    private static final int DATABASE_VERSION =1;
    private static final String TABLE_NAME ="payment";
    private static final String COLUMN_ID ="id";
    private static final String COLUMN_cardholder ="cardholder";
    private static final String COLUMN_cardno ="cardno";
    private static final String COLUMN_cardtype ="cardtype";
    private static final String COLUMN_phoneno ="phoneno";
    private static final String COLUMN_cardexpdate ="cardexpdate";
    private static final String COLUMN_cardccv="cardccv";



    public DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + "( \n" +
                "    "+COLUMN_ID+"  INTEGER NOT NULL CONSTRAINT payment_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    "+COLUMN_cardholder+" varchar(200) NOT NULL,\n" +
                "    "+COLUMN_cardno+" INTEGER NOT NULL,\n" +
                "    "+COLUMN_cardtype+" varchar(200) NOT NULL,\n" +
                "    "+COLUMN_phoneno+" INTEGER NOT NULL,\n" +
                "    "+COLUMN_cardexpdate+" INTEGER NOT NULL,\n" +
                "    "+COLUMN_cardccv+" INTEGER NOT NULL\n" +
                ");";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    boolean addPaymentCard(String cardholder, int cardno, String cardtype, int phoneno, String cardexpdate, int cardccv){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv =new ContentValues();
        cv.put(COLUMN_cardholder, cardholder);
        cv.put(COLUMN_cardno, String.valueOf(cardno));
        cv.put(COLUMN_cardtype, cardtype);
        cv.put(COLUMN_phoneno, String.valueOf(phoneno));
        cv.put(COLUMN_cardexpdate,cardexpdate);
        cv.put(COLUMN_cardccv, String.valueOf(cardccv));


        return sqLiteDatabase.insert(TABLE_NAME, null, cv) != -1;
    }

    Cursor allPaymentCardDatabase(){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM payment " + TABLE_NAME,null);

    }

    boolean updateCardDetails(int id, String cardholder, int cardno, String cardtype, int phoneno, String cardexpdate, int cardccv){

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues cv =new ContentValues();
        cv.put(COLUMN_cardholder, cardholder);
        cv.put(COLUMN_cardno, String.valueOf(cardno));
        cv.put(COLUMN_cardtype, cardtype);
        cv.put(COLUMN_phoneno, String.valueOf(phoneno));
        cv.put(COLUMN_cardexpdate,cardexpdate);
        cv.put(COLUMN_cardccv, String.valueOf(cardccv));

       return sqLiteDatabase.update(TABLE_NAME, cv,COLUMN_ID+"=?", new String[] {String.valueOf(id)}) > 0;
    }

    boolean deleteCard (int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?",new String[] {String.valueOf(id)}) > 0;

    }
}
