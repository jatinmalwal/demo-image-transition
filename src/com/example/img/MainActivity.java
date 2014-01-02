package com.example.img;

import com.jm.imagedemo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class MainActivity extends Activity {
	public ImgTrans imgtransObj;
	private Runnable r;
	private Handler handler;
	private boolean flag;
	private ListView listView;
	private boolean flagAnim;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imgtransObj = (ImgTrans) findViewById(R.id.img_trans);
		handler = new Handler();
		flag = true;
		r = new Runnable() {
			public void run() {
				if (flag) {
					callInvalidate();
					Log.i("thread", "handler.postDelayed(this, 16L)");
					handler.postDelayed(this, 16L);
				}
			}
		};
		((Button) findViewById(R.id.btn_trans)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("thread", "handler.post(r)");
				flag = true;
				imgtransObj.handler = true;
				handler.post(r);
				// handler.postDelayed(r, 16L);

			}
		});

		// InflateListView
		String[] values = { "help", "more","< API 11" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
		listView = (ListView) findViewById(R.id.list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new  OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}
		});

		((ImageView) findViewById(R.id.img_options)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// try2
				// MainActivity.this.openOptionsMenu();

				if (Build.VERSION.SDK_INT >= 11) {
					// try1
					PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
					MenuInflater menuInflater = popupMenu.getMenuInflater();
					menuInflater.inflate(R.menu.custom_menu, popupMenu.getMenu());
					popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub
							return false;
						}
					});
					popupMenu.show();

				} else {
					// try3
					if (!flagAnim) {
						Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.options_anim_out);
						listView.setAnimation(animation);
						listView.setVisibility(View.VISIBLE);
					} else {
						Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.options_anim_in);
						listView.setAnimation(animation);
						listView.setVisibility(View.INVISIBLE);
					}
					flagAnim = !flagAnim;
				}
			}
		});
		
		ViewServer.get(this).addWindow(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.custom_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public void callInvalidate() {
		imgtransObj.invalidateView();
		if (!imgtransObj.handler) {
			Log.i("thread", "handler.removeCallbacks(r)");
			handler.removeCallbacks(r);
			flag = false;

		}
	}
	
	@Override
	protected void onDestroy() {
		ViewServer.get(this).removeWindow(this);
		super.onDestroy();
	}
	
    @Override
    public void onResume() {
    	super.onResume();
    	ViewServer.get(this).setFocusedWindow(this);
    }
}
