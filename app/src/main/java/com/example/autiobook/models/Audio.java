package com.example.autiobook.models;

import java.util.ArrayList;
import java.util.List;

public class Audio {
    private String textPath;
    private String audioPath;
    private String name;
    private String content;

    public Audio(String textPath, String audioPath, String name, String content){
        this.textPath = textPath;
        this.audioPath = audioPath;
        this.name = name;
        this.content = content;
    }
    public static List<Audio> getUserAudioBooks(){
//        ** TO DO **
//        1. grab user mp3 files from raw storage
//        2. for each audio put the metadata into a new Audio()
//        3. return the list of audios
        return new ArrayList<Audio>();
    }


    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public String getTextPath() {
        return textPath;
    }
}
