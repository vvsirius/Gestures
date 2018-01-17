package study.android.multitouch;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Picture implements Drawable {
	float x, y;
	Paint paint;
	float size;
	int color;

	public Picture(float x, float y) {
		paint = new Paint();
		Random rnd = new Random();
		color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
	    paint.setColor(color);
		this.x = x;
		this.y = y;
		this.size = 100 ;
	}

	public void draw(Canvas canvas) {
		canvas.drawRect(x - size / 2, y - size / 2, x + size / 2, y + size / 2, paint);
	}
}
