package com.example.jasenko.cwsboco_product_tool.Coats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jasenko.cwsboco_product_tool.MainActivity;
import com.example.jasenko.cwsboco_product_tool.R;
import com.example.jasenko.cwsboco_product_tool.SpinnerDetails;

import java.util.ArrayList;

public class CoatsSpecActivity extends AppCompatActivity {

    private boolean productSpinners = false,colorSpinners = false;
    public static SpinnerDetails[] spinners = new SpinnerDetails[6];   //[0] = productType, [1] = cuffs, [2] = pockets, [3] = collar, [4] = garmentColor, [5] = collarColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coat_spec);

        addSpinnerProductType();
        addListenerOnButton();
    }
    private void addSpinnerCollar()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getCoatCollar();
        spinners[3] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_collar), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[3].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection4(spinners[3].getSpinner());
    }
    private void addSpinnerCuffs()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getCoatCuffs();
        spinners[1] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_cuffs), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[1].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection2(spinners[1].getSpinner());
    }
    private void addSpinnerPockets()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getCoatPockets();
        spinners[2] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_pockets), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[2].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection3(spinners[2].getSpinner());
    }
    private void addSpinnerProductType()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getCoatProductTypes();
        spinners[0] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_product_type), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[0].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection(spinners[0].getSpinner());

    }
    private void addSpinnerGarmentColor()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getGarmentColor();
        spinners[4] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_garmentColor), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[4].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection5(spinners[4].getSpinner());
    }
    private void addSpinnerCollarColor()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getCoatCollarColor();
        spinners[5] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_collarColor), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[5].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection6(spinners[5].getSpinner());
    }
    private void addListenerOnButton()
    {
        Button aContinue = (Button) findViewById(R.id.continue_button_spec);

        aContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(productSpinners && colorSpinners) {
                    Intent myIntent = new Intent(CoatsSpecActivity.this, GenerateCoatsActivity.class);
                    startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(CoatsSpecActivity.this,"Not every specification was chosen.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }



    private void addListenerOnSpinnerItemSelection(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[0] = new SpinnerDetails(spinner, selectedItem);
                addSpinnerCuffs();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addListenerOnSpinnerItemSelection2(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[1] = new SpinnerDetails(spinner, selectedItem);
                addSpinnerPockets();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addListenerOnSpinnerItemSelection3(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[2] = new SpinnerDetails(spinner, selectedItem);
                addSpinnerCollar();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addListenerOnSpinnerItemSelection4(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[3] = new SpinnerDetails(spinner, selectedItem);
                if(!spinners[3].getSpinnerName().equals("Select collar...")) {
                    productSpinners = true;
                }
                addSpinnerGarmentColor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addListenerOnSpinnerItemSelection5(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[4] = new SpinnerDetails(spinner, selectedItem);
                addSpinnerCollarColor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addListenerOnSpinnerItemSelection6(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[5] = new SpinnerDetails(spinner, selectedItem);
                if(!spinners[5].getSpinnerName().equals("Select collar color...")) {
                    colorSpinners = true;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
