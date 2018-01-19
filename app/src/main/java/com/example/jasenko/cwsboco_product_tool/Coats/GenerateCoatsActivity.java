package com.example.jasenko.cwsboco_product_tool.Coats;

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

public class GenerateCoatsActivity extends AppCompatActivity  {

    private TextView accName, product, productType, cuffs, pockets, collar, garmentColor, collarColor, productCode;
    private Button btnGeneratePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_coat);
        displayCoatSpecDetails();
        btnGeneratePDFAction();

    }

    public void btnGeneratePDFAction() {
        this.btnGeneratePDF = (Button) findViewById(R.id.btnGeneratePdf);

        btnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadTask task = new DownloadTask();
                task.mContext = GenerateCoatsActivity.this;
                task.execute();
                Toast.makeText(GenerateCoatsActivity.this, "Downloading PDF...", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Opening PDF...",Toast.LENGTH_SHORT).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(GenerateCoatsActivity.this, PDFActivity.class);
                        startActivity(myIntent);
                    }
                }, 8000);
            }
        });
    }

    public void displayCoatSpecDetails() {
        accName = (TextView) findViewById(R.id.txtAccName);
        product = (TextView) findViewById(R.id.txtProduct);
        productType = (TextView) findViewById(R.id.txtProductType);
        cuffs = (TextView) findViewById(R.id.txtCuffs);
        pockets = (TextView) findViewById(R.id.txtPockets);
        collar = (TextView) findViewById(R.id.txtCollar);
        garmentColor = (TextView) findViewById(R.id.txtGarmentColor);
        collarColor = (TextView) findViewById(R.id.txtCollarColor);
        productCode = (TextView) findViewById(R.id.txtProductCode);

        accName.setText(MainActivity.sAccName);

        product.setText(MainActivity.productChosen.getProduct());

        MainActivity.ProductCode = new ProductCode(MainActivity.mDBHelper.getCoatProductCode());
        productType.setText(CoatsSpecActivity.spinners[0].getSpinnerName());
        cuffs.setText(CoatsSpecActivity.spinners[1].getSpinnerName());
        pockets.setText(CoatsSpecActivity.spinners[2].getSpinnerName());
        collar.setText(CoatsSpecActivity.spinners[3].getSpinnerName());
        garmentColor.setText(CoatsSpecActivity.spinners[4].getSpinnerName());
        collarColor.setText(CoatsSpecActivity.spinners[5].getSpinnerName());
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