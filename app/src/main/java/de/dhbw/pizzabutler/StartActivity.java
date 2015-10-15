package de.dhbw.pizzabutler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Toolbar toolbar = (Toolbar) findViewById(R.id.start_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
