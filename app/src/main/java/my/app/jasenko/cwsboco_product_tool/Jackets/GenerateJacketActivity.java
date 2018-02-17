package my.app.jasenko.cwsboco_product_tool.Jackets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import my.app.jasenko.cwsboco_product_tool.DownloadTask;
import my.app.jasenko.cwsboco_product_tool.MainActivity;
import my.app.jasenko.cwsboco_product_tool.PDFActivity;
import my.app.jasenko.cwsboco_product_tool.ProductCode;
import my.app.jasenko.cwsboco_product_tool.R;

import java.util.Timer;
import java.util.TimerTask;

public class GenerateJacketActivity extends AppCompatActivity  {

    private TextView accName, product, productType,stud,fabric, sleeve, cuffs, pockets, collar, garmentColor, collarColor,productCode;
    private Button btnGeneratePDF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_jacket);

        displayJacketSpecDetails();
        btnGeneratePDFAction();
    }


    public void btnGeneratePDFAction() {
        this.btnGeneratePDF = (Button) findViewById(R.id.btnGeneratePdf);

        btnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadTask task = new DownloadTask();
                task.mContext = GenerateJacketActivity.this;
                task.execute();
                Toast.makeText(getApplicationContext(), "Downloading PDF...", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Opening PDF...",Toast.LENGTH_LONG).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(GenerateJacketActivity.this, PDFActivity.class);
                        startActivity(myIntent);
                    }
                }, 9000);


            }
        });
    }
    public void displayJacketSpecDetails() {

        accName = (TextView) findViewById(R.id.txtAccName);
        product = (TextView) findViewById(R.id.txtProduct);
        productType = (TextView) findViewById(R.id.txtProductType);
        collar = (TextView) findViewById(R.id.txtCollar);
        pockets = (TextView) findViewById(R.id.txtPockets);
        cuffs = (TextView) findViewById(R.id.txtCuffs);
        stud = (TextView) findViewById(R.id.txtStud);
        fabric = (TextView) findViewById(R.id.txtFabric);
        sleeve = (TextView) findViewById(R.id.txtSleeve);
        garmentColor = (TextView) findViewById(R.id.txtGarmentColor);
        collarColor = (TextView) findViewById(R.id.txtCollarColor);
        productCode = (TextView) findViewById(R.id.txtProductCode);

        accName.setText(MainActivity.sAccName);

        product.setText(MainActivity.productChosen.getProduct());

        MainActivity.ProductCode = new ProductCode(MainActivity.mDBHelper.getJacketProductCode());
        productType.setText(JacketsSpecActivity.spinners[0].getSpinnerName());
        collar.setText(JacketsSpecActivity.spinners[1].getSpinnerName());
        cuffs.setText(JacketsSpecActivity.spinners[2].getSpinnerName());
        pockets.setText(JacketsSpecActivity.spinners[3].getSpinnerName());
        stud.setText(JacketsSpecActivity.spinners[4].getSpinnerName());
        fabric.setText(JacketsSpecActivity.spinners[5].getSpinnerName());
        sleeve.setText(JacketsSpecActivity.spinners[6].getSpinnerName());
        garmentColor.setText(JacketsSpecActivity.spinners[7].getSpinnerName());
        collarColor.setText(JacketsSpecActivity.spinners[8].getSpinnerName());
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