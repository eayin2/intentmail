package myproject.intentmail;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class OptionsActivity extends Activity {
    /** Called when the activity is first created. */

	EditText eemail;
	Button button;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() 
	    {
	        @Override
	        public void onClick(View v) 
	        {
	            savePreferences("storedName", eemail.getText().toString()); // Do some job here
	    		finish();
	        }
	    });
        eemail = (EditText) findViewById(R.id.eemail);
		loadSavedPreferences();
    }
    
    private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String sharedeemail = sharedPreferences.getString("storedName", "");
		eemail.setText(sharedeemail);
	}

	private void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

  
}