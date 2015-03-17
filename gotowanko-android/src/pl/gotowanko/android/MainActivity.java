package pl.gotowanko.android;

import pl.gotowanko.android.base.GotowankoActivity;
import pl.gotowanko.android.base.GotowankoApplication;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends GotowankoActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		final EditText searchView = (EditText) findViewById(R.id.searchEditText);

		ListView mainLayout = (ListView) findViewById(R.id.trainingList);
		final TrainingAdapter adapter = new TrainingAdapter(GotowankoApplication.getTrainingDAO());
		mainLayout.setAdapter(adapter);
		searchView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String stringToSearch = searchView.getText().toString();
				adapter.searchedText(stringToSearch);
			}
		});

	}
}
