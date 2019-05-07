package com.mymanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class SummaryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView textViewCurrentBalance;

    private Button startDate,endDate;
    private DatePickerDialog datePickerDialog = null;
    private int rtype = 0;
    private String from = "",to = "";


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
        if(rtype==0) {
            from = day + "-" + month1 + "-" + year;
            startDate.setText(from);
        }
        else{
            to = day + "-" + month1 + "-" + year;
            endDate.setText(to);
        }
        datePickerDialog = null;
    }

    double total = 0;
    double add = 0,minus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        textViewCurrentBalance=(TextView)findViewById(R.id.textViewCurrentBalance);
        startDate=findViewById(R.id.startDate);
        endDate=findViewById(R.id.endDate);

        List<Account> accounts= new DbHolder().getAccounts();

        printTable(accounts);
        textViewCurrentBalance.setText("On Hand Balance: $"+total);


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                if (datePickerDialog == null) {
                    rtype = 0;
                    datePickerDialog = new DatePickerDialog(SummaryActivity.this, SummaryActivity.this, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));

//                    if(to.length()>0){
//                        datePickerDialog.getDatePicker().setMaxDate(ConstantClass.getTimeMillies(to) - 1000);
//                    }

//                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                    datePickerDialog = null;
                }
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                if (datePickerDialog == null) {
                    rtype = 1;
                    datePickerDialog = new DatePickerDialog(SummaryActivity.this, SummaryActivity.this, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));

//                    if(from.length()>0){
//                        datePickerDialog.getDatePicker().setMinDate(ConstantClass.getTimeMillies(from) - 1000);
//                    }

//                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                } else {
                    datePickerDialog.dismiss();
                    datePickerDialog = null;
                }
            }
        });
    }


    public void ResetNow(View v){
        try {
            from = "";
            to = "";
            startDate.setText("From Date");
            endDate.setText("To Date");

            List<Account> accounts= new DbHolder().getAccounts();
            if(accounts.size()>0){
                printTable(accounts);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SearchNow(View v){
        try{
            if(from.length()>0) {
                if(to.length()>0) {
                    List<Account> accounts= new DbHolder().SerachAcc(from,to);
                    if(accounts.size()>0){
                        printTable(accounts);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Select End Date", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Please Select Start Date", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printTable(List<Account> list)
    {
        TableLayout tableLayout = (TableLayout)findViewById(R.id.timetable);
        tableLayout.removeAllViews();

        LayoutInflater inflater = (LayoutInflater)getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view = inflater.inflate(R.layout.table_row,null);

//        TableRow header = new TableRow(v.getContext());
        view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        TextView trn = (TextView) view.findViewById(R.id.col1);
        trn.setText("Date");
        TextView arr = (TextView) view.findViewById(R.id.txt_arr);
        arr.setText("Amount");
        TextView dep = (TextView) view.findViewById(R.id.txt_dep);
        dep.setText("Cat");

        trn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        arr.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        dep.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        trn.setTextColor(Color.BLACK);
        arr.setTextColor(Color.BLACK);
        dep.setTextColor(Color.BLACK);

        tableLayout.addView(view);

        Account accountBean;
        int i=0;
        for (Account ab:list){
            {
                view = inflater.inflate(R.layout.table_row,null);
//            row = new TableRow(v.getContext());
                trn = (TextView) view.findViewById(R.id.col1);
                arr = (TextView) view.findViewById(R.id.txt_arr);
                dep = (TextView) view.findViewById(R.id.txt_dep);
                i++;
                trn.setText(ab.getDate());
                arr.setText(ab.getAmount());
                dep.setText(ab.getCat());
//
                if (ab.getType().equals(ConstantClass.added))
                {
                    add+=Double.parseDouble(ab.getAmount());
                    trn.setTextColor(Color.MAGENTA);
                    arr.setTextColor(Color.MAGENTA);
                    dep.setTextColor(Color.MAGENTA);
                }else {
                    minus+=Double.parseDouble(ab.getAmount());
                    trn.setTextColor(Color.YELLOW);
                    arr.setTextColor(Color.YELLOW);
                    dep.setTextColor(Color.YELLOW);
                }

                tableLayout.addView(view);
            }
        }

        total = add-minus;
    }

}
