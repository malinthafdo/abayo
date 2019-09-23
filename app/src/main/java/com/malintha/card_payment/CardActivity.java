package com.malintha.card_payment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    DataBaseManager mDatabase;
    List<Cards> cardList;
    ListView listView;
    String key;
    EditText search;
    SearchView searchView;
    CardAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        mDatabase= new DataBaseManager(this);

        cardList = new ArrayList<>();

        listView = (ListView)findViewById(R.id.listViewCards);

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        allPaymentCardDatabase();

//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                (CardActivity.this).adapter.getFilter().filter(charSequence);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });


    }

    private void allPaymentCardDatabase()
    {
        Cursor cv =  mDatabase.allPaymentCardDatabase();

        if(cv.moveToFirst()){
            do{
                cardList.add(new Cards(
                        cv.getInt(0),
                        cv.getString(1),
                        cv.getInt(2),
                        cv.getString(3),
                        cv.getInt(4),
                        cv.getString(5),
                        cv.getInt(6)

                ) );

            }while (cv.moveToNext());
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            adapter = new CardAdapter(this,R.layout.list_layout_cards,cardList,mDatabase);
            listView.setAdapter(adapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                 key = s;
                 //search.setText(searchView.getQuery());
                Toast.makeText(CardActivity.this, "Search Results for "+s, Toast.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                key = s;
                //search.setText(searchView.getQuery());
                Toast.makeText(CardActivity.this,  "Search Results for "+s, Toast.LENGTH_LONG).show();
                return false;
            }
        });
        return true;
    }



//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.search){
//            String key = findViewById(R.id.search).toString();
//
//            Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
