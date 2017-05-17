package com.example.aishwarya.email;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aishwarya.email.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class result extends AppCompatActivity {


    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin;
    private Firebase mref;
TextView t;
    private ArrayList<String> muser = new ArrayList<>();
    String key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle b=getIntent().getExtras();
        key=b.getString("item");
        mref = new Firebase("https://email-ebb96.firebaseio.com/");


        inputEmail = (EditText) findViewById(R.id.em);
        inputPassword = (EditText) findViewById(R.id.pass);
        btnLogin = (Button) findViewById(R.id.show);
        t=(TextView)findViewById(R.id.resu);


btnLogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String k="999";
        Firebase child = mref.child(k);
        child.setValue(String.valueOf(inputEmail.getText()));
        child.removeValue();

        mref.addValueEventListener(new com.firebase.client.ValueEventListener()  {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                // Toast.makeText(getApplicationContext(), "on data", Toast.LENGTH_LONG).show();

               /* Log.v("E_VALUE", "Data :" + dataSnapshot.getValue());
                Toast.makeText(getApplicationContext(), "on data change", Toast.LENGTH_LONG).show();
                muser.add(String.valueOf(dataSnapshot.getValue()));
                arr.notifyDataSetChanged();
*/
                for (com.firebase.client.DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String password = (String)messageSnapshot.child("pass").getValue();
                    String email = (String) messageSnapshot.child("email").getValue();

                    String inem=inputEmail.getText().toString();
                    String inpass=inputPassword.getText().toString();


                    try {
                        if (email.trim().equals(inem)) {
                          //  Toast.makeText(getApplicationContext(),inpass,Toast.LENGTH_LONG).show();
                           // Toast.makeText(getApplicationContext(),password,Toast.LENGTH_LONG).show();



                    if (password.trim().equals(inpass))
                    {
                        key=messageSnapshot.getKey();
                        Toast.makeText(getApplicationContext(),"Successfully Retrieved",Toast.LENGTH_LONG).show();
                        t.setText(String.valueOf( dataSnapshot.child(key).getValue()));
                    // Toast.makeText(getApplicationContext(),"right pass",Toast.LENGTH_LONG).show();


                     }
                     }
                    }
                    catch (Exception e)
                    {
                      //  Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                    //mnmuser.add(email);


                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        t.setText(String.valueOf(muser));


    }
});

            }

}

