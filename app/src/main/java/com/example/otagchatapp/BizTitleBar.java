package com.example.otagchatapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class BizTitleBar extends RelativeLayout {
	
	private Context mContext;
	
	private TextView mTvTitle;

	private String mType = "10";
	
	public BizTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mContext = context;
		View titleBarLayout = View.inflate(mContext, R.layout.common_title_bar, null);
		addView(titleBarLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		//+ type
		String strType = attrs.getAttributeValue(null, "Type");
		setTitleBarType(strType);

		//+ title
		String strTitle = attrs.getAttributeValue(null, "title");
		setTitle(strTitle);
	}
	
	/*
	 * set type to titlebar
	 * @param strType
	 */
	public void setTitleBarType(String strType) {
		
		if(strType == null) {
			return;
		}

		mType = strType;

		/*
			1. img left            + title middle
			2.                       title middle
			3. img  +  title left  +                +   img right
			4. img  +  title left  +                +  text right
			5. img  +  title left
			6. title left          +                +  img(1) + img(2) right
			7. title left          +                +  img(2) right
			8. title left
			9. img + title left    + 				+ img(1) + img(2) right
		*/
		
		//+ type
		switch (strType) {
			case "1":
				findViewById(R.id.rl_title_1).setVisibility(View.VISIBLE);
				findViewById(R.id.rl_title_2).setVisibility(View.GONE);
				findViewById(R.id.rl_title_3).setVisibility(View.GONE);

				findViewById(R.id.iv_title1_left).setVisibility(View.VISIBLE);
				mTvTitle = (TextView) findViewById(R.id.tv_title1_title);
				break;
			case "2":
				findViewById(R.id.rl_title_1).setVisibility(View.VISIBLE);
				findViewById(R.id.rl_title_2).setVisibility(View.GONE);
				findViewById(R.id.rl_title_3).setVisibility(View.GONE);

				findViewById(R.id.iv_title1_left).setVisibility(View.GONE);
				mTvTitle = (TextView) findViewById(R.id.tv_title1_title);
				break;
			case "3":
				findViewById(R.id.rl_title_1).setVisibility(View.GONE);
				findViewById(R.id.rl_title_2).setVisibility(View.VISIBLE);
				findViewById(R.id.rl_title_3).setVisibility(View.GONE);

				findViewById(R.id.tv_title2_right).setVisibility(View.GONE);
				findViewById(R.id.iv_title2_right).setVisibility(View.VISIBLE);
				mTvTitle = (TextView) findViewById(R.id.tv_title2_title);
				break;
			case "4":
				findViewById(R.id.rl_title_1).setVisibility(View.GONE);
				findViewById(R.id.rl_title_2).setVisibility(View.VISIBLE);
				findViewById(R.id.rl_title_3).setVisibility(View.GONE);

				findViewById(R.id.tv_title2_right).setVisibility(View.VISIBLE);
				findViewById(R.id.iv_title2_right).setVisibility(View.GONE);
				mTvTitle = (TextView) findViewById(R.id.tv_title2_title);
				break;
			case "5":
				findViewById(R.id.rl_title_1).setVisibility(View.GONE);
				findViewById(R.id.rl_title_2).setVisibility(View.VISIBLE);
				findViewById(R.id.rl_title_3).setVisibility(View.GONE);

				findViewById(R.id.tv_title2_right).setVisibility(View.GONE);
				findViewById(R.id.iv_title2_right).setVisibility(View.GONE);
				mTvTitle = (TextView) findViewById(R.id.tv_title2_title);
				break;
			case "6":
				findViewById(R.id.rl_title_1).setVisibility(View.GONE);
				findViewById(R.id.rl_title_2).setVisibility(View.GONE);
				findViewById(R.id.rl_title_3).setVisibility(View.VISIBLE);

				findViewById(R.id.iv_title3_right1).setVisibility(View.VISIBLE);
				findViewById(R.id.iv_title3_right2).setVisibility(View.VISIBLE);

				mTvTitle = (TextView) findViewById(R.id.tv_title3_title);
				break;
			case "7":
				findViewById(R.id.rl_title_1).setVisibility(View.GONE);
				findViewById(R.id.rl_title_2).setVisibility(View.GONE);
				findViewById(R.id.rl_title_3).setVisibility(View.VISIBLE);

				findViewById(R.id.iv_title3_right1).setVisibility(View.GONE);
				findViewById(R.id.iv_title3_right2).setVisibility(View.VISIBLE);
				mTvTitle = (TextView) findViewById(R.id.tv_title3_title);
				break;
			case "8":
				findViewById(R.id.rl_title_1).setVisibility(View.GONE);
				findViewById(R.id.rl_title_2).setVisibility(View.GONE);
				findViewById(R.id.rl_title_3).setVisibility(View.VISIBLE);

				findViewById(R.id.iv_title3_right1).setVisibility(View.GONE);
				findViewById(R.id.iv_title3_right2).setVisibility(View.GONE);
				mTvTitle = (TextView) findViewById(R.id.tv_title3_title);
				break;
			case "9":
				findViewById(R.id.rl_title_1).setVisibility(GONE);
				findViewById(R.id.rl_title_2).setVisibility(GONE);
				findViewById(R.id.rl_title_3).setVisibility(VISIBLE);
				findViewById(R.id.tv_title3_title).setVisibility(GONE);
				findViewById(R.id.ll_title_left).setVisibility(VISIBLE);
				mTvTitle = (TextView) findViewById(R.id.tv_title0_left);

		}
	}

	/*
	 *  set title for title bar
	 * @param title
	 */
	public void setTitle(String title) {
		
		if(mTvTitle == null) {
			return;
		}
		
		if(null == title || "".equals(title)) {
			mTvTitle.setText("");
			return;
		}
		mTvTitle.setText(title);
	}
	
	/**
	 * set onclick to all titlebar view
	 */
	public void setOnClickListener(OnClickListener clickListener) {
		
		findViewById(R.id.iv_title1_left).setOnClickListener(clickListener);
		findViewById(R.id.iv_title2_left).setOnClickListener(clickListener);
		findViewById(R.id.iv_title2_right).setOnClickListener(clickListener);
		findViewById(R.id.tv_title2_right).setOnClickListener(clickListener);
		findViewById(R.id.iv_title3_right1).setOnClickListener(clickListener);
		findViewById(R.id.iv_title3_right2).setOnClickListener(clickListener);
		findViewById(R.id.iv_title0_left).setOnClickListener(clickListener);

	}

	public String getType() {
		return mType;
	}

	@SuppressLint("WrongViewCast")
	public void setColor(String mType, String color){
		Drawable background = null;
		switch (mType){
			case "1":
			case "2":
				findViewById(R.id.rl_title_1).setBackgroundColor(Color.parseColor(color));
				findViewById(R.id.rl_title_1).setBackgroundResource(R.drawable.a007_toolbar_bg);
				background = findViewById(R.id.rl_title_1).getBackground();
				break;
			case "3":
			case "4":
			case "5":
				findViewById(R.id.rl_title_2).setBackgroundColor(Color.parseColor(color));
				findViewById(R.id.rl_title_2).setBackgroundResource(R.drawable.a007_toolbar_bg);
				background = findViewById(R.id.rl_title_2).getBackground();
				break;
			case "6":
			case "7":
			case "8":
			case "9":
				findViewById(R.id.rl_title_3).setBackgroundColor(Color.parseColor(color));
				findViewById(R.id.rl_title_3).setBackgroundResource(R.drawable.a007_toolbar_bg);
				background = findViewById(R.id.rl_title_3).getBackground();
				break;
		}

		if (background instanceof GradientDrawable)
			((GradientDrawable)background).setColor(Color.parseColor(color));


	}

}
