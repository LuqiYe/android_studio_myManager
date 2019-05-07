package com.mymanager;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ValidatorClass {

    public static boolean valEdit1(Activity activity, EditText editText, TextView textView, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("Please Enter" + " " + name);
                return false;
            } else {
                return true;
            }
        }
        else{
            textView.setVisibility(View.VISIBLE);
            textView.setText("Please Enter" + " " + name);
            editText.requestFocus();
            return false;
        }
    }
    public static boolean valEdit(Activity activity, EditText editText, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean valEditPassword1(Activity activity, EditText editText, TextView textView, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("Please Enter" + " " + name);
                return false;
            } else {
                if (editText.getText().toString().length() > 5) {
                    return true;
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Password Should Have 6 Char");
                    return false;
                }
            }
        }
        else{
            textView.setVisibility(View.VISIBLE);
            textView.setText("Please Enter" + " " + name);
            return false;
        }
    }
    public static boolean valEditPassword(Activity activity, EditText editText, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (editText.getText().toString().length() > 5) {
                    return true;
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Password Should Have 6 Char", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean valEditEmail(Activity activity, EditText editText, TextView textView, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                textView.setVisibility(View.VISIBLE);
                textView.setText("Please Enter" + " " + name);
                return false;
            } else {
                if (editText.getText().toString().contains("@") && editText.getText().toString().contains(".")) {
                    return true;
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Invalid Email");
                    return false;
                }
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean valEditEmail(Activity activity, EditText editText, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (editText.getText().toString().contains("@") && editText.getText().toString().contains(".")) {
                    return true;
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public static boolean valEditMob(Activity activity, EditText editText, String name)
    {
        if(editText!=null && activity!=null && name!=null) {
            if (editText.getText().toString().length() == 0) {
                Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (editText.getText().toString().length() <=12) {
                    String str = editText.getText().toString();
                    boolean valid = false;
                    for (int i = 0; i < str.length(); i++) {
                        try {
                            int x = Integer.parseInt(str.charAt(i) + "");
                            valid = true;
                        } catch (Exception e) {
                            Toast.makeText(activity.getApplicationContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                    if (valid) {
                        return true;
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(activity.getApplicationContext(), "Invalid Mobile", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        else{
            Toast.makeText(activity.getApplicationContext(), "Please Enter" + " " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
