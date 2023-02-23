package com.maloga.maloga_gps.localisation;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.maloga.maloga_gps.R;

import org.w3c.dom.Document;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RequeteAdressTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private Location userLocation;
    private String adresse = null;

    private static final String LABEL = "REQUETE-ADRESSE";

    public RequeteAdressTask(final Context context, final Location location) {
        this.context = context;
        this.userLocation = location;
    }

    @Override
    protected String doInBackground(Void... param) {
        // http://dev.virtualearth.net/REST/v1/Locations/44.9083624,-0.6946826?o=xml&key=AjbhTDB-DVc9L70P4sMSt0reLPPOQR0YeadiU3i64R7gkS31M04tyn6-tn8bKyJW

        try {
            StringBuilder url = new StringBuilder("https://dev.virtualearth.net/REST/v1/Locations/");
            url.append(userLocation.getLatitude() + ",");
            url.append(userLocation.getLongitude());
            url.append("?o=xml&avoid=minimizeTolls&key=");
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

            String status = document.getElementsByTagName("StatusDescription").item(0).getTextContent();

            if (!"OK".equals(status)) {
                String error = document.getElementsByTagName("ErrorDetails").item(0).getTextContent();
                Log.e(LABEL, error);
                Log.e(LABEL, status);
                Crashlytics.log(Log.ERROR, LABEL, error);
                Crashlytics.log(Log.ERROR, LABEL, status);
            } else {
                Log.d(LABEL, status);

                //On récupère les steps
                String rue = document.getElementsByTagName("AddressLine").item(0).getTextContent();
                String codePostal = document.getElementsByTagName("PostalCode").item(0).getTextContent();
                String city = document.getElementsByTagName("Locality").item(0).getTextContent();
                String confidence = document.getElementsByTagName("Confidence").item(0).getTextContent();

                adresse = rue + ", "+ city;

                Log.d(LABEL, adresse);

                if (adresse == null || adresse.isEmpty()){
                    adresse =  "Impossible de trouver une adresse pour votre point de départ. (" + userLocation.getLatitude() + " ; " + userLocation.getLongitude() + ")";
                }
            }

//
            return adresse;

        } catch (final Exception e) {
            Log.e(LABEL, e.toString());
            Crashlytics.log(Log.ERROR, LABEL, e.toString());
            return "Impossible de trouver une adresse pour votre point de départ. (" + userLocation.getLatitude() + " ; " + userLocation.getLongitude() + ")";
        }
    }

    @Override
    protected void onPostExecute(final String result) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                RequeteAdressTask.super.onPostExecute(adresse);
            }
        };

        new Handler().postDelayed(runnable, 5000);

    }
}
