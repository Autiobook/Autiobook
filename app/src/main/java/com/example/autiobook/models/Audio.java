package com.example.autiobook.models;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Audio {
    private File file;
    private String name;

    // empty constructor needed by the Parceler library
    public Audio() {}

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

        //sort newest to oldest
        List<File> fileList = sortFileByDate(files);

        List<Audio> audioBooks = new ArrayList<>();

        //init Audio list
        for(int i=0;i<fileList.size();i++){
            File book = fileList.get(i);
            audioBooks.add(new Audio(book.getName(), book));
        }

        return audioBooks;
    }

    public String getName() {
        return name;
    }

    public static List<File> sortFileByDate(File[] files){
        List<File> fileList = new ArrayList<File>();

        //move files to fileList
        for(int i=0;i<files.length;i++){
            File book = files[i];
            fileList.add(files[i]);
        }

        //sort newest to oldest
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                long k = file1.lastModified() - file2.lastModified();
                if(k > 0){
                    return 1;
                }else if(k == 0){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
        return fileList;
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
