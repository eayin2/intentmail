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

public class opt_event_Activity extends Activity {
    /** Called when the activity is first created. */

	EditText opt_event;
	Button button;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opt_event);

		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() 
	    {
	        @Override
	        public void onClick(View v) 
	        {
	            savePreferences("stored_opt_event", opt_event.getText().toString()); // Do some job here
	    		finish();
	        }
	    });
        opt_event = (EditText) findViewById(R.id.opt_event);
		loadSavedPreferences();
    }
    public String loadSavedPreferences2() {
  		SharedPreferences sharedPreferences = PreferenceManager
  				.getDefaultSharedPreferences(this);
  		String shared_opt_event = "Bauch"; // declare before try, else you cant access it outside try
  		try {
  			shared_opt_event = sharedPreferences.getString("stored_opt_event", "");
  		} catch (NumberFormatException e) {
  			shared_opt_event = "Bauch"; 
        }
  		/*
  		if (shared_opt_event == null ) {
  		    shared_opt_event = "Bauch";
  		}*/
  		return shared_opt_event;
  	}
    private void loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String shared_opt_event = sharedPreferences.getString("stored_opt_event", "");
		opt_event.setText(shared_opt_event);
	}

	private void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

  
}