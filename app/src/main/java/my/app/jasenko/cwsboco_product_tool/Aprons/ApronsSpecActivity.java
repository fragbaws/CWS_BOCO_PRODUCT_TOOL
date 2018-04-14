package my.app.jasenko.cwsboco_product_tool.Aprons;

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

import java.util.ArrayList;


import my.app.jasenko.cwsboco_product_tool.MainActivity;
import my.app.jasenko.cwsboco_product_tool.R;
import my.app.jasenko.cwsboco_product_tool.SpinnerDetails;


public class ApronsSpecActivity extends AppCompatActivity {

    private boolean productSpinners = false,colorSpinners = false;
    public static SpinnerDetails[] spinners = new SpinnerDetails[5];   //[0] = productType, [1] = length, [2] = stripes, [3] = primaryColor, [4] = secondaryColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprons_spec);

        addSpinnerProductType();
        addListenerOnButton();
    }
    private void addSpinnerProductType()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getApronProductType();
        spinners[0] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_product_type_aprons), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[0].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection(spinners[0].getSpinner());

    }

    private void addSpinnerLength()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getApronLength();
        spinners[1] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_length), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[1].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection2(spinners[1].getSpinner());
    }

    private void addSpinnerStripe()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getApronStripe();
        spinners[2] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_stripes), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[2].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection3(spinners[2].getSpinner());
    }

    private void addSpinnerPrimaryColor()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getApronPrimaryColor();
        spinners[3] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_primaryColor), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[3].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection4(spinners[3].getSpinner());
    }



    private void addSpinnerSecondaryColor()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getApronSecondaryColor();
        spinners[4] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_secondaryColor), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, entries);
        spinners[4].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection5(spinners[4].getSpinner());
    }

    private void addListenerOnButton()
    {
        Button aContinue = (Button) findViewById(R.id.continue_button_spec);

        aContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(productSpinners && colorSpinners) {
                    Intent myIntent = new Intent(ApronsSpecActivity.this, GenerateApronsActivity.class);
                    startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(ApronsSpecActivity.this,"Not every specification was chosen.", Toast.LENGTH_LONG).show();
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
                addSpinnerLength();
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
                addSpinnerStripe();
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
                if(!spinners[2].getSpinnerName().equals("Select stripes...")) {
                    productSpinners = true;
                }
                addSpinnerPrimaryColor();
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
                addSpinnerSecondaryColor();
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
                if(!spinners[4].getSpinnerName().equals("Select secondary color...")) {
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
