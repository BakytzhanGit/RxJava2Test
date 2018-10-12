package com.bsuleymen.rxjava2test;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bsuleymen.rxjava2test.Model.DataForList;
import com.bsuleymen.rxjava2test.Model.WeatherForCityResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.CityWeatherViewHolder> {

    private List<DataForList>cityWeather;
    private Context context;
    private RVAdapterListener listener;

    public RvAdapter(Context context, List<DataForList> list, RVAdapterListener listener){
        this.context = context;
        this.listener = listener;
        this.cityWeather = list;
    };

    public static class CityWeatherViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        ProgressBar progressBar;
        TableLayout tableLayout;
        TextView dateTime_Desc;
        TextView temperature;
        ImageView image;
        TextView city;
        TextView wind_desc;
        TextView humidity;

        public CityWeatherViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.CardView_city);
            progressBar = itemView.findViewById(R.id.progressBar);
            tableLayout = itemView.findViewById(R.id.table);
            image = itemView.findViewById(R.id.weather_icon);

            dateTime_Desc = itemView.findViewById(R.id.dateTime_Desc);
            temperature = itemView.findViewById(R.id.temperature);
            wind_desc = itemView.findViewById(R.id.wind_desc);
            city = itemView.findViewById(R.id.name_city);
            humidity = itemView.findViewById(R.id.humidity);
        }
    }

    @Override
    public void onBindViewHolder(CityWeatherViewHolder holder, int position) {
        holder.tableLayout.setVisibility(View.GONE);
        holder.city.setText(cityWeather.get(position).getCity());

        if (cityWeather.get(position).getTemperature() != null){
            holder.tableLayout.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);
            holder.dateTime_Desc.setText(cityWeather.get(position).getDate() + ", " + cityWeather.get(position).getDescription());
            holder.temperature.setText(cityWeather.get(position).getTemperature() + "\u00b0");
            holder.wind_desc.setText(cityWeather.get(position).getWind_speed());

            holder.humidity.setText(cityWeather.get(position).getHumidity());

            if (cityWeather.get(position).getIcon_url() != null){
                Picasso.get()
                        .load("http://openweathermap.org/img/w/" + cityWeather.get(position).getIcon_url() +".png" )
                        .fit()
                        .into(holder.image);
            }
        }
        else if( cityWeather.get(position).getDescription() == "404"){
            holder.dateTime_Desc.setText("По этому городу данные не найдены");
            holder.progressBar.setVisibility(View.GONE);

        }
        else {
            holder.progressBar.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return cityWeather.size();
    }

    @Override
    public CityWeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_cities_layout, viewGroup, false);
        return new CityWeatherViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface RVAdapterListener {
        void onCitySelected(WeatherForCityResponse weather);
    }
}
