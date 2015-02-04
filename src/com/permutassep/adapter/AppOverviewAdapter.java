package com.permutassep.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.permutassep.R;

public class AppOverviewAdapter extends PagerAdapter {

	private Context context;

	private int[] imagesArray = new int[] {
			R.drawable.app_overview_1_buy,
			R.drawable.app_overview_2_smartphone,
			R.drawable.app_overview_3_save_money,
			R.drawable.app_overview_4_coupons };

	public AppOverviewAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return imagesArray.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi = inflater.inflate(R.layout.activity_app_overview_vp, null);

		ImageView imageView = (ImageView) vi
				.findViewById(R.id.ivViewPagerImageView);
		imageView.setImageResource(imagesArray[position]);

		TextView tv = (TextView) vi.findViewById(R.id.tvViewPagerTextView);
		switch (position) {
		case 0:
			tv.setText(context.getString(R.string.app_overview_1st_item_text));
			break;
		case 1:
			tv.setText(context.getString(R.string.app_overview_2nd_item_text));
			break;
		case 2:
			tv.setText(context.getString(R.string.app_overview_3rd_item_text));
			break;
		case 3:
			tv.setText(context.getString(R.string.app_overview_4th_item_text));
			break;
		}

		((ViewPager) container).addView(vi, 0);

		return vi;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}

}