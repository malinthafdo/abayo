package com.malintha.card_payment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class CardAdapter extends ArrayAdapter<Cards>
{

    Context mctx ;
    int layoutRes;
    List<Cards> cardsList;
    DataBaseManager mDatabase;

    public CardAdapter( Context mctx, int layoutRes,List<Cards> cardsList,DataBaseManager mDatabase ) {
        super(mctx, layoutRes ,cardsList);
        this.mctx = mctx;
        this.layoutRes=layoutRes;
        this.cardsList=cardsList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mctx);

        View view = inflater.inflate(layoutRes,null);

        TextView textViewCardName = view.findViewById(R.id.textViewCardName);
        TextView textViewCardNumber = view.findViewById(R.id.textViewCardNumber);
        TextView textViewCardType = view.findViewById(R.id.textViewCardType);
        TextView textViewPhoneNumber = view.findViewById(R.id.textViewPhoneNumber);
        TextView textViewExpDate = view.findViewById(R.id.textViewExpDate);
        TextView textViewCardCcv = view.findViewById(R.id.textViewCardCcv);

        final Cards cards = cardsList.get(position);

        textViewCardName.setText(cards.getCardHolder());
        textViewCardNumber.setText(String.valueOf(cards.getCardNo()));
        textViewCardType.setText(cards.getCardT());
        textViewPhoneNumber.setText(String.valueOf(cards.getPhoneNo()));
        textViewExpDate.setText(cards.getExpDate());
        textViewCardCcv.setText(String.valueOf(cards.getCvv()));

        view.findViewById(R.id.buttonDeleteCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteCard(cards);
            }
        });

        view.findViewById(R.id.buttonEditCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCardDetails(cards);
            }
        });

    return view;
    }

    private void updateCardDetails(final Cards cards)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mctx);
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.dialog_update_card,null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextCardHolder = view.findViewById(R.id.editTextCardHolder);
        final EditText editTextCardNo = view.findViewById(R.id.editTextCardNo);
        final Spinner spinner =view.findViewById(R.id.spinnerCard);
        final EditText editTextPhoneNo = view.findViewById(R.id.editTextPhoneNo);
        final EditText editTextCardExpDate = view.findViewById(R.id.editTextCardExpDate);
        final EditText editTextCardCVV = view.findViewById(R.id.editTextCardCVV);

        editTextCardHolder.setText(cards.getCardHolder());
        editTextCardNo.setText(String.valueOf(cards.getCardNo()));
        editTextPhoneNo.setText(String.valueOf(cards.getPhoneNo()));
        editTextCardExpDate.setText(cards.getExpDate());
        editTextCardCVV.setText(String.valueOf(cards.getCvv()));

        view.findViewById(R.id.buttonEditCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cardholder = editTextCardHolder.getText().toString().trim();
                String cardno = editTextCardNo.getText().toString().trim();
                String cardtype = spinner.getSelectedItem().toString().trim();
                String cardphoneno= editTextPhoneNo.getText().toString().trim();
                String cardexpdate = editTextCardExpDate.getText().toString().trim();
                String cardccv = editTextCardCVV.getText().toString().trim();



                if( cardholder.isEmpty())
                {
                    editTextCardHolder.setError("name cant be empty");
                    editTextCardHolder.requestFocus();
                    return;
                }
                if(cardno.isEmpty())
                {
                    editTextCardNo.setError("salary cant be empty");
                    editTextCardNo.requestFocus();
                    return;
                }
                if(cardphoneno.isEmpty())
                {
                    editTextPhoneNo.setError("salary cant be empty");
                    editTextPhoneNo.requestFocus();
                    return;
                }
                if(cardexpdate.isEmpty())
                {
                    editTextCardExpDate.setError("salary cant be empty");
                    editTextCardExpDate.requestFocus();
                    return;
                }
                if(cardccv.isEmpty())
                {
                    editTextCardCVV.setError("salary cant be empty");
                    editTextCardCVV.requestFocus();
                    return;
                }

                if (mDatabase.updateCardDetails(cards.getId(),cardholder, Integer.parseInt(cardno), cardtype, Integer.parseInt(cardphoneno), cardexpdate, Integer.parseInt(cardccv))) {
                    Toast.makeText(mctx, "Card updated", Toast.LENGTH_SHORT).show();
                    //refresh update table
                    loadCardFromDatabase();
                }
                else {
                    Toast.makeText(mctx, "Card not updated", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();

            }
        });

    }

    private void deleteCard(final Cards cards)
    {
        AlertDialog.Builder b = new AlertDialog.Builder(mctx);



        b.setTitle("are you sure");
        b.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                if (mDatabase.deleteCard(cards.getId()))
                loadCardFromDatabase();

            }
        });

        b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = b.create();
        alertDialog.show();
    }

    private void loadCardFromDatabase() {

        Cursor cv = mDatabase.allPaymentCardDatabase();

        if(cv.moveToFirst()){
            cardsList.clear();

            do {
                cardsList.add(new Cards(
                        cv.getInt(0),
                        cv.getString(1),
                        cv.getInt(2),
                        cv.getString(3),
                        cv.getInt(4),
                        cv.getString(5),
                        cv.getInt(6)

                ));
            }while (cv.moveToNext());

            notifyDataSetChanged();
        }

    }

}
