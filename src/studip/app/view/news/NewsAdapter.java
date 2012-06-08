package studip.app.view.news;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import studip.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<String> {
		
	private JSONObject json;
	
	public NewsAdapter(Context context, int textViewResourceId, ArrayList<String> objects, JSONObject json) {
        super(context, textViewResourceId, objects);
        this.json = json;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewsItem ni;
		if (convertView == null){
			LayoutInflater inflater = LayoutInflater.from(NewsActivity.this);
			convertView = inflater.inflate(R.layout.news_item, parent, false);
			ni = new NewsItem(json);

			ni.topicTV = (TextView)convertView.findViewById(R.id.topic);
			ni.button = (Button)convertView.findViewById(R.id.callButton);

			convertView.setTag(ni);
		} else {
			ni = (NewsItem) convertView.getTag();
		}

		ni.topicTV.setText(getItem(position));
		//ni.button.setOnClickListener(callBtnListener);
		ni.button.setTag( getItem(position) );
		return convertView;
	}
}
