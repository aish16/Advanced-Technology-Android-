package com.example.aishwarya.email;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display extends AppCompatActivity {
TextView t;
    private Firebase mref;
    ListView list;
    private ArrayList<String> muser = new ArrayList<>();
    ArrayAdapter<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Bundle b=getIntent().getExtras();
        String s=b.getString("abc");
        Firebase.setAndroidContext(this);
        mref = new Firebase ("https://email-ebb96.firebaseio.com/");



        list=(ListView)findViewById(R.id.list);
        arr =new ArrayAdapter<String >(this,android.R.layout.simple_list_item_1,muser);
        list.setAdapter(arr);
        Toast.makeText(getApplicationContext(),"Loading Data....",Toast.LENGTH_LONG).show();
        com.firebase.client.ValueEventListener valueEventListener = mref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                //Toast.makeText(getApplicationContext(), "on data", Toast.LENGTH_LONG).show();

               /* Log.v("E_VALUE", "Data :" + dataSnapshot.getValue());
                Toast.makeText(getApplicationContext(), "on data change", Toast.LENGTH_LONG).show();
                muser.add(String.valueOf(dataSnapshot.getValue()));
                arr.notifyDataSetChanged();
*/
               for (com.firebase.client.DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String phone = (String)messageSnapshot.getKey();
                   String email = (String) messageSnapshot.child("email").getValue();
                    //mnmuser.add(email);
                   muser.add(phone);
                    arr.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"list",Toast.LENGTH_SHORT).show();
                Intent i =new Intent(Display.this,result.class);
                i.putExtra("item",String.valueOf(list.getSelectedItem()));
                //Toast.makeText(getApplicationContext(),"intent",Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });
    }
}