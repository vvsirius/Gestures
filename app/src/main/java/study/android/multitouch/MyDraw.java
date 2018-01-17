package study.android.multitouch;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;

public class MyDraw extends View implements OnTouchListener, OnGestureListener, OnScaleGestureListener {

	GestureDetector gestDetector;
	ScaleGestureDetector scaleDetector;

	public MyDraw(Context context) {
		super(context);

		setOnTouchListener(this);
		gestDetector = new GestureDetector(context, this);
		scaleDetector = new ScaleGestureDetector(context, this);

	}

	ArrayList<Drawable> objects = new ArrayList<Drawable>();

	public void draw(Canvas canvas) {
		for (Drawable obj : objects) {
			obj.draw(canvas);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		// пропускаем событие через детекторы
		gestDetector.onTouchEvent(event);
		scaleDetector.onTouchEvent(event);

		// какой индекс пальца в массиве касаний
		int index = event.getActionIndex();
		// какой id у пальца
		int id = event.getPointerId(index);

		// ищем объект, соответствующий пальцу в массиве
		int i = 0;
		Finger finger = null;
		for (; i < objects.size(); i++) {
			if (!(objects.get(i) instanceof Finger))
				continue;
			finger = (Finger) objects.get(i);
			if (finger.id == id)
				break;
		}
		// Если не нашли (палец добавился на экран) создаем новый объект-палец
		if (i == objects.size()) {
			finger = new Finger();
			finger.id = id;
			objects.add(finger);
		}

		// Если отпустили, удаляем объект-палец
		if (event.getActionMasked() == MotionEvent.ACTION_POINTER_UP
				|| event.getActionMasked() == MotionEvent.ACTION_UP)

		{
			objects.remove(i);
		}

		// обновляем информацию о касаниях
		for (i = 0; i < objects.size(); i++) {
			if (!(objects.get(i) instanceof Finger))
				continue;
			finger = (Finger) objects.get(i);
			// для каждого объекта ищем ему соответствующий палец
			int n = event.findPointerIndex(finger.id);
			if (n != -1) {
				// и обновляем
				finger.x = event.getX(n);
				finger.y = event.getY(n);
			}
		}

		// обновляем рисунок
		this.invalidate();

		return true;
	}

	Picture pic;

	/** Закладывает найденный объект или null в переменную pic */
	@Override
	public boolean onDown(MotionEvent e) {
		// снимаем выбор
		pic = null;
		for (int i = 0; i < objects.size(); i++) {
			if (!(objects.get(i) instanceof Picture))
				continue;
			Picture p = (Picture) objects.get(i);
			// если расстояние от точки касания до центра квадратика 
			// меньше половины его стороны
			if (Math.abs(e.getX() - p.x) < p.size / 2 && Math.abs(e.getY() - p.y) < p.size / 2) {
				// запоминаем выбор
				pic = p;
			}
		}
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		if (pic != null && (Math.abs(velocityX) > 5000 || Math.abs(velocityY) > 5000)) {
			objects.remove(pic);
			pic = null;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		objects.add(new Picture(e.getX(), e.getY()));
		invalidate();
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (pic != null) {
			pic.x -= distanceX;
			pic.y -= distanceY;
		}
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		float scale = detector.getCurrentSpan() - detector.getPreviousSpan();
		if (pic != null) {
			pic.size += scale;
			invalidate();
		}
		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {

		// TRUE!!!
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
	}

	
	void findPic(MotionEvent e) {

	}

}
