package com.maloga.maloga_gps.localisation;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.maloga.maloga_gps.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.DataOutput;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ItineraireTask extends AsyncTask<Void, Integer, Double> {

    private static final String TOAST_MSG = "Calcul de l'itinéraire en cours";
    private static final String TOAST_ERR_MAJ = "Impossible de trouver un itinéraire";
    private static final String LABEL = "REQUETE-ITINERAIRE";

    private Context context;
    private String editDepart;
    private String editArrivee;
    private Activity activity;
    private Double distance;
    private ProgressDialog progressDialog;
    private Boolean needProgressDialog;
    private ArrayList<Location> tab;

    public ItineraireTask(final Activity activity, final String editDepart, final String editArrivee, final Boolean ProgressDialogrequest, final ArrayList<Location> tab) {
        this.context = activity.getApplicationContext();
        this.editDepart = editDepart;
        this.editArrivee = editArrivee;
        this.activity = activity;
        this.needProgressDialog = ProgressDialogrequest;

        if (tab != null){
            this.tab = tab;
        }
    }

    @Override
    protected void onPreExecute() {
        //Toast.makeText(context, TOAST_MSG, Toast.LENGTH_LONG).show();

        if (needProgressDialog){
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Calcul en cours...");
            progressDialog.setMax(10);
            progressDialog.setProgress(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }
    }

    @Override
    protected Double doInBackground(Void... params) {
        try {
            //Construction de l'url à appeler

            // http://dev.virtualearth.net/REST/V1/Routes/Driving?o=xml&wp.0=Taillan+M%C3%A9doc&wp.1=Saint-M%C3%A9dard-En-Jalles&avoid=minimizeTolls&key=AjbhTDB-DVc9L70P4sMSt0reLPPOQR0YeadiU3i64R7gkS31M04tyn6-tn8bKyJW


            StringBuilder url = new StringBuilder("https://dev.virtualearth.net/REST/V1/Routes/Driving?o=xml&avoid=minimizeTolls");
            url.append("&wp.0=");
            url.append(editDepart.replace(' ', '+'));

            if (tab != null){
                for (int i = 1; i < this.tab.size()+1; i++){
                    url.append("&vwp." + i + "=");
                    String point = this.tab.get(i-1).getLatitude() + "," + this.tab.get(i-1).getLongitude();
                    url.append(point);
                }
            }

            url.append("&wp." + (this.tab.size()+1) + "=");
            url.append(editArrivee.replace(' ', '+'));
            url.append("&key=");
            url.append(context.getString(R.string.bing_key));

            Log.d(LABEL, url.toString());

            //Appel du web service
            InputStream stream = new URL(url.toString()).openStream();

            //Traitement des données
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilderFactory.setIgnoringComments(true);
            Document document = documentBuilder.parse(stream);
            document.getDocumentElement().normalize();

            Log.d(LABEL, "Requete reçue");


            //On récupère d'abord le status de la requête

            String status = document.getElementsByTagName("StatusDescription").item(0).getTextContent();
            if(!"OK".equals(status)) {
                String error = document.getElementsByTagName("ErrorDetails").item(0).getTextContent();
                Log.e(LABEL, error);
                Log.e(LABEL, status);
                Crashlytics.log(Log.ERROR, LABEL, error);
                Crashlytics.log(Log.ERROR, LABEL, status);
                return null;
            }else{
                Log.d(LABEL, status);

                // On récupère les steps
                Element elementLeg = (Element) document.getElementsByTagName("Route").item(0);
                String distance = elementLeg.getElementsByTagName("TravelDistance").item(0).getTextContent();

                Log.d(LABEL, distance);

                this.distance = Double.parseDouble(distance);

                int decimalPlaces = 3;
                BigDecimal bd = new BigDecimal(this.distance);

                bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
                this.distance = bd.doubleValue();

                return this.distance;
            }

        }
        catch(final Exception e) {
            Log.e(LABEL, e.toString());
            Crashlytics.log(Log.ERROR, LABEL, e.toString());
            //return  e.toString();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        if (needProgressDialog){
            progressDialog.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(final Double result) {
        super.onPostExecute(distance);

        /*try{
            TextView text = this.activity.findViewById(R.id.distanceText);

            String textContent = text.getText().toString();
            double textInt = Double.parseDouble(textContent);

            Log.d("SET-TEXT", "----------------------");
            Log.d("SET-TEXT", "Content : " + textContent);
            Log.d("SET-TEXT", "Int : " + textInt);
            Log.d("SET-TEXT", "Distance : " + this.distance);

            double fin = Double.parseDouble(this.distance) + textInt;
            NumberFormat formatter = new DecimalFormat("#00.000");
            String last = formatter.format(fin);
            last = String.valueOf(last);
            text.setText(last);

            if (needProgressDialog){
                progressDialog.hide();
            }

        }catch(Exception e){
            if (needProgressDialog){
                progressDialog.hide();
            }
            Log.e("SET-TEXT", e.toString());
            Crashlytics.log(Log.ERROR, "SET-TEXT", e.toString());
        }

         */
    }
}
