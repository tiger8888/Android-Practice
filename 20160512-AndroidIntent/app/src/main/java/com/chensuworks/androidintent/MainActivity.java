package com.chensuworks.androidintent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        Button buttonListContacts = (Button) findViewById(R.id.button_list_contacts);
        buttonListContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.android.contacts.action.LIST_CONTACTS");
                startActivity(intent);
            }
        });

        Button buttonTouchDialer = (Button) findViewById(R.id.button_touch_dialer);
        buttonTouchDialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.android.phone.action.TOUCH_DIALER");
                startActivity(intent);
            }
        });

        Button buttonDial = (Button) findViewById(R.id.button_dial);
        buttonDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4126800518"));
                startActivity(intent);
            }
        });

        Button buttonViewGoogle = (Button) findViewById(R.id.button_view_google);
        buttonViewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(intent);
            }
        });

        Button buttonSettings = (Button) findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.SETTINGS");
                startActivity(intent);
            }
        });

        Button buttonWifiSettings = (Button) findViewById(R.id.button_wifi_settings);
        buttonWifiSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                startActivity(intent);
            }
        });
    }

}
