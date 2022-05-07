package com.example.autiobook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;


public class CreateAudiobook extends Fragment {

    static final int PICK_TEXT_FILE = 8000;

    Uri chosenFile = null;

    EditText tvTitle;
    Button btnUpload;
    TextView tvPreview;
    Button btnCreate;
    String book;

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

}