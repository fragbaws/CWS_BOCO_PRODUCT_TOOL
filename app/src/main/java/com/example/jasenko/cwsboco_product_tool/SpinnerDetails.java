package com.example.jasenko.cwsboco_product_tool;

import android.widget.Spinner;

/**
 * Created by Jasenko on 27/12/2017.
 */

public class SpinnerDetails {

    private  Spinner spinner; // SPINNER ID
    private  String spinnerName; // ITEM CURRENTLY SELECTED IN SPINNER

    public SpinnerDetails(Spinner spinner, String name)
    {
        this.spinner = spinner;
        this.spinnerName = name;
    }

    public void setSpinner(Spinner spinner)
    {
        this.spinner = spinner;
    }
    public void setSpinnerName(String name)
    {
        this.spinnerName = name;
    }
    public Spinner getSpinner()
    {
        return this.spinner;
    }
    public String getSpinnerName()
    {
        return this.spinnerName;
    }
}
