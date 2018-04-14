package my.app.jasenko.cwsboco_product_tool.Coats;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import my.app.jasenko.cwsboco_product_tool.DownloadTask;
import my.app.jasenko.cwsboco_product_tool.MainActivity;
import my.app.jasenko.cwsboco_product_tool.PDFActivity;
import my.app.jasenko.cwsboco_product_tool.ProductCode;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class GenerateCoatsActivity extends AppCompatActivity  {

    private TextView accName, product, productType, cuffs, pockets, collar, garmentColor, collarColor, productCode;
    private Button btnGeneratePDF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(my.app.jasenko.cwsboco_product_tool.R.layout.activity_generate_coat);
        displayCoatSpecDetails();
        btnGeneratePDFAction();

    }

    public void btnGeneratePDFAction() {
        this.btnGeneratePDF = (Button) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.btnGeneratePdf);

        btnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File folder = new File(Environment.getExternalStorageDirectory() + "/CWSBoco Product Tool/");
                if (folder.isDirectory())
                {
                    String[] children = folder.list();
                    for (int i = 0; i < children.length; i++)
                    {
                        new File(folder, children[i]).delete();
                    }
                }
                DownloadTask task = new DownloadTask();
                task.mContext = GenerateCoatsActivity.this;
                task.execute();
                Toast.makeText(GenerateCoatsActivity.this, "Downloading PDF...", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Opening PDF...",Toast.LENGTH_LONG).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(GenerateCoatsActivity.this, PDFActivity.class);
                        startActivity(myIntent);
                    }
                }, 10000);
            }
        });
    }

    public void displayCoatSpecDetails() {
        accName = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtAccName);
        product = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtProduct);
        productType = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtProductType);
        cuffs = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtCuffs);
        pockets = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtPockets);
        collar = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtCollar);
        garmentColor = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtGarmentColor);
        collarColor = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtCollarColor);
        productCode = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtProductCode);

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