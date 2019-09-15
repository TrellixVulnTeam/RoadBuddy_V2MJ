package eu.epitech.road_buddy_java;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.dnkilic.waveform.WaveView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private static final int REQUEST_CODE_INITIALIZE_BUDDY = 1000;
    private static final int REQUEST_CODE_CHOOSE_ACTIVITY = 1001;
    private static final int REQUEST_CODE_START_ACTIVITY = 1002;
    private TextView mTextTv;
    private ImageButton mButtonSpeak;
    private ProgressBar progressBar;
    private TextToSpeech mTTs;
    public ArrayList<String> result;
    WaveView waveView;
    private SpeechRecognizer speech;
    private String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSpeak = findViewById(R.id.speak);
        mTextTv = findViewById(R.id.textTv);
        waveView = findViewById(R.id.waveView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
//        speech.setRecognitionListener(this);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(Locale.getDefault());
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        android.util.Log.e("TTS", "Language not supported.");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    android.util.Log.e("TTS", "Initialization failed.");
                }
            }
        });

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read("Hi there! I am your road buddy! ");//I can feel drowsiness here. Let's get your attention up and running?");
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                waveView.initialize(dm);
                listen(REQUEST_CODE_INITIALIZE_BUDDY);
            }
        });
    }

    private void listen(int CODE) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            waveView.speechStarted();
            startActivityForResult(intent, CODE);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPositive(@NotNull String response) {
        return (response.toLowerCase().contains("yes") || response.toLowerCase().contains("sure") || response.toLowerCase().contains("absolutely"));
    }

    private boolean isNegative(@NotNull String response) {
        return (response.toLowerCase().contains("no") || response.toLowerCase().contains("fuck off"));
    }

    protected void sendRequest(String objective, String title) {
        RequestQueue requestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
        // Start the queue
        requestQueue.start();
        String url = String.format("http://general-api.quxttbjkp2.us-east-2.elasticbeanstalk.com/exchange?" +
                "objective=%1$s&title=%2$s", objective, title);
        Log.d("get", "toto");

        StringRequest jsonOblect = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject json = null;
                try {
                    json = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    read(json.getString("body"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("tptp", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tptp", "error");
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(jsonOblect);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_INITIALIZE_BUDDY: {
                if (resultCode == RESULT_OK && null != data) {
                    waveView.speechPaused();
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTextTv.setText(result.get(0));

                    if (isPositive(result.get(0))) {
                        read("Ok, great! What would you like to do?");
                        listen(REQUEST_CODE_CHOOSE_ACTIVITY);
                    } else if (isNegative(result.get(0))) {
                        read("Okay, no problem! I'll let you drive for now then.");
                    } else {
                        read("Sorry, I am not sure I understand your answer. Can you repeat please?");
                        listen(REQUEST_CODE_INITIALIZE_BUDDY);
                    }
                }
            }
            case REQUEST_CODE_CHOOSE_ACTIVITY: {
                if (resultCode == RESULT_OK && null != data) {
                    result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTextTv.setText(result.get(0));
                    String sentence = result.get(0);
                    if (sentence.toLowerCase().contains("joke"))
                        sendRequest("joke", "blabla");
                    else if (sentence.toLowerCase().contains("call"))
                        sendRequest("describe", "Golden gate bridge");
                }
            }
        }
    }

    public void read(String text) {
        float pitch = (float) 0.75;
        float rate = (float) 0.9;
        mTTs.setPitch(pitch);
        mTTs.setSpeechRate(rate);
        mTTs.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        boolean speakingEnd = mTTs.isSpeaking();
        do{
            speakingEnd = mTTs.isSpeaking();
        } while (speakingEnd);

    }

    @Override
    protected void onDestroy() {
        if (mTTs != null) {
            mTTs.stop();
            mTTs.shutdown();
        }
        super.onDestroy();
    }

//    @Override
//    public void onReadyForSpeech(Bundle arg0) {
//        Log.i(LOG_TAG, "onReadyForSpeech");
//    }
}