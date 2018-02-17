package my.app.jasenko.cwsboco_product_tool.Trousers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class TrousersSpecActivity extends AppCompatActivity {

    private boolean productSpinners = false,colorSpinners = false;
    public static SpinnerDetails[] spinners = new SpinnerDetails[6];   //[0] = productType, [1] = waist, [2] = pockets, [3] = fly, [4] = fabric, [5] = garmentColor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trousers_spec);

        addSpinnerProductType();
        addListenerOnButton();
    }

    private void addSpinnerProductType()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getTrouserProductTypes();
        spinners[0] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_product_type_trouser), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[0].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection(spinners[0].getSpinner());
    }
    private void addSpinnerWaist()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getTrouserWaists();
        spinners[1] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_waist_trouser), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[1].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection2(spinners[1].getSpinner());
    }

    private void addSpinnerPockets()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getTrouserPockets();
        spinners[2] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_pocket_trouser), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[2].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection3(spinners[2].getSpinner());

    }
    private void addSpinnerFly()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getTrouserFly();
        spinners[3] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_fly_trouser), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[3].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection4(spinners[3].getSpinner());
    }
    private void addSpinnerFabric()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getTrouserFabric();
        spinners[4] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_fabric_trouser), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[4].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection5(spinners[4].getSpinner());
    }

    private void addSpinnerGarmentColor()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getTrouserGarmentColor();
        spinners[5] = new SpinnerDetails((Spinner) findViewById(R.id.spinner_garmentColor_trouser), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[5].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection6(spinners[5].getSpinner());
    }

    private void addListenerOnButton()
    {
        Button aContinue = (Button) findViewById(R.id.continue_button_spec_trouser);

        aContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(productSpinners && colorSpinners) {
                    Intent myIntent = new Intent(TrousersSpecActivity.this, GenerateTrouserActivity.class);
                    startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(TrousersSpecActivity.this,"Not every specification was chosen.", Toast.LENGTH_LONG).show();
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
                addSpinnerWaist();
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
                addSpinnerFly();
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
                addSpinnerFabric();
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
                if(!spinners[4].getSpinnerName().equals("Select fabric...")) {
                    productSpinners = true;
                }
                addSpinnerGarmentColor();
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
                if(!spinners[5].getSpinnerName().equals("Select garment color...")) {
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
