package study.android.multitouch;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	MyDraw myDraw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myDraw = new MyDraw(this);
		setContentView(myDraw);
	}

}