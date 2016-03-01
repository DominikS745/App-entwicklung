package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class StartActivity extends BaseActivity {

    //Diese beiden Variablen für NavDrawer
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navdrawer_start);


        //Icons und Text für NavDrawer initalisieren
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load titles from strings.xml
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);//load icons from strings.xml
        set(navMenuTitles, navMenuIcons);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
            if (id == R.id.action_share) {
            //Share Method
            Intent postToTwitter = new Intent(Intent.ACTION_SEND);
            postToTwitter.setType("text/plain");
            postToTwitter.putExtra(Intent.EXTRA_TEXT, getString(R.string.twitter_post));
            startActivity(Intent.createChooser(postToTwitter, "Share with"));
        }
        */

        return super.onOptionsItemSelected(item);
    }

    //Aufruf der Location-Abfrage (-Activity)
    public void onClickLocation(View v) {
        Intent findLocation = new Intent(this, LocationActivity.class);
        startActivity(findLocation);
    }






    //Onclick Methoden für Twitter und Facebook:
    public void onClickFacebook(View v) {
        String urlToShare = "http://wwww.facebook.com"; //Muss noch auf die URL von PizzaButler geändert werden
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    public void onClickTwitter(View v) {
        Intent postToTwitter = new Intent(Intent.ACTION_SEND);
        postToTwitter.setType("text/plain");
        postToTwitter.putExtra(Intent.EXTRA_TEXT, getString(R.string.twitter_post));
        startActivity(Intent.createChooser(postToTwitter, "Share with"));
    }
}
