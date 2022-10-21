package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button2);
        TextView text = findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] cmd = text.getText().toString().split(" ");

                try {
                    // These two lines are what we care about
                    Process process = Runtime.getRuntime().exec(cmd);
                    InputStream iStream = process.getInputStream();

                    // This is how we check whether it works
                    tryWriteProcessOutput(iStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void tryWriteProcessOutput(InputStream iStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));

        String output = "";
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                output += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        Log.d("cmdOutput", output);
    }
}