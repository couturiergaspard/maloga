package com.maloga.maloga_gps.volant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.maloga.maloga_gps.carnet.Carnet;
import com.maloga.maloga_gps.param.Parametre;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.localisation.Map;

import java.util.ArrayList;


public class Volant extends AppCompatActivity {

    private Button bouton_geo;
    private Button bouton_carnet;
    private Button bouton_parametre;
    private Button bouton_volant;
    private Button new_car;
    private Integer ACCESS_LOCATION_CODE = 1;

    String nom_voiture;

    Integer name_pos = 0;

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volant);

        //Crashlytics
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Crashlytics.setUserEmail(user.getEmail());
        Crashlytics.setUserIdentifier(user.getUid());

        //Boutons
        nuit();
        spinner();


        SharedPreferences pref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        String monemail = pref.getString("email","NULL");
        Crashlytics.setUserIdentifier(monemail);

        bouton_carnet = findViewById(R.id.carnet);
        bouton_carnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activite_carnet();
            }
        });
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
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Parametre.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });

        bouton_parametre = findViewById(R.id.parametre);
        bouton_parametre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activite_parametre();
            }
        });
        bouton_parametre.setOnTouchListener(new View.OnTouchListener() {
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
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Parametre.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });

        bouton_geo = findViewById(R.id.geo_start);
        bouton_geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("geo_trajet_nom_voiture",nom_voiture);
                name_pos++;
                editor.putString("geo_trajet_name_pos",name_pos.toString());
                name_pos--;
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
            }
        });
        bouton_geo.setOnTouchListener(new View.OnTouchListener() {
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
                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else if (x1 - x2 > 200) {
                    //Right
                    Intent i = new Intent(getApplicationContext(), Parametre.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                    break;
                }
                return false;
            }
        });

        bouton_volant = findViewById(R.id.volant);
        bouton_volant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("geo_trajet_nom_voiture",nom_voiture);
                name_pos++;
                editor.putString("geo_trajet_name_pos",name_pos.toString());
                name_pos--;
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), Map.class);
                startActivity(intent);
            }
        });
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
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Parametre.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });

        new_car = findViewById(R.id.button_new);
        new_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),new_voiture.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_fast,R.anim.fade_out_fast);
            }
        });
        new_car.setOnTouchListener(new View.OnTouchListener() {
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
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Parametre.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.volant).setOnTouchListener(new View.OnTouchListener() {
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
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Parametre.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });
    }


    //Fonctions pour lancer les activités :
    public void activite_carnet() {
        Intent intent_carnet = new Intent(this, Carnet.class);
        startActivity(intent_carnet);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void activite_parametre() {
        Intent intent_parametre = new Intent(this, Parametre.class);
        startActivity(intent_parametre);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void nuit() {
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit", false);
        if (nuit == true) {
            ConstraintLayout layout = findViewById(R.id.activity_volant);
            layout.setBackgroundResource(R.color.theme2);
            Button geo = findViewById(R.id.geo_start);
            geo.setBackground(getResources().getDrawable(R.drawable.base_n));
        } else {
            ConstraintLayout layout = findViewById(R.id.activity_volant);
            layout.setBackgroundResource(R.color.theme1);
        }
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
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
                    Intent i = new Intent(getApplicationContext(), Carnet.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else if (x1 - x2 > 200) {
                    //Right
                    Intent i = new Intent(getApplicationContext(), Parametre.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return false;
    }
    public void spinner(){
        final Spinner voiture = findViewById(R.id.spinner);
        final SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        ArrayList<String> area = new ArrayList<>();
        Integer nombre_voiture = Integer.parseInt(sharedPref.getString("nombre_voiture","null"));
        for(int i = 1; i <= nombre_voiture; i++) {
            //add values in area arrayList
            String voiture_numero = "voiture_numero_" + i;
            area.add(sharedPref.getString(voiture_numero, "null"));
        }


        Boolean nuit = sharedPref.getBoolean("nuit", false);
        if (nuit == true) {
            voiture.setPopupBackgroundResource(R.color.theme2);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_itemn,area);
            voiture.setAdapter(adapter);
            ViewCompat.setBackgroundTintList(voiture, ColorStateList.valueOf(getResources().getColor(R.color.colorText1)));
        } else {
            voiture.setPopupBackgroundResource(R.color.theme1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_itemj,area);
            voiture.setAdapter(adapter);
        }

        voiture.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name_pos = position;
                Log.d("SPINNER",name_pos.toString());
                name_pos++;
                nom_voiture = sharedPref.getString("voiture_numero_"+name_pos,"null");
                name_pos--;
                SharedPreferences.Editor editor = sharedPref.edit();
                Integer tempo = name_pos;
                editor.putString("wvoiture",tempo.toString());
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        voiture.setOnTouchListener(new View.OnTouchListener() {
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
                            Intent i = new Intent(getApplicationContext(), Carnet.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        } else if (x1 - x2 > 200) {
                            //Right
                            Intent i = new Intent(getApplicationContext(), Parametre.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                        break;
                }
                return false;
            }
        });
    }
}
