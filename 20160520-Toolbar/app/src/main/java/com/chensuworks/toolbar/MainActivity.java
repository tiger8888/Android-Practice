package com.chensuworks.toolbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * http://www.101apps.co.za/index.php/articles/using-toolbars-in-your-apps.html
 * http://techlovejump.com/android-toolbar-tutorial/
 * https://www.youtube.com/watch?v=pMO8EVkhJO8
 * http://javatechig.com/android/android-lollipop-toolbar-example
 *
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar topToolbar;
    private Toolbar bottomToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        topToolbar = (Toolbar) findViewById(R.id.top_bar);
        setSupportActionBar(topToolbar);

        topToolbar.setTitle("TITLE");
        topToolbar.setSubtitle("this is subtitle");

        topToolbar.setNavigationIcon(R.drawable.ic_action_back);
        topToolbar.setNavigationContentDescription("navigation icon");
        topToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "navigation icon", Toast.LENGTH_SHORT).show();
            }
        });

        topToolbar.setLogo(R.drawable.membrane);
        topToolbar.setLogoDescription("membrane");

        // This trumps over onOptionsItemSelected(MenuItem).
        topToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        Toast.makeText(getApplicationContext(), "Menu1", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu2:
                        Toast.makeText(getApplicationContext(), "Menu2", Toast.LENGTH_SHORT).show();
                        return true;
                }

                return false;
            }
        });

        bottomToolbar = (Toolbar) findViewById(R.id.bottom_bar);
        bottomToolbar.setLogo(R.drawable.membrane);
        bottomToolbar.setLogoDescription("membrane");
        bottomToolbar.setTitle("this is title");
        bottomToolbar.setSubtitle("this is subtitle");
        bottomToolbar.setNavigationIcon(R.drawable.ic_action_back);
        bottomToolbar.inflateMenu(R.menu.menu_items);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Toast.makeText(getApplicationContext(), "menu1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu2:
                Toast.makeText(getApplicationContext(), "menu2", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

}
