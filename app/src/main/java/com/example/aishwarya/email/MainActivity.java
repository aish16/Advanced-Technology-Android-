package com.example.aishwarya.email;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import static android.R.attr.key;


public class MainActivity extends AppCompatActivity {
    private Button scan_btn,login;

    TextView t;
    private static final String TAG = MainActivity.class.getName();
    private static final String BOTTOM_SHEET_STATE = "bottom_sheet_state";
    private static final String LAST_UID = "last_uid";
    private List<PrintLetterBarcodeData> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan_btn = (Button) findViewById(R.id.scan);
        final Activity activity = this;

        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Display.class);
                i.putExtra("abc","abc");
                startActivity(i);
            }
        });


        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });

    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            t=(TextView)findViewById(R.id.result);


            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();

                } else {
                   // Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    t.setText(result.getContents());
                    PrintLetterBarcodeData barcodeData;
                    String format=result.getFormatName().toString();
                    String contents=result.getContents();
                    Log.d(TAG, format);
                    Log.d(TAG, contents);
                    XmlPullParserFactory xmlFactoryObject;
                    try {
                        xmlFactoryObject = XmlPullParserFactory.newInstance();
                        XmlPullParser myparser = xmlFactoryObject.newPullParser();
                        myparser.setInput(new StringReader(contents));
                        int event = myparser.getEventType();
                        while(event != XmlPullParser.END_DOCUMENT){
                            //String name = myparser.getName();
                            switch(event){
                                case XmlPullParser.START_TAG:
                                    break;
                                case XmlPullParser.END_TAG:
                                    barcodeData = new PrintLetterBarcodeData();
                                    barcodeData.setUid(myparser.getAttributeValue(null, "uid"));
                                    barcodeData.setName(myparser.getAttributeValue(null, "name"));
                                    barcodeData.setCo(myparser.getAttributeValue(null, "co"));
                                    barcodeData.setDist(myparser.getAttributeValue(null, "dist"));
                                    barcodeData.setGender(myparser.getAttributeValue(null, "gender"));
                                    barcodeData.setHouse(myparser.getAttributeValue(null, "house"));
                                    barcodeData.setLoc(myparser.getAttributeValue(null, "loc"));
                                    barcodeData.setPc(myparser.getAttributeValue(null, "pc"));
                                    barcodeData.setState(myparser.getAttributeValue(null, "state"));
                                    barcodeData.setStreet(myparser.getAttributeValue(null, "street"));
                                    barcodeData.setVtc(myparser.getAttributeValue(null, "vtc"));
                                    barcodeData.setYob(myparser.getAttributeValue(null, "yob"));
                                    t.setText("Scanned Successfully");
                                   Intent i =new Intent(MainActivity.this,Insert.class);
                                    String a=barcodeData.toString();
                                    i.putExtra("abc",a);
                                    startActivity(i);

                            }
                            event = myparser.next();

                        }
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }









                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


