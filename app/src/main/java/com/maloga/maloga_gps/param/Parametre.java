package com.maloga.maloga_gps.param;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.maloga.maloga_gps.carnet.Carnet;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.connexion.Login;
import com.maloga.maloga_gps.volant.Volant;

public class Parametre extends AppCompatActivity {

    private Button bouton_volant;
    private Button bouton_carnet;
    private Button bouton_deconnection;
    private Switch switch_sombre;
    private Boolean nuit_t = false;
    private Button info_t;
    private Button info_c;
    private Button notif;
    private Button propos;
    private Button help;
    private Button contact;

    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);

        //Boutons
        bouton_volant = findViewById(R.id.volant);
        bouton_volant.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            bouton_volant.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    activite_volant();
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });

        bouton_carnet = findViewById(R.id.carnet);
        bouton_carnet.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            bouton_carnet.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    activite_carnet();
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });

        bouton_deconnection = findViewById(R.id.deconnexion);
        bouton_deconnection.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            bouton_deconnection.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deconnexion();
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });


        switch_sombre = findViewById(R.id.switch_s);
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_parametre);
            layout.setBackgroundResource(R.color.theme2);
            switch_sombre.setTextColor(getResources().getColor(R.color.colorText1));
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_parametre);
            layout.setBackgroundResource(R.color.theme1);
            switch_sombre.setTextColor(getResources().getColor(R.color.colorText1));
        }
        switch_sombre.setChecked(nuit);
        nuit_t = nuit;
        switch_sombre.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            switch_sombre.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mode_couleur();
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });

        info_t = findViewById(R.id.infotrajet);
        info_t.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            info_t.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), information_trajets.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });

        info_c = findViewById(R.id.compte);
        info_c.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            info_c.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), information_compte.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });

        notif = findViewById(R.id.notif);
        notif.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            notif.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), information_notification.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });

        propos = findViewById(R.id.apropos);
        propos.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            propos.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), a_propos.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });


        help = findViewById(R.id.aide);
        help.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            help.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), com.maloga.maloga_gps.param.help.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });


        contact = findViewById(R.id.contact);
        contact.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x2 - x1 > 200) {
                            //Left
                            Intent i = new Intent(getApplicationContext(), Volant.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else {
                            //Right
                            contact.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), com.maloga.maloga_gps.param.contact.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                }
                            });
                        }
                        break;
                }
                return false;
            }
        });
    }


    //Fonctions pour lancer les activités :
    public void activite_volant(){
        Intent intent_volant = new Intent(this, Volant.class);
        startActivity(intent_volant);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void activite_carnet(){
        Intent intent_carnet = new Intent(this, Carnet.class);
        startActivity(intent_carnet);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public void deconnexion(){
        Intent intent_deconnexion = new Intent(this, Login.class);
        startActivity(intent_deconnexion);
    }
    public void mode_couleur(){
        nuit_t = !nuit_t;
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(nuit_t == true){
            editor.putBoolean("nuit",true);
            editor.commit();
            nuit();
        }else{
            editor.putBoolean("nuit",false);
            editor.commit();
            nuit();
        }
    }
    public void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_parametre);
            layout.setBackgroundResource(R.color.theme2);
            switch_sombre.setTextColor(getResources().getColor(R.color.colorText1));
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_parametre);
            layout.setBackgroundResource(R.color.theme1);
            switch_sombre.setTextColor(getResources().getColor(R.color.colorText1));
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
                    Intent i = new Intent(getApplicationContext(), Volant.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                } else if (x1 - x2 > 200) {
                    //Right
                }
                break;
        }
        return false;
    }
}
