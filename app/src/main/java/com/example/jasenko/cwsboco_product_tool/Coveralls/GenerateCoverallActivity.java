package com.example.jasenko.cwsboco_product_tool.Coveralls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jasenko.cwsboco_product_tool.DownloadTask;
import com.example.jasenko.cwsboco_product_tool.MainActivity;
import com.example.jasenko.cwsboco_product_tool.PDFActivity;
import com.example.jasenko.cwsboco_product_tool.ProductCode;
import com.example.jasenko.cwsboco_product_tool.R;

import java.util.Timer;
import java.util.TimerTask;

public class GenerateCoverallActivity extends AppCompatActivity  {

    private TextView accName, product, productType,rulePocket,access,fabric, cuffs, pockets, collar, garmentColor, collarColor,productCode;
    private Button btnGeneratePDF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_coverall);


        displayCoverallSpecDetails();
        btnGeneratePDFAction();
    }


    public void btnGeneratePDFAction() {
        this.btnGeneratePDF = (Button) findViewById(R.id.btnGeneratePdf);

        btnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadTask task = new DownloadTask();
                task.mContext = GenerateCoverallActivity.this;
                task.execute();
                Toast.makeText(getApplicationContext(), "Downloading PDF...", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Opening PDF...",Toast.LENGTH_SHORT).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(GenerateCoverallActivity.this, PDFActivity.class);
                        startActivity(myIntent);
                    }
                }, 8000);
            }
        });
    }

    public void displayCoverallSpecDetails() {

        accName = (TextView) findViewById(R.id.txtAccName);
        product = (TextView) findViewById(R.id.txtProduct);
        productType = (TextView) findViewById(R.id.txtProductType);
        collar = (TextView) findViewById(R.id.txtCollar);
        rulePocket = (TextView) findViewById(R.id.txtRulePocket);
        pockets = (TextView) findViewById(R.id.txtPockets);
        cuffs = (TextView) findViewById(R.id.txtCuffs);
        access = (TextView) findViewById(R.id.txtAccess);
        fabric = (TextView) findViewById(R.id.txtFabric);
        garmentColor = (TextView) findViewById(R.id.txtGarmentColor);
        collarColor = (TextView) findViewById(R.id.txtCollarColor);
        productCode = (TextView) findViewById(R.id.txtProductCode);

        accName.setText(MainActivity.sAccName);

        product.setText(MainActivity.productChosen.getProduct());

        MainActivity.ProductCode = new ProductCode(MainActivity.mDBHelper.getCoverallProductCode());
        productType.setText(CoverallsSpecActivity.spinners[0].getSpinnerName());
        collar.setText(CoverallsSpecActivity.spinners[1].getSpinnerName());
        rulePocket.setText(CoverallsSpecActivity.spinners[2].getSpinnerName());
        cuffs.setText(CoverallsSpecActivity.spinners[3].getSpinnerName());
        pockets.setText(CoverallsSpecActivity.spinners[4].getSpinnerName());
        access.setText(CoverallsSpecActivity.spinners[5].getSpinnerName());
        fabric.setText(CoverallsSpecActivity.spinners[6].getSpinnerName());
        garmentColor.setText(CoverallsSpecActivity.spinners[7].getSpinnerName());
        collarColor.setText(CoverallsSpecActivity.spinners[8].getSpinnerName());
        productCode.setText(MainActivity.ProductCode.getProductCode());

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