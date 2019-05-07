package com.mymanager;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.List;

public class Activity_History extends AppCompatActivity {
    TextView textViewCurrentBalance;
    double total;

    double add = 0,minus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        textViewCurrentBalance=(TextView)findViewById(R.id.textViewCurrentBalance);

        List<Account> accounts= new DbHolder().getAccounts();

        printTable(accounts);
        textViewCurrentBalance.setText("On Hand balance: $"+total);
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

        trn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        arr.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        dep.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
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

            total = add-minus;
        }}
}
