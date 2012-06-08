package studip.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import studip.add.model.OAuthConnector;
import studip.app.view.news.NewsActivity;
import studip.app.view.slideout.MenuActivity;
import studip.app.view.slideout.SlideoutActivity;


public class StudIPAppActivity extends Activity {
	
	/**
	 *  Called when the activity is first created. 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//um die NetworkOnMainThreadException zu vermeiden
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 



		//prüfen, ob das App schon gestartet wurde und die requestToken und Secret schon geholt wurden
		//dieser Wert ist in boolena firststart gespeichert
		SharedPreferences appPrefs = getSharedPreferences("studipAppPrefs", MODE_PRIVATE);
		//TODO ...
		boolean firststart = appPrefs.getBoolean("firststart", true);

		OAuthConnector.orderToGetRequestToken();

		OAuthConnector.saveRequestTokenAndSecret(this);

		OAuthConnector.openLoginBrowser(this);
	}


	
	@Override
	public void onResume() {
		super.onResume();

		//SlideMenu
		setContentView(R.layout.news_view);
		findViewById(R.id.slide_button).setOnClickListener(
				new OnClickListener() {
					//@Override
					public void onClick(View v) {
						slideButtonPressed(v);
					}
				});
		
		OAuthConnector.orderToGetAccessToken(this);
		

		startActivity(new Intent(StudIPAppActivity.this, NewsActivity.class));


		//Log.d("JSON", json.toString());

		//JSONObject news = json.getJSONObject("news");
		//Log.d("NEWS", news.toString());

	}
	
	public void slideButtonPressed(View view) {
		int width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
		SlideoutActivity.prepare(StudIPAppActivity.this, R.id.inner_content, width);
		startActivity(new Intent(StudIPAppActivity.this, MenuActivity.class));
		overridePendingTransition(0, 0);
	}
	
   
    public void login(View view) {
    	EditText editText = (EditText) findViewById(R.id.passwort);
        String message = editText.getText().toString();
        
        if (!message.equals("")) {
        
        	TextView tv = new TextView(this);
        	tv.setText(message);
        	this.setContentView(tv);
        }
    }

}