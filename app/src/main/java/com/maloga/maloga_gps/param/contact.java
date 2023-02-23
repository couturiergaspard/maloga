package com.maloga.maloga_gps.param;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.param.Parametre;

public class contact extends AppCompatActivity {

    Button button_return;

    float x1,x2,y1,y2;

    EditText commentaire;

    String monemail;

    Button send;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        button_return = findViewById(R.id.button_return);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Parametre.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
        nuit();
        mail();
    }
    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_contact);
            layout.setBackgroundResource(R.color.theme2);
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_contact);
            layout.setBackgroundResource(R.color.theme1);
        }
    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x2 - x1 > 200) {
                    //Left
                    Intent i = new Intent(getApplicationContext(), Parametre.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (x1 - x2 > 200) {
                    //Right
                }
                break;
        }
        return false;
    }
    public void mail(){
        //On envois le message donné à la base de donnée
        SharedPreferences pref = getSharedPreferences("DATA",Context.MODE_PRIVATE);
        monemail = pref.getString("email","NULL");
        monemail = monemail.replace(".","%");
        commentaire = findViewById(R.id.commentaire);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                if (commentaire.getText().toString().length() >= 5) {
                    try {
                        mDatabase.child("Commentary").child(monemail).setValue(commentaire.getText().toString().replace(".", "%"));
                        Toast.makeText(getApplicationContext(), "Commentaire envoyé", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Parametre.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
