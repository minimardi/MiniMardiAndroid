package net.sourceforge.minimardi;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MinimardiPreferenceActivity extends PreferenceActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);        
        addPreferencesFromResource(R.xml.preferences);        
    }
}