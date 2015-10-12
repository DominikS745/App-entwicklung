package de.dhbw.pizzabutler;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.List;

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
        boolean facebookAppFound = false;

        // Überprüfung ob die Facebook app installiert ist
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        //Fallback falls die Facebook app nicht installiert ist
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }

    public void onClickTwitter(View v) {
        try {
            // Überprüfung ob die Twitter app installiert ist
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            Intent postToTwitter = new Intent(Intent.ACTION_SEND);
            postToTwitter.setClassName("com.twitter.android", "com.twitter.android.composer.ComposerActivity");
            postToTwitter.setType("text/plain");
            postToTwitter.putExtra(Intent.EXTRA_TEXT, getString(R.string.twitter_post));
            startActivity(postToTwitter);
        } catch (Exception e) {
            //Fallback falls die Twitter app nicht installiert ist
            String url = "http://www.twitter.com/intent/tweet?text=" + getString(R.string.twitter_post);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }

}
