package com.mymanager;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

public class Activity_Update extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Button btnAdd,btnSpend,editTextDate;
    private DatePickerDialog datePickerDialog = null;
    private EditText editTextAmount,editTextCat;
    private String dateStr = "";
    private TextView textViewCurrentBalance;
    private double balance = 0;

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String day = dayOfMonth + "";
        String month1 = (month + 1) + "";

        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month1.length() == 1) {
            month1 = "0" + month1;
        }
        dateStr = day + "-" + month1 + "-" + year;
        editTextDate.setText(dateStr);
        datePickerDialog = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        textViewCurrentBalance.setText("Current balance: $"+new DbHolder().getBalance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btnAdd=(Button)findViewById(R.id.buttonAdd);
        btnSpend=(Button)findViewById(R.id.buttonSpend);
        editTextDate = findViewById(R.id.editTextDate);
        editTextAmount=(EditText)findViewById(R.id.editTextAmount);
        editTextCat=(EditText)findViewById(R.id.editTextCat);

        textViewCurrentBalance = findViewById(R.id.textViewCurrentBalance);



        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                if (datePickerDialog == null) {
                    datePickerDialog = new DatePickerDialog(Activity_Update.this, Activity_Update.this, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
//                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                    datePickerDialog = null;
                }

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidatorClass.valEdit(Activity_Update.this,editTextAmount,"Amount")
                        && ValidatorClass.valEdit(Activity_Update.this,editTextCat,"Category")) {
                    if(dateStr.length()>0) {
                        Account ub = new Account();

                        final int min = 200;
                        final int max = 999999;
                        final int random = new Random().nextInt((max - min) + 1) + min;

                        ub.setTransId("" + random);
                        ub.setCat(editTextCat.getText().toString());
                        ub.setDate(editTextDate.getText().toString());
                        ub.setType(ConstantClass.added);
                        ub.setAmount(editTextAmount.getText().toString());

                        new DbHolder().saveAccount(ub);

                        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                        editTextAmount.setText("");
                        editTextDate.setText("Choose Date");
                        editTextCat.setText("");
                        textViewCurrentBalance.setText("Current balance: $"+new DbHolder().getBalance());
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidatorClass.valEdit(Activity_Update.this,editTextAmount,"Amount")
                        && ValidatorClass.valEdit(Activity_Update.this,editTextCat,"Category")) {
                    if(dateStr.length()>0) {

                        balance = new DbHolder().getBalance();
                        double amt = Double.parseDouble(editTextAmount.getText().toString());
                        if(amt<=balance) {
                            Account ub = new Account();

                            final int min = 200;
                            final int max = 999999;
                            final int random = new Random().nextInt((max - min) + 1) + min;

                            ub.setTransId("" + random);
                            ub.setCat(editTextCat.getText().toString());
                            ub.setDate(editTextDate.getText().toString());
                            ub.setAmount(editTextAmount.getText().toString());
                            ub.setType(ConstantClass.spent);
                            new DbHolder().saveAccount(ub);

                            Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT).show();
                            editTextAmount.setText("");
                            editTextDate.setText("Choose Date");
                            editTextCat.setText("");
                            textViewCurrentBalance.setText("Current balance: $"+new DbHolder().getBalance());
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "You Have not Sufficient Balance", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

}
