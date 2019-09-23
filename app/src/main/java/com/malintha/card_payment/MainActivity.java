package com.malintha.card_payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    DataBaseManager mDatabase;

    EditText editTextCardHolder,editTextCardNo,editTextPhoneNo,editTextCardExpDate,editTextCardCVV;
    Spinner spinnerCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase= new DataBaseManager(this);


        editTextCardHolder = (EditText)findViewById(R.id.editTextCardHolder);
        editTextCardNo = (EditText)findViewById(R.id.editTextCardNo);
        editTextPhoneNo =(EditText)findViewById(R.id.editTextPhoneNo);
        editTextCardExpDate =(EditText)findViewById(R.id.editTextCardExpDate);
        editTextCardCVV = (EditText)findViewById(R.id.editTextCardCVV);
        spinnerCard =(Spinner)findViewById(R.id.spinnerCard);

        findViewById(R.id.buttonAddCard).setOnClickListener(this);
        findViewById(R.id.textViewViewCard).setOnClickListener(this);

    }



    public void addPaymentCard(){

        String cardholder = editTextCardHolder.getText().toString().trim();
        String cardno = editTextCardNo.getText().toString().trim();
        String cardtype = spinnerCard.getSelectedItem().toString();
        String phoneno = editTextPhoneNo.getText().toString().trim();
        String cardexpdate = editTextCardExpDate.getText().toString().trim();
        String cardccv = editTextCardCVV.getText().toString().trim();

        if( cardholder.isEmpty())
        {
            editTextCardHolder.setError("name cant be empty");
            editTextCardHolder.requestFocus();
            return;
        }
        if( cardno.isEmpty())
        {
            editTextCardNo.setError("name cant be empty");
            editTextCardNo.requestFocus();
            return;
        }
        if( phoneno.isEmpty())
        {
            editTextPhoneNo.setError("name cant be empty");
            editTextPhoneNo.requestFocus();
            return;
        }
        if( cardexpdate.isEmpty())
        {
            editTextCardExpDate.setError("name cant be empty");
            editTextCardExpDate.requestFocus();
            return;
        }
        if( cardccv.isEmpty())
        {
            editTextCardCVV.setError("name cant be empty");
            editTextCardCVV.requestFocus();
            return;
        }

        if(mDatabase.addPaymentCard(cardholder, Integer.parseInt(cardno), cardtype, Integer.parseInt(phoneno), cardexpdate, Integer.parseInt(cardccv)))
        Toast.makeText(this,"Card added",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Card not added",Toast.LENGTH_SHORT).show();

    }

  


    @Override
    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.buttonAddCard:
                addPaymentCard();
                break;

            case R.id.textViewViewCard:
                    startActivity(new Intent(this,CardActivity.class));
                break;
        }

    }
}
