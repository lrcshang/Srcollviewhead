package com.example.scrolltop;

import com.example.scrolltop.MyScrollView.OnScrollListener;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnScrollListener{
	private HorizontalScrollView hView;
	private MyScrollView myScrollView;
    private int searchLayoutTop;
    
    LinearLayout search01,search02;
    RelativeLayout rlayout;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化控件
		init();
		// 返回箭头（默认不显示）  
        getActionBar().setDisplayHomeAsUpEnabled(false);  
       // 左侧图标点击事件使能  
       getActionBar().setHomeButtonEnabled(true);  
       // 使左上角图标(系统)是否显示  
       getActionBar().setDisplayShowHomeEnabled(false);  
       // 显示标题  
       getActionBar().setDisplayShowTitleEnabled(false);  
       //显示自定义视图  
       getActionBar().setDisplayShowCustomEnabled(true);  
       View actionbarLayout = LayoutInflater.from(this).inflate(  
               R.layout.actionbar, null);  
       getActionBar().setCustomView(actionbarLayout);  
	}

	@SuppressLint("NewApi")
	private void init() {
		hView = (HorizontalScrollView)findViewById(R.id.search_edit);
		myScrollView = (MyScrollView)findViewById(R.id.myScrollView);
		search01 = (LinearLayout)findViewById(R.id.search01);
		search02 = (LinearLayout)findViewById(R.id.search02);
		rlayout = (RelativeLayout)findViewById(R.id.rlayout);
		ListViewForScrollView listViewForSc=(ListViewForScrollView) findViewById(R.id.listView1);
		String [] arr_data = {"数据1","数据2","数据3","数据4","数据2","数据3","数据4","数据2","数据3","数据4","数据2","数据3","数据4","数据2","数据3","数据4"};

		//新建一个数组适配器ArrayAdapter绑定数据，参数(当前的Activity，布局文件，数据源)
		ArrayAdapter arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_data);

		//视图(ListView)加载适配器
		listViewForSc.setAdapter(arr_adapter);
		myScrollView.smoothScrollTo(0, 0); 
		myScrollView.setOnScrollListener(this);  
//		iamge = (ImageView)findViewById(R.id.image);
		drawable = getResources().getDrawable(R.drawable.bg11);
		drawable.setAlpha(START_ALPHA);
//		iamge.setBackgroundDrawable(drawable);
	}
	private static final int START_ALPHA = 0;
	private static final int END_ALPHA = 255;
	private int fadingHeight = 600;   //当ScrollView滑动到什么位置时渐变消失（根据需要进行调整）
	private Drawable drawable;  
//	ImageView iamge;
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);    
        if(hasFocus){  
        	searchLayoutTop = rlayout.getBottom();//获取searchLayout的顶部位置
        }
	}

	//监听滚动Y值变化，通过addView和removeView来实现悬停效果
	@Override
	public void onScroll(int scrollY) {
		rlayout.scrollTo(0, -scrollY/4);
		if (scrollY > fadingHeight) {
			scrollY = fadingHeight;   //当滑动到指定位置之后设置颜色为纯色，之前的话要渐变---实现下面的公式即可
		}
		drawable.setAlpha(scrollY * (END_ALPHA - START_ALPHA) / fadingHeight + START_ALPHA);
		if(scrollY >= searchLayoutTop){  
			if (hView.getParent()!=search01) {
				search02.removeView(hView);
				search01.addView(hView);
			}
        }else{
        	if (hView.getParent()!=search02) {
        		search01.removeView(hView);
        		search02.addView(hView);
			}
        }
	}
}
