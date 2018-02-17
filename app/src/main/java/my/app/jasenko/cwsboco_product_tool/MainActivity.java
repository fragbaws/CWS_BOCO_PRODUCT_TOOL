package my.app.jasenko.cwsboco_product_tool;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import my.app.jasenko.cwsboco_product_tool.Coats.CoatsSpecActivity;
import my.app.jasenko.cwsboco_product_tool.Coveralls.CoverallsSpecActivity;
import my.app.jasenko.cwsboco_product_tool.Jackets.JacketsSpecActivity;
import my.app.jasenko.cwsboco_product_tool.Trousers.TrousersSpecActivity;
import my.app.jasenko.cwsboco_product_tool.data.ProductsDBHelper;

public class MainActivity extends AppCompatActivity {

    public static ProductCode ProductCode;
    private Spinner productTypeSpinner;
    private Button btnContinue;
    public static ProductChosen productChosen;
    public static String sAccName, sCustName, sCustNum, sCustEmail;
    private EditText etAccName, etCustName, etCustNum, etCustEmail;

    public static ProductsDBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addSpinner();
        addListenerOnButton();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        mDBHelper = new ProductsDBHelper(this);
    }



    private void addSpinner() {
        this.productTypeSpinner = (Spinner) findViewById(R.id.spinner_main);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_array, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productTypeSpinner.setAdapter(adapter);
    }

    private void addListenerOnButton() {
        this.productTypeSpinner = (Spinner) findViewById(R.id.spinner_main);
        this.btnContinue = (Button) findViewById(R.id.continue_button_main);


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAccName = (EditText) findViewById(R.id.etAccount_name);
                etCustName = (EditText) findViewById(R.id.etCustomer_name);
                etCustNum = (EditText) findViewById(R.id.etCustomer_contact_number);
                etCustEmail = (EditText) findViewById(R.id.etCustomer_email);

                if (productTypeSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(MainActivity.this,
                            "Product not chosen",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etAccName.getText().toString().equals("") || etCustName.getText().toString().equals("") || etCustNum.getText().toString().equals("") || etCustEmail.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "One of the fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                sAccName = etAccName.getText().toString();
                sCustName = etCustName.getText().toString();
                sCustNum = etCustNum.getText().toString();
                sCustEmail = etCustEmail.getText().toString();
                productChosen = new ProductChosen(String.valueOf(productTypeSpinner.getSelectedItem()));

                if(String.valueOf(productTypeSpinner.getSelectedItem()).equals("Coat")) {
                    Intent myIntent = new Intent(MainActivity.this, CoatsSpecActivity.class);
                    startActivity(myIntent);
                }
                else if(String.valueOf(productTypeSpinner.getSelectedItem()).equals("Coverall")) {
                    Intent myIntent = new Intent(MainActivity.this, CoverallsSpecActivity.class);
                    startActivity(myIntent);
                }
                else if(String.valueOf(productTypeSpinner.getSelectedItem()).equals("Jacket"))
                {
                    Intent myIntent = new Intent(MainActivity.this, JacketsSpecActivity.class);
                    startActivity(myIntent);
                }
                else if(String.valueOf(productTypeSpinner.getSelectedItem()).equals("Trouser"))
                {
                    Intent myIntent = new Intent(MainActivity.this, TrousersSpecActivity.class);
                    startActivity(myIntent);
                }
            }
        });
    }

}
