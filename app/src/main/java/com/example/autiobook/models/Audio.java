package com.example.autiobook.models;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Audio {
    private File file;
    private String name;

    public Audio(String name, File file){
        //remove the .mp3
        this.name = name.substring(0,name.length()-4);
        this.file = file;
    }
    public static List<Audio> getUserAudioBooks(Context context){
        //get dir
        ContextWrapper contextWrapper = new ContextWrapper(context);
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);

        //get files
        File[] files = musicDirectory.listFiles();
        List<Audio> audioBooks = new ArrayList<>();

        //init Audio list
        for(int i=0;i<files.length;i++){
            File book = files[i];
            audioBooks.add(new Audio(book.getName(), book));
        }

        return audioBooks;
    }


    public String getName() {
        return name;
    }

//    public String getContent() {
//        return content;
//    }

//    public String getAudioPath() {
//        return audioPath;
//    }

//    public String getTextPath() {
//        return textPath;
//    }
}
