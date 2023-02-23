package com.maloga.maloga_gps.meteo;

import com.maloga.maloga_gps.R;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

<<<<<<< HEAD
=======
/**
 * Fait par Gaspard Couturier
 * 2020
 */

>>>>>>> feature/Louis
public class GetMeteo extends AsyncTask<Void, Integer, String>{

    private Context context;
    private Location location;
    private String meteo;

    private Hashtable<Integer, String> weather_conditions = new Hashtable<Integer, String>();



    public GetMeteo(final Context context, final Location location) {
        this.context = context;
        this.location = location;
    }

    //actions éxecutées en arriere plan
    @Override
    protected String doInBackground(Void... params) {

        //api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={your api key}

        try{

            StringBuilder url = new StringBuilder("https://api.openweathermap.org/data/2.5/weather?&units=metric&mode=xml");
            url.append("&lat=" + location.getLatitude());
            url.append("&lon=" + location.getLongitude());
            url.append("&appid=" + context.getString(R.string.meteo_key));

            Log.d("REQUETTE", url.toString());

            InputStream stream = new URL(url.toString()).openStream();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilderFactory.setIgnoringComments(true);
            Document document = documentBuilder.parse(stream);
            document.getDocumentElement().normalize();

            Log.d("REQUETTE", "Requete reçue");

            Element e = (Element) document.getElementsByTagName("weather").item(0);
            String codeMeteo = e.getAttribute("number");

            Log.d("METEO", codeMeteo);

            if (200 <= Integer.parseInt(codeMeteo) && Integer.parseInt(codeMeteo) < 300){ return "Orage"; }
            if (300 <= Integer.parseInt(codeMeteo) && Integer.parseInt(codeMeteo) < 400){ return "Bruine"; }
            if (500 <= Integer.parseInt(codeMeteo) && Integer.parseInt(codeMeteo) < 600){ return "Pluie"; }
            if (600 <= Integer.parseInt(codeMeteo) && Integer.parseInt(codeMeteo) < 700){ return "Neige"; }
            if (801 <= Integer.parseInt(codeMeteo) && Integer.parseInt(codeMeteo) < 805){ return "Nuages"; }
            if (800 == Integer.parseInt(codeMeteo)){ return "Soleil"; }

        }catch(Exception e){

        }
        return null;
    }

    //retour des actions éxecutées en arrière plan
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
