package studip.app.view.news;

import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Button;
import android.widget.TextView;

public class NewsItem {

	public String news_id, topic, body, date, user_id, expire, allow_comments, chdate, chdate_uid, mkdate;

	public TextView topicTV;
	
	public Button button;
	
	public NewsItem(JSONObject json) {
        try {
			news_id = json.getString("news_id");
	        topic = json.getString("topic");
	        body = json.getString("body");
	        user_id = json.getString("user_id");
	        expire = json.getString("expire");
	        allow_comments = json.getString("allow_comments");
	        chdate = json.getString("chdate");
	        chdate_uid = json.getString("chdate_uid");
	        mkdate = json.getString("mkdate");
		} catch (JSONException e) {
			
		}
	}
	
}
