package eu.epitech.road_buddy_java;

<<<<<<< HEAD
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
=======
    /*
    import android.Manifest;
    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.content.pm.PackageManager;
    import android.media.MediaPlayer;
    import android.media.MediaRecorder;
    import android.os.Bundle;
    import android.os.Environment;
    import android.util.Log;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.LinearLayout;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;

    import java.io.IOException;

    public class MainActivity extends AppCompatActivity {

        public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
        private static final String LOG_TAG = "AudioRecordTest";
        private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
        private static String fileName = null;

        private RecordButton recordButton = null;
        private MediaRecorder recorder = null;

        private PlayButton   playButton = null;
        private MediaPlayer   player = null;

        // Requesting permission to RECORD_AUDIO
        private boolean permissionToRecordAccepted = false;
        private String [] permissions = {Manifest.permission.RECORD_AUDIO};

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode){
                case REQUEST_RECORD_AUDIO_PERMISSION:
                    permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
            }
            if (!permissionToRecordAccepted ) finish();

        }

        private void onRecord(boolean start) {
            if (start) {
                startRecording();
            } else {
                stopRecording();
            }
        }

        private void onPlay(boolean start) {
            if (start) {
                startPlaying();
            } else {
                stopPlaying();
            }
        }

        private void startPlaying() {
            player = new MediaPlayer();
            try {
                player.setDataSource(fileName);
                player.prepare();
                player.start();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        }

        private void stopPlaying() {
            player.release();
            player = null;
        }

        private void startRecording() {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setOutputFile(fileName);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }

            recorder.start();
        }

        private void stopRecording() {
            recorder.stop();
            recorder.release();
            recorder = null;
        }

        @SuppressLint("AppCompatCustomView")
        class RecordButton extends Button {
            boolean mStartRecording = true;

            OnClickListener clicker = new OnClickListener() {
                public void onClick(View v) {
                    onRecord(mStartRecording);
                    if (mStartRecording) {
                        setText("Stop recording");
                    } else {
                        setText("Start recording");
                    }
                    mStartRecording = !mStartRecording;
                }
            };

            public RecordButton(Context ctx) {
                super(ctx);
                setText("Start recording");
                setOnClickListener(clicker);
            }
        }

        @SuppressLint("AppCompatCustomView")
        class PlayButton extends Button {
            boolean mStartPlaying = true;

            OnClickListener clicker = new OnClickListener() {
                public void onClick(View v) {
                    onPlay(mStartPlaying);
                    if (mStartPlaying) {
                        setText("Stop playing");
                    } else {
                        setText("Start playing");
                    }
                    mStartPlaying = !mStartPlaying;
                }
            };

            public PlayButton(Context ctx) {
                super(ctx);
                setText("Start playing");
                setOnClickListener(clicker);
            }
        }

        @Override
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);

            // Record to the external cache directory for visibility
            //fileName = getExternalCacheDir().getAbsolutePath();
            //fileName += "/audiorecordtest.3gp";

            fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            fileName += "/recordings.3gp";

            ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

            LinearLayout ll = new LinearLayout(this);
            recordButton = new RecordButton(this);
            ll.addView(recordButton,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0));
            playButton = new PlayButton(this);
            ll.addView(playButton,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            0));
            setContentView(ll);
        }

        @Override
        public void onStop() {
            super.onStop();
            if (recorder != null) {
                recorder.release();
                recorder = null;
            }

            if (player != null) {
                player.release();
                player = null;
            }
        }
    }
    */


import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
>>>>>>> d21565d7370c8da57709228959ab252dbb747ec7
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
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
=======
import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//import android.support.v7.app.AppCompatActivity;

>>>>>>> d21565d7370c8da57709228959ab252dbb747ec7

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
<<<<<<< HEAD
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
=======
    //private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    //private Button play, stop, record;
    //private MediaRecorder myAudioRecorder;
    //private String outputFile;
    private TextView mTextTv;
    private ImageButton mButtonSpeak;
    private TextToSpeech mTTs;
    private EditText mEditText;
    private ImageButton mButtonListen;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    private RequestQueue Requestqueue;

        /*
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode){
                case REQUEST_RECORD_AUDIO_PERMISSION:
                    permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
            }
            if (!permissionToRecordAccepted ) finish();
        }

        private void manageBtnClick() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_RECORD_AUDIO_PERMISSION);
            } else {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_LONG).show();
            }
        }
        */

    private void speak() {
>>>>>>> d21565d7370c8da57709228959ab252dbb747ec7
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
<<<<<<< HEAD
        try {
            waveView.speechStarted();
            startActivityForResult(intent, CODE);
=======
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi, I am your buddy! Let's talk!");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
>>>>>>> d21565d7370c8da57709228959ab252dbb747ec7
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

<<<<<<< HEAD
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

=======
    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }

    class RetrievePostTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String ...strings) {
            try {
                URL url = null;
                try {
                    url = new URL("https://dr53cfyaka.execute-api.us-2.amazonaws.com/api/submit-conversation");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream is = null;

                int response = conn.getResponseCode();
                System.out.println("The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = convertInputStreamToString(is, length);
                return contentAsString;
                int response = conn.getResponseCode();
                String jsonInputString = "{\"objective\": \"" + strings[0] + "\", \"title\": \"Golden Gate bridge\"}";
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println(response.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String body = null;
                try {
                    body = jsonObject.getJSONObject("body").getString("body");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return body;
            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }
    }


>>>>>>> d21565d7370c8da57709228959ab252dbb747ec7
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
<<<<<<< HEAD
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
=======
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTextTv.setText(result.get(0));

                    String[] trigger_words = new String[]{"joke", "enumerate", "funfact"};

                    for (int i = 0; i < trigger_words.length; i++)
                        if (result.get(0).contains(trigger_words[i])) {

                            final String obj = trigger_words[i];
                            String body = null;
                            try {
                                body = new RetrievePostTask().execute(obj).get(10000, TimeUnit.MILLISECONDS);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (TimeoutException e) {
                                e.printStackTrace();
                            }
                            System.out.println(body);
                            listenJson(body);
                        }
>>>>>>> d21565d7370c8da57709228959ab252dbb747ec7
                }
            }
        }
    }

<<<<<<< HEAD
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

=======
    private void listen() {
        String text = mEditText.getText().toString();
        float pitch = (float) 0.75;
        float rate = (float) 0.75;
        mTTs.setPitch(pitch);
        mTTs.setSpeechRate(rate);

        mTTs.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void listenJson(String text) {
        float pitch = (float) 0.75;
        float rate = (float) 0.75;
        mTTs.setPitch(pitch);
        mTTs.setSpeechRate(rate);

        mTTs.speak(text, TextToSpeech.QUEUE_FLUSH, null);
>>>>>>> d21565d7370c8da57709228959ab252dbb747ec7
    }

    @Override
    protected void onDestroy() {
        if (mTTs != null) {
            mTTs.stop();
            mTTs.shutdown();
        }
<<<<<<< HEAD
        super.onDestroy();
    }

//    @Override
//    public void onReadyForSpeech(Bundle arg0) {
//        Log.i(LOG_TAG, "onReadyForSpeech");
//    }
}
=======

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //play = (Button) findViewById(R.id.play);
        //stop = (Button) findViewById(R.id.stop);
        //record = (Button) findViewById(R.id.record);

        mButtonSpeak = findViewById(R.id.speak);
        mTextTv = findViewById(R.id.textTv);

        mButtonListen = findViewById(R.id.listen);
        mEditText = findViewById(R.id.read);


        //stop.setEnabled(false);
        //play.setEnabled(false);

        //outputFile = getExternalCacheDir().getAbsolutePath();
        //outputFile += "/audiorecordtest.3gp";
        //myAudioRecorder = new MediaRecorder();
        //myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        //myAudioRecorder.setOutputFile(outputFile);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(Locale.getDefault());

                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported.");

                    } else {
                        mButtonListen.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed.");
                }
            }
        });

        mButtonListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen();
                Toast.makeText(getApplicationContext(), "Reading...", Toast.LENGTH_SHORT).show();
            }
        });

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
                    /*
                    try {
                        if (myAudioRecorder == null) {
                            outputFile = getExternalCacheDir().getAbsolutePath();
                            outputFile += "/audiorecordtest.3gp";
                            myAudioRecorder = new MediaRecorder();
                            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                            myAudioRecorder.setOutputFile(outputFile);
                        }
                        myAudioRecorder.prepare();
                        myAudioRecorder.start();
                    } catch (IllegalStateException ise) {
                        // make something ...
                    } catch (IOException ioe) {
                        // make something
                    }
                    record.setEnabled(false);
                    stop.setEnabled(true);
                    */
                Toast.makeText(getApplicationContext(), "Listening...", Toast.LENGTH_SHORT).show();
            }
        });

            /*
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //manageBtnClick();

                    myAudioRecorder.stop();
                    myAudioRecorder.release();
                    myAudioRecorder = null;
                    record.setEnabled(true);
                    stop.setEnabled(false);
                    play.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Audio Recorded successfully", Toast.LENGTH_LONG).show();
                }
            });

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(outputFile);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        // make something
                    }
                }
            });
            */
    }
}


    /*
    public class MainActivity extends AppCompatActivity {

        public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

        MediaRecorder mediaRecorder;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);



            mediaRecorder = new MediaRecorder();
        }

        public void sendMessage(View view) {
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            EditText editText = (EditText) findViewById(R.id.editText);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }

        public void startRecording(View view) {

            try {
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS);

                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(path, "/recordings");

                mediaRecorder.setOutputFile(file);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

                mediaRecorder.prepare();
                mediaRecorder.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stopRecording(View view) {
            mediaRecorder.stop();
        }
    }
    */


>>>>>>> d21565d7370c8da57709228959ab252dbb747ec7
