package com.example.trackit;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemoryData {
    public static void saveData(String data, Context context){
        try{
             FileOutputStream fileOutputStream=context.openFileOutput("data.txt",context.MODE_PRIVATE);
             fileOutputStream.write(data.getBytes());
             fileOutputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public  static  String getData(Context context){
        String data="";
        try{
            FileInputStream fis=context.openFileInput("data.txt");
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader bufferedReader=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            data=sb.toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return data;
    }
    public static void savenumber(String data, Context context){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput("number.txt",context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public  static  String getnumber(Context context){
        String data="";
        try{
            FileInputStream fis=context.openFileInput("number.txt");
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader bufferedReader=new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            data=sb.toString();
        }catch(IOException e){
            e.printStackTrace();
        }
        return data;
    }
}
