package my.app.jasenko.cwsboco_product_tool.Jackets;

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

import my.app.jasenko.cwsboco_product_tool.MainActivity;
import my.app.jasenko.cwsboco_product_tool.SpinnerDetails;

import java.util.ArrayList;

public class JacketsSpecActivity extends AppCompatActivity {

    private boolean productSpinners = false,colorSpinners = false;
    public static SpinnerDetails[] spinners = new SpinnerDetails[9];   //[0] = productType, [1] = collar, [2] = cuffs, [3] = pockets, [4] = stud, [5] = fabric, [6] = sleeve,[7] = garmentColor, [8] = collarColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(my.app.jasenko.cwsboco_product_tool.R.layout.activity_jackets_spec);

        addSpinnerProductType();
        addListenerOnButton();
    }
    private void addSpinnerProductType()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketProductTypes();
        spinners[0] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_product_type_jacket), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[0].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection(spinners[0].getSpinner());
    }
    private void addSpinnerCollar()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketCollars();
        spinners[1] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_collar_jacket), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[1].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection2(spinners[1].getSpinner());
    }

    private void addSpinnerCuffs()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketCuffs();
        spinners[2] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_cuffs_jacket), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[2].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection3(spinners[2].getSpinner());

    }
    private void addSpinnerPockets()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketPockets();
        spinners[3] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_pockets_jacket), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[3].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection4(spinners[3].getSpinner());
    }
    private void addSpinnerStud()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketStuds();
        spinners[4] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_stud), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[4].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection5(spinners[4].getSpinner());
    }
    private void addSpinnerFabric()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketFabrics();
        spinners[5] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_fabric_jacket), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[5].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection6(spinners[5].getSpinner());
    }
    private void addSpinnerSleeve()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketSleeves();
        spinners[6] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_sleeve_jacket), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[6].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection7(spinners[6].getSpinner());
    }
    private void addSpinnerGarmentColor()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketGarmentColor();
        spinners[7] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_garmentColor_jacket), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[7].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection8(spinners[7].getSpinner());
    }
    private void addSpinnerCollarColor()
    {
        ArrayList<String> entries = MainActivity.mDBHelper.getJacketCollarColor();
        spinners[8] = new SpinnerDetails((Spinner) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.spinner_collarColor_jacket), "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, my.app.jasenko.cwsboco_product_tool.R.layout.spinner_layout, my.app.jasenko.cwsboco_product_tool.R.id.txt, entries);
        spinners[8].getSpinner().setAdapter(adapter);
        addListenerOnSpinnerItemSelection9(spinners[8].getSpinner());
    }
    private void addListenerOnButton()
    {
        Button aContinue = (Button) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.continue_button_spec_jacket);

        aContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(productSpinners && colorSpinners) {
                    Intent myIntent = new Intent(JacketsSpecActivity.this, GenerateJacketActivity.class);
                    startActivity(myIntent);
                }
                else
                {
                    Toast.makeText(JacketsSpecActivity.this,"Not every specification was chosen.", Toast.LENGTH_LONG).show();
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
                addSpinnerCollar();
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
                addSpinnerCuffs();
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
                addSpinnerPockets();
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
                addSpinnerStud();
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
                addSpinnerFabric();
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
                addSpinnerSleeve();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addListenerOnSpinnerItemSelection7(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[6] = new SpinnerDetails(spinner, selectedItem);
                if(!spinners[6].getSpinnerName().equals("Select sleeve...")) {
                    productSpinners = true;
                }
                addSpinnerGarmentColor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addListenerOnSpinnerItemSelection8(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[7] = new SpinnerDetails(spinner, selectedItem);
                addSpinnerCollarColor();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addListenerOnSpinnerItemSelection9(final Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = (String) adapterView.getItemAtPosition(i);
                spinners[8] = new SpinnerDetails(spinner, selectedItem);
                if(!spinners[8].getSpinnerName().equals("Select collar color...")) {
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
