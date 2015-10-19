package de.dhbw.pizzabutler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Marius on 19.10.2015.
 */
public class DialogLocation extends AppCompatActivity {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Benutzen der Builder-Class um das ding zu bauen
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS ist nicht aktiv");
        builder.setMessage("Bitte aktivieren Sie ihr GPS");

        //Gar nicht erst die Möglichkeit des negierens geben (nur Okay-Button)
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                // Zeigt die Systemeinstellungen wenn der User zustimmt
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent,1);
            }
        });
        // Erstellen des Dialogs und wirft ihn aus
        return builder.create();
    }
}
