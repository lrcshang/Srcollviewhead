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
		//��ʼ���ؼ�
		init();
		// ���ؼ�ͷ��Ĭ�ϲ���ʾ��  
        getActionBar().setDisplayHomeAsUpEnabled(false);  
       // ���ͼ�����¼�ʹ��  
       getActionBar().setHomeButtonEnabled(true);  
       // ʹ���Ͻ�ͼ��(ϵͳ)�Ƿ���ʾ  
       getActionBar().setDisplayShowHomeEnabled(false);  
       // ��ʾ����  
       getActionBar().setDisplayShowTitleEnabled(false);  
       //��ʾ�Զ�����ͼ  
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
		String [] arr_data = {"����1","����2","����3","����4","����2","����3","����4","����2","����3","����4","����2","����3","����4","����2","����3","����4"};

		//�½�һ������������ArrayAdapter�����ݣ�����(��ǰ��Activity�������ļ�������Դ)
		ArrayAdapter arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_data);

		//��ͼ(ListView)����������
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
	private int fadingHeight = 600;   //��ScrollView������ʲôλ��ʱ������ʧ��������Ҫ���е�����
	private Drawable drawable;  
//	ImageView iamge;
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);    
        if(hasFocus){  
        	searchLayoutTop = rlayout.getBottom();//��ȡsearchLayout�Ķ���λ��
        }
	}

	//��������Yֵ�仯��ͨ��addView��removeView��ʵ����ͣЧ��
	@Override
	public void onScroll(int scrollY) {
		rlayout.scrollTo(0, -scrollY/4);
		if (scrollY > fadingHeight) {
			scrollY = fadingHeight;   //��������ָ��λ��֮��������ɫΪ��ɫ��֮ǰ�Ļ�Ҫ����---ʵ������Ĺ�ʽ����
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
