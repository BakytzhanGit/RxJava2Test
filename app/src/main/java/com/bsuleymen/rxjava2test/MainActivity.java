package com.bsuleymen.rxjava2test;

import android.content.Context;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bsuleymen.rxjava2test.Model.CitiesResponse;
import com.bsuleymen.rxjava2test.Model.DataForList;
import com.bsuleymen.rxjava2test.Model.WeatherForCityResponse;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static junit.framework.Assert.assertTrue;

public class MainActivity extends AppCompatActivity implements RvAdapter.RVAdapterListener{

    private static final String TAG = "rx_log";
    private EditText enteredCity;
    private Retrofit retrofit,weather_retrofit;
    private List<DataForList> city_weather = new ArrayList<>();
    //private List<DataForList> city_weather2 = new ArrayList<>();

    private RecyclerView rv;
    private RvAdapter mRVAdapter;
    LinearLayoutManager llm;
    ResourceSubscriber<String> subscriber;
    private ProgressBar mProgressBar;

    private CompositeDisposable disposables = new CompositeDisposable();
    private DisposableObserver<DataForList> observer;
    private DisposableObserver<DataForList> o1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enteredCity = findViewById(R.id.edit_city);
        rv = findViewById(R.id.list_cities);
        init();
        //initOservable();

        enteredCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            private Timer timer=new Timer();
            private final long DELAY = 300; // milliseconds
            Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
            Runnable workRunnable;
            @Override
            public void afterTextChanged(Editable s) {
                String city = enteredCity.getText().toString();
                disposables.clear();
                Log.e("disposable", "is cleared");
                if(city.length() > 2){
                    handler.removeCallbacks(workRunnable);
                    workRunnable = () -> doSmth(s.toString());
                    handler.postDelayed(workRunnable, 3000 /*delay*/);
                }

            }
            private final void doSmth(String city) {

                mProgressBar.setVisibility(View.VISIBLE);
                initOservable();
                disposables.add(observer);
                Log.e("disposable", "is added");
                getPossibleCities(city)
                        .map(dataForList -> dataForList.getCity())
                        .flatMap(new Function<String, ObservableSource<DataForList>>() {
                            @Override
                            public ObservableSource<DataForList> apply(String s) throws Exception {
                                return getWeather(s);
                            }
                        })
                        .subscribe(observer);
            }
        });

        /*city_weather.add(new DataForList("Алматы"));
        city_weather.add(new DataForList("Алмалыбак"));
        city_weather.add(new DataForList("Алма-Арасан"));*/

        /*Observable o1 = Observable.fromIterable(city_weather)
                .map(dataForList -> dataForList.getCity())
                .flatMap((Function<String, ObservableSource<DataForList>>) s -> getWeather(s));

        o1.subscribe(new DisposableObserver<DataForList>() {
            @Override
            public void onNext(DataForList o) {
                Log.e(TAG, "onNext9 " + o.getCity());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError9 " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete9");
            }
        });*/

    }

    private void initOservable() {
        observer = new DisposableObserver<DataForList>() {
            @Override
            public void onNext(DataForList dataForList) {
                Log.e("observse", "onNext " + observer.isDisposed());
                int position = city_weather.indexOf(new DataForList(dataForList.getCity()));
                Log.e(TAG, "onNext " + dataForList.getTemperature() + " " + dataForList.getCity() + " position: " + position);
                //city_weather2.add(dataForList);
                if (position == -1){
                    Log.e(TAG,"is returned " + dataForList.getCity());
                    return;
                }

                city_weather.set(position,dataForList);
                mRVAdapter.notifyItemChanged(position);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError" + e.toString());
                Toast.makeText(MainActivity.this, "Произошло ошибка при получении городов" + e.toString(), Toast.LENGTH_LONG).show();
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                int pos = 0;
                for (DataForList data : city_weather){
                                                /*Log.e(TAG, "onComplete City: " + data.getCity() +
                                                        " Temp: " + data.getTemperature() +
                                                " Date: " + data.getDate() + " desc: " + data.getDescription());*/
                    if (data.getTemperature() == null && data.getDescription() == null
                            && data.getDate() == null){
                        data.setDescription("404");
                        mRVAdapter.notifyItemChanged(pos);
                    }
                    pos++;
                }
                Log.e(TAG, "onComplete");
                Log.e("observse", "onComplete " + observer.isDisposed());
                //city_weather2.addAll(city_weather);
                //mRVAdapter.notifyDataSetChanged();
            }
        };
    }

    private void init() {
        llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);
        mRVAdapter = new RvAdapter(this, city_weather, this);
        rv.setAdapter(mRVAdapter);

        mProgressBar = findViewById(R.id.main_ProgressBar);
        mProgressBar.setVisibility(View.GONE);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        weather_retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private Observable<DataForList> getPossibleCities(String input) {

        return retrofit.create(API.class).listCities(input,"country:kz", "(cities)", "AIzaSyCH9VP3TQy5I4wJp_TN2WpOdAaGcSPbTec")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<CitiesResponse, ObservableSource<DataForList>>() {
                    @Override
                    public ObservableSource<DataForList> apply(CitiesResponse citiesResponse) throws Exception {
                        if (citiesResponse.getPredictions().size() == 0){
                            Toast.makeText(MainActivity.this, citiesResponse.getStatus() + " \n" + citiesResponse.getError_message(), Toast.LENGTH_LONG).show();
                            mProgressBar.setVisibility(View.GONE);
                            return Observable.never();
                        }
                        //city_weather2.clear();
                        city_weather.clear();
                        mProgressBar.setVisibility(View.GONE);
                        int size = citiesResponse.getPredictions().size();
                        for (int i = 0; i < size; i++){
                            String city_name = citiesResponse.getPredictions().get(i).getStructured_formatting().getMain_text();
                            city_weather.add(new DataForList(city_name));
                            Log.e("rx_log_cities", citiesResponse.getPredictions().get(i).getStructured_formatting().getMain_text());
                        }
                        mRVAdapter.notifyDataSetChanged();
                        return Observable.fromIterable(city_weather);
                    }
                });
    }

    private Observable<DataForList> getWeather(String city){
        return weather_retrofit.create(API.class).getWeather(city,"ru","metric","97d8054c1616a34cdc7ebee9f0093eec")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(Observable.empty())
                .map(weatherForCityResponse -> {
                    String date = "";
                    String description = "";
                    String icon_url = "";
                    String temp = "";
                    String huminity = "";
                    String wind_speed = "";
                    Log.e("rx_log_weather", weatherForCityResponse.getWeather().get(0).getDescription() + " City: " +
                    weatherForCityResponse.getName());
                    int cod = weatherForCityResponse.getCod();
                    Log.e("getweather", cod + "");
                    if (cod == 200){
                        DateFormat dateFormat = new SimpleDateFormat("dd.MM HH:mm");
                        Calendar calendar = Calendar.getInstance();
                        date = dateFormat.format(calendar.getTime());
                        Log.e("date_time", "onNext: " +  date + " City:" + city);
                        description = weatherForCityResponse.getWeather().get(0).getDescription();
                        icon_url = weatherForCityResponse.getWeather().get(0).getIcon();
                        temp = (int)weatherForCityResponse.getMain().getTemp() + "";
                        huminity = weatherForCityResponse.getMain().getHumidity() + "% Осадки";
                        wind_speed = weatherForCityResponse.getWind().getSpeed() + " м/сек Ветра";
                    }
                    return new DataForList(city,description,icon_url,temp,date,huminity,wind_speed);
                });
    }
    @Override
    protected void onDestroy() {
        if (disposables != null && !disposables.isDisposed()) {
            disposables.clear();
        }
        if (observer != null && !observer.isDisposed()) {
            observer.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onCitySelected(WeatherForCityResponse weather) {

    }

    public void startRequest(View view) {


        List a = new ArrayList<String>();
        a.add("a");a.add("b");a.add("c");a.add("d");a.add("e");a.add("f");a.add("g");
        Observable<DataForList> o = getWeather("Шымкент");

        o1 = new DisposableObserver<DataForList>() {
            @Override
            public void onNext(DataForList o) {
                Log.e(TAG, "onNext9 " + o + " " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError9 " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete9 " + Thread.currentThread().getName());
            }
        };
        o.delay(2000,TimeUnit.MILLISECONDS).subscribe(o1);
        Disposable d = o1;
        disposables.add(d);

    }
    static Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                // Do some long running operation
                SystemClock.sleep(2000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }
    public void cancelRequest(View view) {
        disposables.clear();
    }
}
