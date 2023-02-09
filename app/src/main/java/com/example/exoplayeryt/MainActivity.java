package com.example.exoplayeryt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText textInputEditText;
    MaterialButton materialButton;
    String link;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textInputEditText = findViewById(R.id.edit_text);
        materialButton = findViewById(R.id.submitBtn);

        textInputEditText.getText().toString();

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                link = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                link = s.toString();
            }
        });


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ExoPlayerActivity.class);
//                intent.putExtra("link", link);
                intent.putExtra("link", "https://www.youtube.com/watch?v=sacPbmVSkrg");
                intent.putExtra("quality", "best");
                intent.putExtra("item", 4);
                intent.putExtra("position", "10");
                startActivity(intent);

            }
        });



    }
}