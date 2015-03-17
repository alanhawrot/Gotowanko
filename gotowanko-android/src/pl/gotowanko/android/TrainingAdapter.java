package pl.gotowanko.android;

import pl.gotowanko.android.db.dao.TrainingDAO;
import pl.gotowanko.android.db.entity.Training;
import pl.gotowanko.android.tools.PhotoUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TrainingAdapter extends BaseAdapter {

	private TrainingDAO trainingDAO;
	private String stringToSearch = "";

	public TrainingAdapter(TrainingDAO trainingDAO) {
		this.trainingDAO = trainingDAO;
	}

	@Override
	public int getCount() {
		return trainingDAO.filter(stringToSearch).size();
	}

	@Override
	public Object getItem(int position) {
		return trainingDAO.filter(stringToSearch).get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Context context = parent.getContext();
		Training t = trainingDAO.filter(stringToSearch).get(position);
		LinearLayout l = new LinearLayout(context);
		l.setTag(t.getName());
		l.setPadding(10, 10, 10, 10);
		l.setGravity(Gravity.CENTER_VERTICAL);
		ImageView im = new ImageView(context);
		PhotoUtils photUtils = new PhotoUtils(context);
		im.setImageDrawable(photUtils.getDrawableByKeyOrPath(t.getImage()));
		TextView v = new TextView(context);
		v.setPadding(30, 10, 10, 10);
		v.setText(t.getName());
		v.setTextSize(16);
		v.setTypeface(null, Typeface.BOLD);
		l.addView(im);
		l.addView(v);
		l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String trainingName = (String) v.getTag();
				Intent intent = new Intent(context, TrainingActivity.class);
				intent.putExtra("TrainingName", trainingName);
				context.startActivity(intent);
			}
		});
		return l;

	}

	public void searchedText(String stringToSearch) {
		this.stringToSearch = stringToSearch;
		this.notifyDataSetChanged();

	}

}
