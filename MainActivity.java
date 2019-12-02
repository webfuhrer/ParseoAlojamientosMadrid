package com.example.luis.pruebasvolley;

import android.app.Activity;
import android.support.v7.app.*;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue rq= Volley.newRequestQueue(this);
        String url ="https://www.esmadrid.com/opendata/alojamientos_v1_es.xml";

        Response.Listener oyente=new Response.Listener() {
            @Override
            public void onResponse(Object o) {
                String xml_respuesta=(String)o;
                //ParseaXML.parsearConXmlPullParser(xml_respuesta);

                    ParseaXML.parsearConXmlPullParser(xml_respuesta);

            }
        };
        Response.ErrorListener oyente_fallo=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            Log.v("Error", volleyError.getMessage());
            }
        };

        StringRequest respuesta=new StringRequest(StringRequest.Method.POST, url, oyente, oyente_fallo);

        rq.add(respuesta);
    }
        private void tratarXML(String xml)
        {

        }
    }

