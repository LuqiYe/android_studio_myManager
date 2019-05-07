package com.mymanager;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView textViewCurrentBalance,added_txt,spent_txt;
    double total = 0;
    PieChart chart1;
    private Button startDate,endDate;
    private DatePickerDialog datePickerDialog = null;
    private int rtype = 0;
    private String from = "",to = "";


    double add = 0,minus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        textViewCurrentBalance=(TextView)findViewById(R.id.textViewCurrentBalance);
        added_txt=(TextView)findViewById(R.id.added_txt);
        spent_txt=(TextView)findViewById(R.id.spent_txt);
        startDate=findViewById(R.id.startDate);
        endDate=findViewById(R.id.endDate);

        chart1 = findViewById(R.id.chart1);
        initChart();

        List<Account> accounts= new DbHolder().getAccounts();

        calculateNow(accounts);


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                if (datePickerDialog == null) {
                    rtype = 0;
                    datePickerDialog = new DatePickerDialog(ChartActivity.this, ChartActivity.this, myCalendar
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
                    datePickerDialog = new DatePickerDialog(ChartActivity.this, ChartActivity.this, myCalendar
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


    public void SearchNow(View v){
        try{
            if(from.length()>0) {
                if(to.length()>0) {
                    List<Account> accounts= new DbHolder().SerachAcc(from,to);
                    if(accounts.size()>0){
                        calculateNow(accounts);
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

    public void ResetNow(View v){
        try {
            from = "";
            to = "";
            startDate.setText("From Day");
            endDate.setText("To Day");

            List<Account> accounts= new DbHolder().getAccounts();
            if(accounts.size()>0){
                calculateNow(accounts);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initChart() {
        try{
            chart1.setUsePercentValues(true);
            chart1.getDescription().setEnabled(false);
            chart1.setExtraOffsets(5, 10, 5, 5);

            chart1.setDragDecelerationFrictionCoef(0.95f);
            chart1.setRotationAngle(0);
            chart1.setRotationEnabled(true);
            chart1.setHighlightPerTapEnabled(true);

            chart1.animateY(100, Easing.EaseInOutQuad);

            Legend l = chart1.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setYOffset(0f);

            // entry label styling
            chart1.setEntryLabelColor(Color.WHITE);
            chart1.setEntryLabelTextSize(12f);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private void calculateNow(List<Account> accounts) {

        total = 0;
        minus = 0;
        add = 0;


        textViewCurrentBalance.setText("On Hand balance: $"+total);

        for(Account ab:accounts){
            if (ab.getType().equals(ConstantClass.added))
            {
                add+=Double.parseDouble(ab.getAmount());
            }else {
                minus+=Double.parseDouble(ab.getAmount());
            }
        }
        total = add - minus;

        added_txt.setText("Added: $"+add);
        spent_txt.setText("Spend: $"+minus);
        textViewCurrentBalance.setText("On Hand balance: $"+total);

        double perSpent = (minus/add)*100;
        double perRemain = (total/add)*100;

        setData(perSpent,perRemain);

    }
    private void setData(double spent, double added) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        int count = 1;

        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) spent,
                    "Spent",
                    getResources().getDrawable(R.drawable.ic_launcher_background)));
            entries.add(new PieEntry((float) added,
                    "Remain",
                    getResources().getDrawable(R.drawable.ic_launcher_background)));
        }


        PieDataSet dataSet = new PieDataSet(entries, "expense");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(getResources().getColor(R.color.colorAccent));
        colors.add(getResources().getColor(R.color.colorPrimary));


        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart1));
        data.setValueTextSize(7f);
        data.setValueTextColor(Color.BLACK);
        chart1.setData(data);

        // undo all highlights
        chart1.highlightValues(null);

        chart1.invalidate();
        chart1.animateXY(10,10);
    }



    public void onClick(View v){


    }
}
