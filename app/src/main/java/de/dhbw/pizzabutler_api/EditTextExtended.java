package de.dhbw.pizzabutler_api;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * erweitert die api-Klasse EditText
 * überschreibt die setError()-Methode, um eine Fehlermeldung trotz leeren Strings zu vermeiden
 * (schwarze Box)
 */
public class EditTextExtended extends EditText{

    public EditTextExtended(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        setCompoundDrawables(null, null, icon, null);
    }
}
