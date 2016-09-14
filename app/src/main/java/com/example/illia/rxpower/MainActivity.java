package com.example.illia.rxpower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.illia.rxpower.api.ApiService;
import com.example.illia.rxpower.model.WeatherModel;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private TextView tv, tv2, tv3, tv4, tv5, tv6;
    private CompositeSubscription compositeSubscription;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compositeSubscription = new CompositeSubscription();

        tv = (TextView) findViewById(R.id.tv);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        EditText cityLine = (EditText) findViewById(R.id.city);

        compositeSubscription.add(
                RxTextView.textChanges(cityLine)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(cs -> !TextUtils.isEmpty(cs))
                .map(CharSequence::toString)
                .subscribe(this::makeCall)
        );
    }

    private void makeCall(String s) {
        Log.d("### Call ###", ">>>>>>>>>>>>>> call made: " + s);
        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

        compositeSubscription.add(
                ApiService.getService().getWeather(s, ApiService.APP_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setWeather, this::onError)
        );
    }

    private void setWeather(WeatherModel w) {
        progressBar.setVisibility(View.GONE);

        tv.setText(w.weather.get(0).main);
        tv2.setText(w.weather.get(0).description);
        tv3.setText("temp " + w.main.temp);
        tv4.setText(w.name);
        tv5.setText("humidity " + w.main.humidity);
        tv6.setText("grnd_level " + w.main.grnd_level);
    }

    private void onError(Throwable t) {
        progressBar.setVisibility(View.GONE);

        Toast.makeText(this, "YOU LOOSE " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
