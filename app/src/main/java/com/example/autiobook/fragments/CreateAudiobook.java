package com.example.autiobook.fragments;

import static com.parse.Parse.getApplicationContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autiobook.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Objects;


public class CreateAudiobook extends Fragment {

    static final int PICK_TEXT_FILE = 8000;
    public static final String TAG = "CreateAudio";
    Uri chosenFile = null;

    EditText tvTitle;
    Button btnUpload;
    TextView tvPreview;
    Button btnCreate;
    String book;
    TextToSpeech textToSpeech;

    public CreateAudiobook() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_audiobook, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = requireView().findViewById(R.id.etTitle);
        btnUpload = requireView().findViewById(R.id.btnUpload);
        tvPreview = requireView().findViewById(R.id.tvPreview);
        btnCreate = requireView().findViewById(R.id.btnCreate);

        btnUpload.setOnClickListener(e -> openFile());
//        deleteAllAudios();
        //init tts and onclick for btnCreate
        InitializeTTS();
    }

    // I do not care that this is deprecated this way is easier than the new one.
    @SuppressWarnings("deprecation")
    @SuppressLint("SetTextI18n")
    private void openFile() {
        tvPreview.setText("Loading...");
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("text/plain");

        startActivityForResult(i, PICK_TEXT_FILE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_TEXT_FILE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                chosenFile = data.getData();
            }

            try {
                if(chosenFile != null) {
                    book = readTextFromUri(chosenFile);
                    tvPreview.setText(book);
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream =
                     requireContext().getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line + "\n";
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    private void InitializeTTS(){
        Log.i(TAG, "Intializing TTS");
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result=textToSpeech.setLanguage(Locale.US);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e(TAG, "This Language is not supported");
                    }
                    else{
                        btnCreate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i(TAG, "onClick btnCreate");
                                ConvertTextToSpeech();
                            }
                        });
                    }
                }
                else
                    Log.e(TAG, "Initilization Failed!");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void ConvertTextToSpeech() {
        String title = tvTitle.getText().toString();

        if(title==null||"".equals(title)){
            Log.i(TAG, "title is empty!");
            Toast.makeText(getApplicationContext(), "Please enter a book title!", Toast.LENGTH_LONG).show();
            return;
        }
        else if(book==null||"".equals(book)){
            Log.i(TAG, "book is empty!");
            Toast.makeText(getApplicationContext(), "Please upload a non-empty book!", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            // get audio directory
            ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
            File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);

            // get audio by name
            File file = new File(musicDirectory,title + ".mp3");

            // create mp3 file
            if(file.exists()){
                Log.i(TAG, "File already exists");
                Toast.makeText(getApplicationContext(),"Book name already taken!", Toast.LENGTH_LONG).show();
                return;
            }else{
                //syntehsize with tts
                String utteranceId = title;
                Log.i(TAG, book);
                textToSpeech.synthesizeToFile(book, null, file, utteranceId);
                textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {
                        Log.i(TAG, "onStart synthesize\n"+s);
                    }

                    @Override
                    public void onDone(String s) {
                        Log.i(TAG, "successfully synthesized book\n"+s);
                        Toast.makeText(getApplicationContext(), "Audio book created", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String s) {
                        Log.e(TAG, "error with synthesizing book\n"+s);
                    }
                });
            }
        }
    }

    //for testing only
    private void deleteAllAudios(){
        // get audio directory
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);

        File dir = musicDirectory;
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
    }
}