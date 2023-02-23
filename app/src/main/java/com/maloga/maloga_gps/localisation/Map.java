package com.maloga.maloga_gps.localisation;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maloga.maloga_gps.R;
import com.maloga.maloga_gps.meteo.GetMeteo;
import com.maloga.maloga_gps.volant.Volant;
import com.maloga.maloga_gps.carnet.Carnet;

import java.util.ArrayList;

public class Map extends FragmentActivity implements OnMapReadyCallback {
    FusedLocationProviderClient fusedLocationClient; // Service de localisation
    Location userStartLocation; // Localisation de départ
    Location userEndLocation; // Localisation d'arrivée
    Location pointM; // Localisation intermédiaire
    Boolean running = false; // Etat du chronomètre
    Chronometer chronometer; // Chronomètre
    DatabaseReference mDatabase;
    long pauseOffset; // Offset pour relancer le chronomètre en cas de pause
    GoogleMap mMap; // Carte GoogleMap
    private LocationRequest locationRequest; // Requete de localisation
    Double distance = 0.0;
    private ArrayList<Location> localisations = new ArrayList<Location>();
    private ArrayList<Location> allLocations = new ArrayList<Location>();
    String meteo;

    String adresseDepart;
    String adresseArrivee;

    /**
     * Paramètres
     */
    Double minSpeed = 5.0; // Vitesse minimale requise pour localisation (Km/h)
    Integer nbLocBeforeRequest = 6; // Nombre minimal de localisations requise pour une requete itinéraire
    Integer intervalForLocRequest = 20000; // Nombre maximal de milissecondes entre chaque requete de localisation
    Integer fastestIntervalForLocRequest = 10000; // Nombre minimal de milissecondes entre chaque requete de localisation


    // Methode de retour de localisation à chaque actualisation
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                String startPoint = pointM.getLatitude() + "," + pointM.getLongitude();
                String endPoint = location.getLatitude() + "," + location.getLongitude();


                String speed = Float.toString(location.getSpeed());

                if (location.getSpeed() > (minSpeed / 3.6 )){
                    // On récupère la localisation pour le prochain calcul
                    localisations.add(location);
                    allLocations.add(location);

                    LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(point).title("Etape").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                    Log.d("LOCALISATION", "Localisation sauvegardé pour le prochain calcul...");
                }

                if (localisations.size() >= nbLocBeforeRequest){
                    ItineraireTask itineraireTask = new ItineraireTask(Map.this, startPoint, endPoint, false, localisations);
                    itineraireTask.execute();

                    try {
                        distance += itineraireTask.get();
                    }catch (Exception e){
                        Log.e("ERREUR", e.toString());
                    }

                    TextView textView = findViewById(R.id.distanceText);
                    textView.setText(String.valueOf(distance));

                    pointM = localisations.get(localisations.size() - 1);
                    localisations = new ArrayList<Location>();
                    Log.d("REQUETE-TRAJET", String.valueOf(distance));
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_loc);

        nuit(); // Vérifie si le mode nuit est activé

        getWindow(). addFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); // Garde l'écran de conduite allumer

        // Paramètres de la requete pour la localisation
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(intervalForLocRequest);
        locationRequest.setFastestInterval(fastestIntervalForLocRequest);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // On déclare la carte Google Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                finish();
                Log.d("MAP", "Fermée !");
            }
        };
        Map.this.getOnBackPressedDispatcher().addCallback(this, callback);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap){
        mMap = googleMap;

        // On vérifie si la localisation est activée
        if (isLocationEnabled()){
            // On récupère le service de localisation
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            // On prend la localisation du point de départ
            mMap.setMyLocationEnabled(true);
            getStartLocation();

            allLocations.add(userStartLocation);


            // On attend 700ms et on lance le runnable
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {

                    Log.d("RESULTAT", userStartLocation.getLatitude() + " : " + userStartLocation.getLongitude());

                    if (userStartLocation.getLongitude() != 0 && userStartLocation.getLatitude() != 0){

                        // On affiche la localisation sur la carte
                        LatLng startPoint = new LatLng(userStartLocation.getLatitude(), userStartLocation.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(startPoint).title("Départ"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(startPoint));
                        mMap.setMinZoomPreference(10);
                        mMap.setMaxZoomPreference(15);

                        // On récupère le chronomètre
                        chronometer = (Chronometer) findViewById(R.id.simpleChronometer);
                        // On lance le chronomètre
                        startChronometer();

                        // On récupère l'adresse de départ
                        RequeteAdressTask requeteAdressTask = new RequeteAdressTask(Map.this, userStartLocation);
                        requeteAdressTask.execute();

                        try {
                            adresseDepart = requeteAdressTask.get();
                            TextView textView = findViewById(R.id.departText);
                            textView.setText("Départ : " + adresseDepart);
                        }catch (Exception e){
                            Log.e("ERREUR", e.toString());
                        }

                        final Button pauseButton = findViewById(R.id.pauseButton);
                        pauseButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (running){
                                    pauseChronometer();
                                    pauseButton.setBackground(getResources().getDrawable(R.drawable.start2));
                                    stopLocationUpdates();
                                }else{
                                    startChronometer();
                                    pauseButton.setBackground(getResources().getDrawable(R.drawable.pause));
                                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                                    startLocationUpdates();
                                }
                            }
                        });

                        pointM = userStartLocation;

                        startLocationUpdates();

                        final Button stopButton = findViewById(R.id.stopButton);
                        stopButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fusedLocationClient = LocationServices.getFusedLocationProviderClient(Map.this);
                                pauseChronometer();
                                getEndLocation();
                                allLocations.add(userEndLocation);

                                Runnable runnable1 = new Runnable() {
                                    @Override
                                    public void run() {
                                        //http://dev.virtualearth.net/REST/V1/Routes?wp.0=37.779160067439079,-122.42004945874214&wp.1=32.715685218572617,-117.16172486543655&key=BingMapsKey

                                        LatLng finPoint = new LatLng(userEndLocation.getLatitude(), userEndLocation.getLongitude());
                                        mMap.addMarker(new MarkerOptions().position(finPoint).title("Arrivée"));
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(finPoint));

                                        String startPoint = pointM.getLatitude() + "," + pointM.getLongitude();
                                        String endPoint = userEndLocation.getLatitude() + "," + userEndLocation.getLongitude();

                                        stopLocationUpdates();

                                        ItineraireTask itineraireTask = new ItineraireTask(Map.this, startPoint, endPoint, false, localisations);
                                        itineraireTask.execute();

                                        try {
                                            distance += itineraireTask.get();
                                        }catch (Exception e){
                                            Log.e("ERREUR", e.toString());
                                        }

                                        // On récupère l'adresse de départ
                                        RequeteAdressTask requeteAdressTask = new RequeteAdressTask(Map.this, userEndLocation);
                                        requeteAdressTask.execute();

                                        try {
                                            adresseArrivee = requeteAdressTask.get();
                                        }catch (Exception e){
                                            Log.e("ERREUR", e.toString());
                                        }

                                        GetMeteo getMeteo = new GetMeteo(Map.this, userEndLocation);
                                        getMeteo.execute();

                                        try {
                                            String meteo = getMeteo.get();
                                            TextView text = findViewById(R.id.departText);
                                            text.setText(meteo);
                                        }catch (Exception e){
                                            Log.e("ERREUR", e.toString());
                                        }


                                        Runnable runnable2 = new Runnable() {
                                            @Override
                                            public void run() {
                                                sauvegarde();
                                            }
                                        };
                                        new Handler().postDelayed(runnable2, 1000000);

                                    }
                                };

                                new Handler().postDelayed(runnable1, 2000);
                            }
                        });
                    }else{
                        Log.e("ITINERAIRE", "Impossible de récupérer la localisation");
                        Crashlytics.log(Log.ERROR, "ITINERAIRE", "Impossible de récupérer la localisation de l'appareil");
                        Intent intent = new Intent(getApplicationContext(), Volant.class);
                        startActivity(intent);
                        Map.this.finish();
                    }
                }
            };
            new Handler().postDelayed(runnable, 2000);

        }else{
            /**
             * On demande d'activer la localisation
             */
            askForTurningOnLocation();

            /**
             * On relance l'activité par la suite
             */
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    restartActivity();
                }
            };
            new Handler().postDelayed(runnable, 8000);
        }//
    }

    /**
     * Cette méthode lance l'actualisation automatique de la position
     */
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
    /**
     * Cette méthode interrompt l'actualisation automatique de la position
     */
    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    /**
     * Cette méthide permet de démarrer le chronomètre
     */
    public void startChronometer(){
        if (!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    /**
     * Cette méthode met le chronomètre en pause
     */
    public void pauseChronometer(){
        if (running){
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.stop();
            running = false;
        }
    }

    /**
     * Cette méthode remet le chronomètre à 0
     */
    public void resetChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    /**
     * Ces méthodes récupèrent la localisation
     */
    public void getStartLocation(){
        Log.d("LOCALISATION", "Asking for localisation...");
        try{
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d("LOCALISATION", "Return a localisation");
                                userStartLocation =  location;
                                Log.d("LOCALISATION", userStartLocation.getLatitude() + " : " + userStartLocation.getLongitude());
                            }else{
                                // userStartLocation = null;
                                Log.d("LOCALISATION", "No localisation get");
                            }
                        }
                    });
        }finally {
            Log.d("LOCALISATION", "Nothing...");
        }
    }
    public void getEndLocation(){
        Log.d("LOCALISATION", "Asking for localisation...");
        try{
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d("LOCALISATION", "Return a localisation");
                                userEndLocation =  location;
                                Log.d("LOCALISATION", userEndLocation.getLatitude() + " : " + userEndLocation.getLongitude());
                            }else{
                                // userStartLocation = null;
                                Log.d("LOCALISATION", "No localisation get");
                            }
                        }
                    });
        }finally {
            Log.d("LOCALISATION", "Nothing...");
        }
    }

    /**
     * Cette méthode redémarre l'application
     */
    public void restartActivity(){
        Intent intent = new Intent();
        intent.setClass(Map.this, Map.this.getClass());
        Map.this.startActivity(intent);
        Map.this.finish();
    }

    /**
     * Cette fonction demande l'activation de la localisation
     */
    public void askForTurningOnLocation(){
        new AlertDialog.Builder(this, R.style.AppTheme_Dialog_Alert)
                .setTitle("Localisation requise !")
                .setMessage("Aucun accès à la localisation. Veuillez vérifier si celle-ci est bien activée dans vos paramètres avant de poursuivre.")
                .setPositiveButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int wich) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                }).create().show();

    }

    /**
     * Vérifie si la localisation est activé
     * @return
     */
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    /**
     * Cette méthode gère le mode nuit
     */
    private void nuit(){
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Boolean nuit = sharedPref.getBoolean("nuit",false);
        if(nuit == true){
            ConstraintLayout layout = findViewById(R.id.activity_map_loc);
            layout.setBackgroundResource(R.color.theme2);
            TextView distancetxt = findViewById(R.id.distanceText);
            distancetxt.setTextColor(getResources().getColor(R.color.colorText1));
            TextView km = findViewById(R.id.textView1);
            km.setTextColor(getResources().getColor(R.color.colorText1));
            Chronometer chrono = findViewById(R.id.simpleChronometer);
            chrono.setTextColor(getResources().getColor(R.color.colorText1));
            TextView editText = findViewById(R.id.departText);
            editText.setTextColor(getResources().getColor(R.color.colorText1));
        }else{
            ConstraintLayout layout = findViewById(R.id.activity_map_loc);
            layout.setBackgroundResource(R.color.theme1);
        }
    }

    String temps;
    String nom_voiture;
    String login_email;

    Integer nombre_vue;
    Integer name_pos;

    private void sauvegarde(){
        //Météo obtenu
        GetMeteo getMeteo = new GetMeteo(Map.this, userEndLocation);
        getMeteo.execute();

        try {
            meteo = getMeteo.get();
            Log.d("METEO","météo : "+meteo);
        }catch (Exception e){
            Log.e("ERREUR", e.toString());
        }

        //Charger le numero de fenetre
        SharedPreferences sharedPref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        nombre_vue = Integer.parseInt(sharedPref.getString("nombre_fenetre_a_charger", "0"));
        nombre_vue++;
        Log.d("DATAID","Nombre de fentre : " + nombre_vue);
        login_email = sharedPref.getString("email","NULL").replace(".","%");
        Log.d("DATAID","Email : " + login_email);
        //Convertis le temps en String
        Integer tempo2 = (int) (SystemClock.elapsedRealtime() - chronometer.getBase());
        temps = tempo2.toString();
        temps = temps.replace(":",".");
        Integer tempo = Math.round(Integer.parseInt(temps));
        float tempo3 = tempo;
        tempo3 = tempo3 / 1000 / 60;
        Integer tempo4 = Math.round(tempo3);
        Log.d("tempo4",tempo4.toString());
        temps = tempo4.toString();
        nom_voiture = sharedPref.getString("geo_trajet_nom_voiture","null");
        name_pos = Integer.parseInt(sharedPref.getString("geo_trajet_name_pos","null"));
        String distanceS = distance.toString().replace(".",",");
        float distanceF = Float.parseFloat(distanceS.replace(",","."));
        Integer distanceI = Math.round(distanceF);

        //Sauvegarde en ligne
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child("nbr").setValue(nombre_vue.toString());
        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("A").setValue(adresseDepart);
        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("B").setValue(adresseArrivee);
        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Distance").setValue(distanceI.toString());
        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Temps").setValue(temps);
        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Voiture").setValue(nom_voiture);
        mDatabase.child("Users").child(login_email).child("Trajets").child("Id").child(nombre_vue.toString()).child("Meteo").setValue(meteo);


        //Sauvegarde locale
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nombre_fenetre_a_charger",nombre_vue.toString());
        editor.putString("fenetre"+nombre_vue+"_distance",distanceI.toString());
        editor.putString("fenetre"+nombre_vue+"_temps",temps);
        editor.putString("fenetre"+nombre_vue+"_A",adresseDepart);
        editor.putString("fenetre"+nombre_vue+"_B",adresseArrivee);
        editor.putString("fenetre"+nombre_vue+"_voiture",nom_voiture);
        editor.putString("fenetre"+nombre_vue+"_voiture_num",name_pos.toString());
        editor.putString("fenetre"+nombre_vue+"_meteo",meteo);
        editor.commit();


        Toast.makeText(getApplicationContext(),"Trajet ajouté",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), Carnet.class);
        startActivity(intent);
    }
}