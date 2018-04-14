package my.app.jasenko.cwsboco_product_tool.Trousers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import my.app.jasenko.cwsboco_product_tool.DownloadTask;
import my.app.jasenko.cwsboco_product_tool.MainActivity;
import my.app.jasenko.cwsboco_product_tool.PDFActivity;
import my.app.jasenko.cwsboco_product_tool.ProductCode;
import my.app.jasenko.cwsboco_product_tool.R;

public class GenerateTrouserActivity extends AppCompatActivity {

    private TextView accName, product, productType, waist, pockets, fly, fabric, garmentColor, productCode;
    private Button btnGeneratePDF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_trouser);

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
                task.mContext = GenerateTrouserActivity.this;
                task.execute();
                Toast.makeText(GenerateTrouserActivity.this, "Downloading PDF...", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Opening PDF...",Toast.LENGTH_LONG).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(GenerateTrouserActivity.this, PDFActivity.class);
                        startActivity(myIntent);
                    }
                }, 9000);
            }
        });
    }

    public void displayCoatSpecDetails() {
        accName = (TextView) findViewById(R.id.txtAccName);
        product = (TextView) findViewById(R.id.txtProduct);
        productType = (TextView) findViewById(R.id.txtProductType);
        waist = (TextView) findViewById(R.id.txtWaist);
        pockets = (TextView) findViewById(R.id.txtPockets);
        fly = (TextView) findViewById(R.id.txtFly);
        fabric = (TextView) findViewById(R.id.txtFabric);
        garmentColor = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtGarmentColor);
        productCode = (TextView) findViewById(my.app.jasenko.cwsboco_product_tool.R.id.txtProductCode);

        accName.setText(MainActivity.sAccName);

        product.setText(MainActivity.productChosen.getProduct());

        MainActivity.ProductCode = new ProductCode(MainActivity.mDBHelper.getTrouserProductCode());
        productType.setText(TrousersSpecActivity.spinners[0].getSpinnerName());
        waist.setText(TrousersSpecActivity.spinners[1].getSpinnerName());
        pockets.setText(TrousersSpecActivity.spinners[2].getSpinnerName());
        fly.setText(TrousersSpecActivity.spinners[3].getSpinnerName());
        fabric.setText(TrousersSpecActivity.spinners[4].getSpinnerName());
        garmentColor.setText(TrousersSpecActivity.spinners[5].getSpinnerName());
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
