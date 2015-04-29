package com.example.nammi.jsouptutorial;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    // URL Address
    String url = "http://gsmbensin.is/gsmbensin_web.php";
    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Locate the Buttons in activity_main.xml
        Button titlebutton = (Button) findViewById(R.id.titlebutton);
        Button descbutton = (Button) findViewById(R.id.descbutton);
        Button logobutton = (Button) findViewById(R.id.logobutton);

// Capture button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
// Execute Title AsyncTask
                new Title().execute();
            }
        });

// Capture button click
        descbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
// Execute Description AsyncTask
                new Description().execute();
            }
        });

// Capture button click
        logobutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
// Execute Logo AsyncTask
                new Logo().execute();
            }
        });

    }

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;
        String lada;
        List kaka[];

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
// Connect to the web site
                Document document = Jsoup.connect(url).get();
                Log.d("aoeu", document.toString());
                // Get the html document title
                Elements elements = document.body().getElementsByTag("tr");
                Log.d("asdf",elements.toString());
                lada = elements.text();
                String[] kaka = lada.split(" ");
                Log.d("dudododo",kaka.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
// Set title into TextView
            TextView txttitle = (TextView) findViewById(R.id.titletxt);
            txttitle.setText(title);
            mProgressDialog.dismiss();
        }
    }

    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, Void> {
        String desc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the Meta data
                Elements description = document.select("div[class=pricetable]");
                // Locate the content attribute
                desc = description.attr("tr[class=header]");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
// Set description into TextView
            TextView txtdesc = (TextView) findViewById(R.id.desctxt);
            txtdesc.setText(desc);
            mProgressDialog.dismiss();
        }
    }

    // Logo AsyncTask
    private class Logo extends AsyncTask<Void, Void, Void> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Android Basic JSoup Tutorial");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the class data
                Elements img = document.select("div[class=logo] img[src]");
                // Locate the src attribute
                String imgSrc = img.attr("src");
                // Download image from URL
                InputStream input = new java.net.URL(imgSrc).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
// Set downloaded image into ImageView
            ImageView logoimg = (ImageView) findViewById(R.id.logo);
            logoimg.setImageBitmap(bitmap);
            mProgressDialog.dismiss();
        }
    }
}
