package com.example.img;

import com.jm.imagedemo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ImgTrans extends View {
	private Bitmap bitmap;
	private int width;
	private int x1, x2;
	public boolean handler;
	private void init() {
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		width = bitmap.getWidth();
		handler=true;
		x1 = 0;
		x2 = width;
	}

	public ImgTrans(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ImgTrans(Context context) {
		super(context);
		init();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		x1 -= 2;
		x2 -= 2;
		canvas.drawBitmap(bitmap, x1, 0, null);
		canvas.drawBitmap(bitmap, x2, 0, null);
		super.onDraw(canvas);
	}

	public void invalidateView() {
		
			if (x1 < -width) {
				x1 = 0;
				x2 = width;
				handler=false;
				Log.i("img", "reset");
			}
			Log.i("img", "invalidate()");
			invalidate();
	}
}

