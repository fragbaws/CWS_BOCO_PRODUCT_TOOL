package my.app.jasenko.cwsboco_product_tool.Aprons;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
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

public class GenerateApronsActivity extends AppCompatActivity {

    private TextView accName, product, productType,length, stripes, primaryColor, secondaryColor, productCode;
    private Button btnGeneratePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_aprons);

        displayApronSpecDetails();
        btnGeneratePDFAction();
    }

    public void btnGeneratePDFAction() {
        this.btnGeneratePDF = (Button) findViewById(R.id.btnGeneratePdf);

        btnGeneratePDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*File folder = new File(Environment.getExternalStorageDirectory() + "/CWSBoco Product Tool/");
                if (folder.isDirectory())
                {
                    String[] children = folder.list();
                    for (int i = 0; i < children.length; i++)
                    {
                        new File(folder, children[i]).delete();
                    }
                }*/
                DownloadTask task = new DownloadTask();
                task.mContext = GenerateApronsActivity.this;
                task.execute();
                Toast.makeText(GenerateApronsActivity.this, "Downloading PDF...", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Opening PDF...",Toast.LENGTH_LONG).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent myIntent = new Intent(GenerateApronsActivity.this, PDFActivity.class);
                        startActivity(myIntent);
                    }
                }, 10000);
            }
        });
    }

    public void displayApronSpecDetails() {
        accName = (TextView) findViewById(R.id.txtAccName);
        product = (TextView) findViewById(R.id.txtProduct);
        productType = (TextView) findViewById(R.id.txtProductType  );
        length = (TextView) findViewById(R.id.txtLength);
        stripes = (TextView) findViewById(R.id.txtStripes);
        primaryColor = (TextView) findViewById(R.id.txtPrimaryColor);
        secondaryColor = (TextView) findViewById(R.id.txtSecondaryColor);
        productCode = (TextView) findViewById(R.id.txtProductCode);

        accName.setText(MainActivity.sAccName);

        product.setText(MainActivity.productChosen.getProduct());

        MainActivity.ProductCode = new ProductCode(MainActivity.mDBHelper.getApronProductCode());
        productType.setText(ApronsSpecActivity.spinners[0].getSpinnerName());
        length.setText(ApronsSpecActivity.spinners[1].getSpinnerName());
        stripes.setText(ApronsSpecActivity.spinners[2].getSpinnerName());
        primaryColor.setText(ApronsSpecActivity.spinners[3].getSpinnerName());
        secondaryColor.setText(ApronsSpecActivity.spinners[4].getSpinnerName());
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
