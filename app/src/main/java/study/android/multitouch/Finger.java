package study.android.multitouch;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Finger implements Drawable{
	float x, y;
	int color;
	int id;
	Paint paint;
	public Finger() {
		paint = new Paint();
		paint.setColor(Color.argb(128, 0, 0, 0));
		paint.setTextSize(100);
	}
	public void draw(Canvas canvas)
	{
		//будем рисовать со сдвигом, 
		// чтобы не загораживать пальцами
		float shift = 50;
		paint.setColor(Color.BLACK);
		canvas.drawCircle(x - shift, y - shift, 100, paint);
		paint.setColor(Color.WHITE);
		canvas.drawText(id + "", x - shift - 40, y - shift, paint);
		
	}
}
