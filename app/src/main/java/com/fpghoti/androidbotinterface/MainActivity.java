package com.fpghoti.androidbotinterface;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fpghoti.androidbotinterface.bot.ChatBot;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textOut;
    public static ChatBot bot;
    private boolean isLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLoaded = false;
        editText = (EditText) findViewById(R.id.editText);
        loadBot();
    }

    private void loadBot(){
        outputPrint("Now loading the chat bot files...");
        File fileExt = new File(getExternalFilesDir(null).getAbsolutePath() + "/bots");
        if (!fileExt.exists()) {
            try {
                unpackBot();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        bot = new ChatBot(getExternalFilesDir(null).getAbsolutePath());
        isLoaded = true;
        outputPrint("Chat bot files are done loading!");
    }

    private void unpackBot() {
        try {
            ZipInputStream zs = new ZipInputStream(getAssets().open("bots.zip"));
            ZipEntry entry = null;

            String destination = getExternalFilesDir(null).getAbsolutePath() + "/";

            byte[] buffer = new byte[4096];
            int bytes;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    File dir = new File(destination, entry.getName());
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                } else {
                    FileOutputStream file = new FileOutputStream(destination + entry.getName());
                    while ((bytes = zs.read(buffer)) != -1) {
                        file.write(buffer, 0, bytes);
                    }
                    file.close();
                }
            }
            zs.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        textOut = (TextView) findViewById(R.id.output);
        if (message.length() > 0) {
            while(lineCount(textOut.getText().toString()) > 20){
                textOut.setText(removeFirstLine(textOut.getText().toString()));
            }
            textOut.setText(textOut.getText() + "\n" + "You: " + message);
            hideKeyboard();
            editText.getText().clear();

            if(isLoaded) {
                final String finalmsg = bot.chatBot(message);
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                while (lineCount(textOut.getText().toString()) > 20) {
                                    textOut.setText(removeFirstLine(textOut.getText().toString()));
                                }
                                textOut.setText(textOut.getText() + "\n" + "Bot: " + finalmsg);
                            }
                        },
                        2000
                );
            }else{
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                while (lineCount(textOut.getText().toString()) > 20) {
                                    textOut.setText(removeFirstLine(textOut.getText().toString()));
                                }
                                outputPrint("The chat bot is still loading.");
                            }
                        },
                        2000
                );
            }
        }
    }

    public void outputPrint(String input){
        textOut = (TextView) findViewById(R.id.output);
        if (input.length() > 0) {
            while(lineCount(textOut.getText().toString()) > 20){
                textOut.setText(removeFirstLine(textOut.getText().toString()));
            }
            textOut.setText(textOut.getText() + "\n" + input);
        }
    }

    private void hideKeyboard() {
        View v = getCurrentFocus();
        if (v == null) {
            v = new View(this);
        }
        InputMethodManager manager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public int lineCount(String s){
        return s.split("\r\n|\r|\n").length;
    }

    public String removeFirstLine(String s){
        if(lineCount(s) < 2){
            return "";
        }
        String result = "";
        String[] lines = s.split("\r\n|\r|\n");
        for(int i = 1; i < lineCount(s) - 1; i++){
            result += lines[i] + "\n";
        }
        result += lines[lineCount(s) - 1];
        return result;
    }

}
