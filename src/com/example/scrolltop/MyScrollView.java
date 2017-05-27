package com.example.scrolltop;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/*
 * ScrollView并没有实现滚动监听，�?以我们必须自行实现对ScrollView的监听，
 * 我们很自然的想到在onTouchEvent()方法中实现对滚动Y轴进行监�?
 * ScrollView的滚动Y值进行监�?
 */
public class MyScrollView extends ScrollView {
	private OnScrollListener onScrollListener;  
    /** 
     * 主要是用在用户手指离�?MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比�? 
     */  
    private int lastScrollY;
    
	public MyScrollView(Context context) {
		super(context, null);
	}
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}
	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	/** 
     * 设置滚动接口 
     * @param onScrollListener 
     */
	public void setOnScrollListener(OnScrollListener onScrollListener){
		this.onScrollListener = onScrollListener;
	}
	/** 
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法�? 
     */  
    private Handler handler = new Handler() {  
  
        public void handleMessage(android.os.Message msg) {  
            int scrollY = MyScrollView.this.getScrollY();  
              
            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发�?�消�?  
            if(lastScrollY != scrollY){  
                lastScrollY = scrollY;  
                handler.sendMessageDelayed(handler.obtainMessage(), 5);    
            }  
            if(onScrollListener != null){  
                onScrollListener.onScroll(scrollY);  
            }  
              
        };  
  
    }; 
    /** 
     * 重写onTouchEvent�? 当用户的手在MyScrollView上面的时候， 
     * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候， 
     * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发�?�消息，在handler处理 
     * MyScrollView滑动的距�? 
     */ 
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(onScrollListener != null){  
            onScrollListener.onScroll(lastScrollY = this.getScrollY());  
        }  
        switch(ev.getAction()){  
        case MotionEvent.ACTION_UP:  
             handler.sendMessageDelayed(handler.obtainMessage(), 20);    
            break;  
        }  
		return super.onTouchEvent(ev);
	}

	/** 
     * 滚动的回调接�? 
     */  
    public interface OnScrollListener{  
        /** 
         * 回调方法�? 返回MyScrollView滑动的Y方向距离 
         */  
        public void onScroll(int scrollY);  
    }
}
