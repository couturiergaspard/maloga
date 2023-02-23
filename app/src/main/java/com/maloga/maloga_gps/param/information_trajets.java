package com.maloga.maloga_gps.param;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.maloga.maloga_gps.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class information_trajets extends AppCompatActivity {

    Button button_return;

    String numero_voiture;

    Integer temp;
    Integer voiturenbr = 0;
    Integer nbrvoiture = 0;
    Integer numero_graph = 1;
    Integer distance_totale = 0;
    Integer temps_total = 0;

    TextView distance_moy_t;
    TextView temps_moy_t;

    float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_trajets);
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
        chart();
        chart2();
        chart3();
        moy();
    }

    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_information_trajets);
            layout.setBackgroundResource(R.color.theme2);
            temps_moy_t = findViewById(R.id.moy_temps);
            temps_moy_t.setTextColor(Color.WHITE);
            distance_moy_t = findViewById(R.id.moy_dist);
            distance_moy_t.setTextColor(Color.WHITE);
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_information_trajets);
            layout.setBackgroundResource(R.color.theme1);
        }
    }
    //On detecte un slide
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2) {
                    //Left
                    Intent i = new Intent(getApplicationContext(), Parametre.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return false;
    }
    //Mise en place de diagramme camembert
    public void chart(){
        /**
         * on récupère les données
         */
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Integer i;
        Integer voiture_totale_int = Integer.parseInt(sharedPref.getString("nombre_fenetre_a_charger", "0"));
        Log.d("CAMEMBERT","nombre fenetre à charger : "+voiture_totale_int.toString());
        /**
         * Remets tout à 0
         */
        for(i = Integer.parseInt(sharedPref.getString("nombre_voiture","0")); i >= 0; i--) {
            editor.putString("voiture" +i+"_percent","0");
            Log.d("CAMEMBERT","del num = "+i.toString());
            editor.commit();
        }

        /**
         * assigne les valeurs
         */
        Integer w;
        for(w = voiture_totale_int; w != 0; w--){
            Log.d("CAMEMBERT","w = " + w.toString());
            voiturenbr = Integer.parseInt(sharedPref.getString("fenetre"+w+"_voiture_num","0"));
            temp = Integer.parseInt(sharedPref.getString("voiture"+voiturenbr.toString()+"_percent","0"));
            temp++;
            Log.d("CAMEMBERT","temp : "+temp);
            Log.d("CAMEMBERT","voiture nbr : "+ voiturenbr.toString());
            editor.putString("voiture"+voiturenbr.toString()+"_percent",temp.toString());
            editor.commit();
        }
        nbrvoiture = Integer.parseInt(sharedPref.getString("nombre_voiture","0"));
        Integer v;
        //On crée la liste
        List<SliceValue> pieData = new ArrayList<>();
        for(v = nbrvoiture;v >= 0; v--){
            float percent = Integer.parseInt(sharedPref.getString("voiture"+v+"_percent","0"));
            Log.d("CAMEMBERT","v = "+v);
            Log.d("CAMEMBERT","percent before : " + percent);
            percent = (percent/(voiture_totale_int))*100;
            Log.d("CAMEMBERT","total : " + voiture_totale_int);
            Log.d("CAMEMBERT","percent after : " + percent);
            if(percent != 0){
                /**
                 * on ajoute les valeurs
                 */
                if(percent > 10){
                    Integer x = v + 1;
                    String nom = sharedPref.getString("voiture_numero_"+x,"NULL");
                    Integer val = Math.round(percent);
                    nom = nom + " : "+ val;
                    nom = nom + " %";
                    if(numero_graph == 1){
                        pieData.add(new SliceValue(percent,getResources().getColor(R.color.colorText1)).setLabel(nom));
                    }else if (numero_graph == 2){
                        pieData.add(new SliceValue(percent,Color.RED).setLabel(nom));
                    }else if (numero_graph == 3){
                        pieData.add(new SliceValue(percent,Color.GREEN).setLabel(nom));
                    }else if (numero_graph == 4){
                        pieData.add(new SliceValue(percent,Color.BLUE).setLabel(nom));
                    }else if (numero_graph == 5){
                        pieData.add(new SliceValue(percent,Color.YELLOW).setLabel(nom));
                    }else if (numero_graph == 6){
                        pieData.add(new SliceValue(percent,Color.CYAN).setLabel(nom));
                    }else if (numero_graph == 7){
                        pieData.add(new SliceValue(percent,Color.MAGENTA).setLabel(nom));
                    }else if (numero_graph == 8){
                        pieData.add(new SliceValue(percent,Color.GRAY).setLabel(nom));
                    }else if (numero_graph == 9){
                        pieData.add(new SliceValue(percent,Color.BLACK).setLabel(nom));
                    }else if (numero_graph == 10){
                        pieData.add(new SliceValue(percent,getResources().getColor(R.color.theme1)).setLabel(nom));
                    }
                    numero_graph++;
                }
                Log.d("CAMEMBERT","percent add : "+ percent);
            }
        }

        /**
         * on l'ajoute au xml
         */
        PieChartView pieChartView = findViewById(R.id.chart);
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("Trajets par voiture").setCenterText1FontSize(15).setCenterText1Color(Color.parseColor("#ffffff"));
        pieChartView.setPieChartData(pieChartData);
    }
    public void chart2(){
        List<SliceValue> pieData2 = new ArrayList<>();
        pieData2.add(new SliceValue(100,Color.WHITE).setLabel("Aucune information !"));
        /**
         * on l'ajoute au xml
         */
        PieChartView pieChartView = findViewById(R.id.chart2);
        PieChartData pieChartData = new PieChartData(pieData2);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("Météo").setCenterText1FontSize(15).setCenterText1Color(Color.parseColor("#ffffff"));
        pieChartView.setPieChartData(pieChartData);
    }
    public void chart3(){
        List<SliceValue> pieData3 = new ArrayList<>();
        pieData3.add(new SliceValue(100,Color.WHITE).setLabel("Aucune information !"));
        /**
         * on l'ajoute au xml
         */
        PieChartView pieChartView = findViewById(R.id.chart3);
        PieChartData pieChartData = new PieChartData(pieData3);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("Type de route").setCenterText1FontSize(15).setCenterText1Color(Color.parseColor("#ffffff"));
        pieChartView.setPieChartData(pieChartData);
    }
    public void moy() {
        /**
         * Distance moyenne
         */
        //On récupère les donées
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Integer i;
        Integer nbr_trajet_int = Integer.parseInt(sharedPref.getString("nombre_fenetre_a_charger", "0"));
        for(i = 0;i <= nbr_trajet_int;i++){
            distance_totale = distance_totale + Integer.parseInt(sharedPref.getString("fenetre"+i+"_distance","0"));
        }
        Integer distance_moy = Math.round(distance_totale/nbr_trajet_int);
        distance_moy_t = findViewById(R.id.moy_dist);
        distance_moy_t.setText("Distance moyenne par trajet : "+distance_moy.toString()+"km");


        /**
         * Temps moyen
         */
        //On récupère les donées
        Integer j;
        Log.d("INFO_TRAJ",nbr_trajet_int.toString());
        for(j = 0;j <= nbr_trajet_int;j++){
            temps_total = temps_total + Integer.parseInt(sharedPref.getString("fenetre"+j+"_temps","0"));
            Log.d("INFO_TRAJ",temps_total.toString());
        }
        Integer temps_moy = Math.round(temps_total/nbr_trajet_int);
        temps_moy_t = findViewById(R.id.moy_temps);
        temps_moy_t.setText("Temps moyen par trajet : "+temps_moy.toString()+"min");
    }
}
