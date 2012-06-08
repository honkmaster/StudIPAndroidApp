package studip.app.view.news;

import studip.app.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class NewsActivity extends FragmentActivity {
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		this.setContentView(R.layout.news_view);
	    getSupportFragmentManager().beginTransaction().add(R.id.news_placeholder, new NewsFragment(), "news").commit();
	}
}
