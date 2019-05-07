package com.mymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnUpdate,btnHistory;
    private TextView textViewCurrentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnUpdate=(Button)findViewById(R.id.buttonUpdate);
        btnHistory=(Button)findViewById(R.id.buttonHistory);
        textViewCurrentBalance = findViewById(R.id.textViewCurrentBalance);



        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Activity_History.class));
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Activity_Update.class));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewCurrentBalance.setText("On Hand Balance: $"+new DbHolder().getBalance());
    }
    public void GraphNow(View v){
        startActivity(new Intent(getApplicationContext(), ChartActivity.class));
    }
    public void SearchNow(View v){
        startActivity(new Intent(getApplicationContext(), SummaryActivity.class));
    }
}
