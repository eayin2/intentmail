package myproject.intentmail;

import java.util.ArrayList;
import java.util.List;
import myproject.intentmail.opt_event_Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;  

public class MainActivity extends Activity {
	private Spinner spinner1;
	private String[] recipients = new String[1]; 
	private EditText numsubject;
	private String ssubject;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // here we specify the layouts xml files
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        recipients[0] = loadSavedPreferences(); 
        Toast.makeText(MainActivity.this, recipients[0], Toast.LENGTH_LONG).show();
        List<String> list = new ArrayList<String>();
        opt_event_Activity i_opt_event = new opt_event_Activity();
        String events_string = i_opt_event.loadSavedPreferences2();
        try {
        	String[] events_array = events_string.split("\\s+");
            for (String s: events_array) {
            	list.add(s);
            }
        } catch (NumberFormatException e) {
        }
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item,list);
		dataAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
		addListenerOnSpinnerItemSelection();
        numsubject = (EditText) findViewById(R.id.numsubject);
    	spinner1 = (Spinner) findViewById(R.id.spinner1);
        Button sendBtn = (Button) findViewById(R.id.sendEmail);
        sendBtn.setOnClickListener(new View.OnClickListener() {	
            public void onClick(View view) {
        	    sendEmail();
        	    numsubject.setText("");
            }
            
        });
    }

    @Override  
    public boolean onCreateOptionsMenu(Menu menu) {  
        getMenuInflater().inflate(R.menu.main, menu);
        return true;  
    }  
      			  
    @Override  
    public boolean onOptionsItemSelected(MenuItem item) {  
        switch (item.getItemId()) {  
            case R.id.opt_email:  
                Intent ooptions = new Intent(this, OptionsActivity.class); // this refers to the current object
                this.startActivity(ooptions);
                break;
            case R.id.opt_event:  
                Intent ooptions2 = new Intent(this, opt_event_Activity.class); // this refers to the current object
                this.startActivity(ooptions2);
                break;
            default:  
                return super.onOptionsItemSelected(item);  
        }
        return true;
    }
    
	public void addListenerOnSpinnerItemSelection(){
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}
    
	private String loadSavedPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
		.getDefaultSharedPreferences(this);
		String sharedeemail = sharedPreferences.getString("storedName", "");	
		return sharedeemail;
	}
	 
    protected void sendEmail() {
        recipients[0] = loadSavedPreferences(); 
    	Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
    	email.setType("message/rfc822");
    	email.putExtra(Intent.EXTRA_EMAIL, recipients);
    	ssubject = String.valueOf(spinner1.getSelectedItem()) + " " + numsubject.getText().toString();
    	email.putExtra(Intent.EXTRA_SUBJECT, ssubject);
    	email.setType("text/html");   	// intent.setType("text/plain");
    	final PackageManager pm = getPackageManager(); // Choosing  for intent. 
    	final List<ResolveInfo> matches = pm.queryIntentActivities(email, 0);
    	ResolveInfo best = null;
    	for (final ResolveInfo info : matches) {
    	    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
    	        best = info;
    	        break;
    	    }
    	}
    	if (best != null) {
    	    email.setClassName(best.activityInfo.packageName, best.activityInfo.name);
    	}
    	try {
    		startActivity(email);
        } catch (android.content.ActivityNotFoundException ex) {
        Toast.makeText(MainActivity.this, "No email client installed.",
       		 Toast.LENGTH_LONG).show();
        }
    }
}