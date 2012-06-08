package studip.app.view.news;

import java.util.ArrayList;
import java.util.Map;

import net.oauth.OAuthMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import studip.add.model.OAuthConnector;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewsFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter();
	}
	
	private void setListAdapter() {
		ArrayList<Map.Entry<String, String>> params = new ArrayList<Map.Entry<String, String>>();
		//addPostParameters(params, your_application_data);
		//params.add(new OAuth.Parameter("oauth_token", LocationRepositoriesSqlite.getDefaultAccessToken(this)));

		JSONObject newsJSON = OAuthConnector.sendInvokation(OAuthMessage.GET, "news.json", params);
		
		JSONArray newsArray;
		
		JSONObject news;
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		try {
			newsArray = (JSONArray)newsJSON.get("news");
			
			for (int i = 0; i < newsArray.length(); i++) {
				news = newsArray.getJSONObject(i);
				arrayList.add(news.getString("topic"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList.toArray(new String[0])));
		getListView().setCacheColorHint(0);
		
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

	}

	
}
