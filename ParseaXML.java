package com.example.luis.pruebasvolley;

/**
 * Created by luis on 06/11/2017.
 */

import android.util.Log;
import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by LUIS on 27/10/2016.
 */
public class ParseaXML {

    public static void parsearConXmlPullParser(String xml_respuesta) {
        XmlPullParser parser = Xml.newPullParser();
        InputStream in=null;
        try {
            in = new ByteArrayInputStream(xml_respuesta.getBytes());

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            leerServiceList(parser, "serviceList");
        }
        catch(Exception e)
        {

        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void leerServiceList(XmlPullParser parser, String serviceList) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, serviceList);
        while (parser.next() != XmlPullParser.END_TAG) {//Mientras no llegue al final de la etiqueta serviceList
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                Log.d("ENIF", "ENIF");
                continue;
            }
            String name = parser.getName();
            // Busco etiqueta "service" que está dentro de serviceList
            if (name.equals("service")) {
                tratarService(parser);
            } else {
                skip(parser);
            }
        }
    }

    private static void tratarService(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "service");
        while (parser.next() != XmlPullParser.END_TAG) {//Mientras no llegue al final de la etiqueta serviceList
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Compruebo si el nombre de etiqueta me interesa
            if (name.equals("basicData")) {
                tratarBasicData(parser);
            }
            else if(name.equals("multimedia"))
            {
                tratarMultimedia(parser);
            } else {
                 skip(parser);
            }
        }
    }

    private static void tratarMultimedia(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "multimedia");
        while (parser.next() != XmlPullParser.END_TAG) {//Mientras no llegue al final de la etiqueta serviceList
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("media")) {
                extraerMedia(parser);
            }
            else {
                skip(parser);
            }
        }
    }

    private static void extraerMedia(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "media");
        while (parser.next() != XmlPullParser.END_TAG) {//Mientras no llegue al final de la etiqueta serviceList
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("url")) {
                extraerUrl(parser);
            }
            else {
                skip(parser);
            }
        }
    }

    private static void extraerUrl(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "url");
        if (parser.next() == XmlPullParser.TEXT) {
            String url = parser.getText();
            Log.d("url", url);
            parser.nextTag();
        }

        parser.require(XmlPullParser.END_TAG, null, "url");
    }

    private static void tratarBasicData(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "basicData");
        while (parser.next() != XmlPullParser.END_TAG) {//Mientras no llegue al final de la etiqueta serviceList
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("title")) {
                extraerTitulo(parser);
            }
            else if (name.equals("body"))
                {
                    extraerBody(parser);

            } else {
                 skip(parser);
            }
        }
    }
    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private static void extraerTitulo(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "title");
        if (parser.next() == XmlPullParser.TEXT) {
            String titulo = parser.getText();
            Log.d("titulo", titulo);
            parser.nextTag();
        }

        parser.require(XmlPullParser.END_TAG, null, "title");

    }
    private static void extraerBody(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, "body");
        if (parser.next() == XmlPullParser.TEXT) {
            String body = parser.getText();
            Log.d("body", body);
            parser.nextTag();
        }

        parser.require(XmlPullParser.END_TAG, null, "body");

    }

   
}
