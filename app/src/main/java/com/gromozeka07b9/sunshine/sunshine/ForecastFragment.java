package com.gromozeka07b9.sunshine.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String[] forecastArray = {
                "Понедельник - солнечно - +20/+18",
                "Вторник - солнечно - +20/+18",
                "Среда - солнечно - +20/+18",
                "Четверг - солнечно - +20/+18",
                "Пятница - солнечно - +20/+18",
                "Суббота - солнечно - +20/+18",
                "Воскресенье - солнечно - +20/+18"
        };
        ArrayList<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        ArrayAdapter<String> mForecastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForecast);

        ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);



        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {
        public FetchWeatherTask()
        {

        }
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void...params)
        {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;
            try
            {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043,USA&mode=json&units=metric&cnt=7");
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null)
                {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line=reader.readLine())!=null)
                {
                    buffer.append(line + "\n");
                }
                if(buffer.length()==0)
                {
                    return null;
                }
                forecastJsonStr = buffer.toString();
            }
            catch(IOException e)
            {
                Log.e(LOG_TAG, "Error", e);
            }
            finally {
                if(urlConnection != null)
                {
                    urlConnection.disconnect();
                }
                if(reader!=null)
                {
                    try {
                        reader.close();
                    }
                    catch (final IOException e)
                    {
                        Log.e(LOG_TAG, "Error", e);
                    }

                }
            }
            return null;
        }
    }

}