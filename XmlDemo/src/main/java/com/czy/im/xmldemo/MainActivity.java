package com.czy.im.xmldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.txt);

        domXml();
    }


    private void domXml() {
        try {
            InputStream is = getAssets().open("activity_main.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(is);
            Element root = document.getDocumentElement();
            Log.d(TAG, "domXml:  bookElement.getNodeValue() = " + root.getNodeName());

            NodeList nodeList = root.getElementsByTagName("employee");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element bookElement = (Element) nodeList.item(i);
                Log.d(TAG, "domXml:  bookElement.getNodeValue() = " + bookElement.getNodeName());
            }

            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void saxXml() {

    }
}
