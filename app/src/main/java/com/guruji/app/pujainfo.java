package com.guruji.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class pujainfo extends AppCompatActivity {

    String pujasubname, pujaInfopara, pujaImpo, pujaPrice, pujaSamagri, pujapriceinfo;
    String pujafinal;
    Switch aSwitch;
    Button bookPuja;

    TextView textView, PujaInfo, pujaImportance, pujaprice, pujapricedetail;
    int pujapricceint, pujasama;
    ImageView imageView, imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pujainfo);

        textView = (TextView) findViewById(R.id.pujaNametextView);
        PujaInfo = (TextView) findViewById(R.id.pujaInfoDetails);
        pujaImportance = (TextView) findViewById(R.id.pujaImportance);
        pujaprice = (TextView) findViewById(R.id.pujaprice);
        pujapricedetail = (TextView) findViewById(R.id.pujapricedetails);
        imageView = (ImageView) findViewById(R.id.pujainfoImage);
        aSwitch = (Switch) findViewById(R.id.samagriButton);
        bookPuja= (Button) findViewById(R.id.PujaBook);

        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();

        pujasubname = intent.getStringExtra("userName");
        pujaInfopara = intent.getStringExtra("userDesc");
        pujaImpo = intent.getStringExtra("userImp");
        pujaSamagri = intent.getStringExtra("userAge");
        pujaPrice = intent.getStringExtra("userPrice");
        pujapricceint = Integer.parseInt(pujaPrice);
        pujasama = Integer.parseInt(pujaSamagri);

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!aSwitch.isChecked()){
                    pujaprice.setText("\u20B9"+pujapricceint);
                    pujafinal = pujaPrice;
                    pujapriceinfo = "(This price includes only the Puja booking charges and does not include Samagri price)";
                    pujapricedetail.setText(pujapriceinfo);

                }
                else{

                    int pujapriceUpadte = pujasama+pujapricceint;
                    String pujaupdated = String.valueOf(pujapriceUpadte);
                    pujaprice.setText("\u20B9"+pujaupdated);
                    pujafinal = pujaupdated;
                    pujapriceinfo = "(This price includes Puja booking charges as well as samagri price)";
                    pujapricedetail.setText(pujapriceinfo);


                }

            }
        });
        pujapriceinfo = "(This price includes only the Puja booking charges and does not include Samagri price)";
        pujafinal = pujaPrice;
        pujaprice.setText("\u20B9"+pujafinal);
        pujapricedetail.setText(pujapriceinfo);
        textView.setText(pujasubname);
        PujaInfo.setText(pujaInfopara);
        pujaImportance.setText(pujaImpo);



        bookPuja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pujainfo.this, Booking.class);
                intent.putExtra("Bookname",pujasubname);
                intent.putExtra("BookPricefinal",pujafinal);

                intent.putExtra("BookDetails",pujapriceinfo);


                startActivity(intent);
            }
        });




    }
}