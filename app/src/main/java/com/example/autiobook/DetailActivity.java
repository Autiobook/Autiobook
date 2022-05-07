package com.example.autiobook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";
    TextView tvName;
    TextView tvContent;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvName = findViewById(R.id.tvName);
        tvContent = findViewById(R.id.tvContent);

        tvName.setText("**TODO**");
        tvContent.setText("Add media player here to play mp3 audio file");

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=textToSpeech.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        ConvertTextToSpeech();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });
    }

    private void ConvertTextToSpeech() {
        String text = tvContent.getText().toString();
        if(text==null||"".equals(text))
        {
            text = "Content not available";
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }else{
//            textToSpeech.speak(text+"is saved", TextToSpeech.QUEUE_FLUSH, null);
//            textToSpeech.addSpeech("Test mp3");
        }
    }

}