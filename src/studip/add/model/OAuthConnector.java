package studip.add.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient4.HttpClient4;

public class OAuthConnector {
	
	private final static String CONSUMER_KEY = "4763db64d4776df0fbc47c49ecc74a7104fa7702a";
	private final static String CONSUMER_SECRET = "7b1ee183fb7e22bc3dcee53991b00b6e";
	
	private final static String BASE_URL = "http://devel09.uni-oldenburg.de/trunk/plugins.php/restipplugin";
	private final static String OAUTH_URL = BASE_URL + "/oauth";
	private final static String ACCESS_URL = OAUTH_URL + "/access_token";
	private final static String AUTHORIZATION_URL = OAUTH_URL + "/authorize";
	private final static String REQUEST_URL = OAUTH_URL + "/request_token";	
	
	private final static String JSON_URL = BASE_URL + "/api/";
	
	public static final String APP_PREFS_NAME = "studipAppPrefs";
	
	private static String requestToken;
	private static String requestSecret;
	private static String accessToken;
	private static String accessSecret;
	
	private static OAuthAccessor accessor;
	
	public static void orderToGetRequestToken() {
		OAuthClient client = new OAuthClient(new HttpClient4());
		accessor = defaultAccessor();
		try {
			//hole Request Token
			client.getRequestToken(accessor);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		requestToken = accessor.requestToken;
		requestSecret = accessor.tokenSecret;
	}
	
	public static OAuthAccessor defaultAccessor() {
		String callbackUrl = "";//"studip-android-app:%2F%2F%2F";
		OAuthServiceProvider provider =  new OAuthServiceProvider(REQUEST_URL, AUTHORIZATION_URL, ACCESS_URL);
		OAuthConsumer consumer = new OAuthConsumer(callbackUrl, CONSUMER_KEY, CONSUMER_SECRET, provider);
		OAuthAccessor accessor = new OAuthAccessor(consumer);
		return accessor;
	}
	
	public static void openLoginBrowser(Activity activity) {
		//Browser für das anmelden öffnen	
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(accessor.consumer.serviceProvider.userAuthorizationURL + "?oauth_token="+accessor.requestToken + "&oauth_callback=" + accessor.consumer.callbackURL));
		
		activity.startActivity(intent);
	}
	
	public static void saveRequestTokenAndSecret(Activity activity) {
		SharedPreferences appPrefs = activity.getSharedPreferences(APP_PREFS_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = appPrefs.edit();
		prefsEditor.putString("requestToken", requestToken);
		prefsEditor.putString("requestSecret", requestSecret);
		prefsEditor.commit();
	}
	
	public static void saveAccessTokenAndSecret(Activity activity) {
		SharedPreferences appPrefs = activity.getSharedPreferences(APP_PREFS_NAME, Activity.MODE_PRIVATE);
		SharedPreferences.Editor prefsEditor = appPrefs.edit();
		prefsEditor.putString("accessToken", accessToken);
		prefsEditor.putString("accessSecret", accessSecret);
		prefsEditor.commit();
	}
	
	public static void orderToGetAccessToken(Activity activity) {
		ArrayList<Map.Entry<String, String>> params = new ArrayList<Map.Entry<String, String>>();
		OAuthClient client = new OAuthClient(new HttpClient4());
		accessor = defaultAccessor();

	
		SharedPreferences appPrefs = activity.getSharedPreferences(APP_PREFS_NAME, Activity.MODE_PRIVATE);
		accessor.accessToken = appPrefs.getString("requestToken", "");
		accessor.tokenSecret = appPrefs.getString("requestSecret", "");
		Log.d("requestToken", accessor.accessToken);
		Log.d("requestSecret", accessor.tokenSecret);

		
		try {
			OAuthMessage omessage = client.invoke(accessor, "POST", accessor.consumer.serviceProvider.accessTokenURL, params);
			accessToken = omessage.getParameter("oauth_token");
			accessSecret = omessage.getParameter("oauth_token_secret");
			accessor.accessToken = accessToken;
			accessor.tokenSecret = accessSecret;
			Log.d("accessToken", accessToken);
			Log.d("accessSecret", accessSecret);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JSONObject sendInvokation(String httpMethod, String url, Collection<? extends Entry> parameters) {
		OAuthClient client = new OAuthClient(new HttpClient4());

		JSONObject json = new JSONObject();
		try {	
			OAuthMessage omessage = client.invoke(accessor, httpMethod,  JSON_URL + "/" + url, parameters);
			//Log.d("MESSAGE", omessage.readBodyAsString());
			json = new JSONObject(omessage.readBodyAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return json;
	}
	
	public static String getRequestToken() {
		return requestToken;
	}
	
	public static String getRequestSecret() {
		return requestSecret;
	}
	
	public static String getAccessToken() {
		return accessToken;
	}
	
	public static String getAccessSecret() {
		return accessSecret;
	}
}
