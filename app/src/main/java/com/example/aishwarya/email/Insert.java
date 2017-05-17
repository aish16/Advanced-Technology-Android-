package com.example.aishwarya.email;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Insert extends AppCompatActivity {
    public Firebase mref;
    Map<String, Object> postValues;
    EditText em, ph, passw;

    Button save;
    String key = "";
    TextView label1;

    private FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Bundle b = getIntent().getExtras();
        String s = b.getString("abc");
        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        label1 = (TextView) findViewById(R.id.label);
        //label1.setText(s);
        em = (EditText) findViewById(R.id.email);
        ph = (EditText) findViewById(R.id.phone);
        passw = (EditText) findViewById(R.id.passw);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://email-ebb96.firebaseio.com/");
        save = (Button) findViewById(R.id.insert);
        postValues = PrintLetterBarcodeData.toMap();
        auth = FirebaseAuth.getInstance();
        //Toast.makeText(getApplicationContext(), "before clck", Toast.LENGTH_LONG).show();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!=null)
                {
                    firebaseAuth.getCurrentUser().sendEmailVerification();

                }
            }
        };


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                label1.setText("");
                //Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
                String key = String.valueOf(ph.getText());
                //Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();
                // Map<String, Object> childUpdates = new HashMap<>();
                //childUpdates.put("/posts/" + key, postValues);
                Firebase child = mref.child(key);
                child.setValue(postValues);
                Firebase ec = child.child("email");
                ec.setValue(String.valueOf(em.getText()));
                key = String.valueOf(ph.getText());
                Firebase pas = child.child("pass");
                pas.setValue(String.valueOf(passw.getText()));
                //key = (String.valueOf(ph.getText()));
                startSignIn();


            }
        });

        mref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                label1.setText("");
                //Log.v("E_VALUE", "Data :" + dataSnapshot.child("name"));
                String key = (String.valueOf(ph.getText()));
               // Toast.makeText(getApplicationContext(), key, Toast.LENGTH_LONG).show();

                //  Toast.makeText(getApplicationContext(), "this is null", Toast.LENGTH_LONG).show();
                label1.setText(String.valueOf(dataSnapshot.child(key)));


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

    @Override
    protected void onStart(){
super.onStart();

        auth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        String email=String.valueOf(em.getText());
        String password=String.valueOf(passw.getText());
        String phone=String.valueOf(ph.getText());
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!phone.matches("[0-9]{10}")) {
            Toast.makeText(getApplicationContext(), "invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                            Toast.makeText(Insert.this, task.getException().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(Insert.this,"Email Sent",
                                    Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }
}


