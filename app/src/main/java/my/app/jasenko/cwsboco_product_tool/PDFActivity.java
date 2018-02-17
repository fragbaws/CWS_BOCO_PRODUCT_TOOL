package my.app.jasenko.cwsboco_product_tool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import my.app.jasenko.cwsboco_product_tool.Signature.PaintView;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.qoppa.android.pdf.PDFException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PDFActivity extends AppCompatActivity  {

    private ShareActionProvider mShareActionProvider;
    private Context mContext;
    private PaintView paintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWSBoco Product Tool/" + MainActivity.sAccName + "_" + MainActivity.ProductCode.getProductCode() + ".pdf");
            pdfView.fromFile(file)
                    .enableDoubletap(true)
                    .enableSwipe(true)
                    .load();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        paintView.init(metrics);

        this.mContext = this.getApplicationContext();
    }

    public boolean write(String fname) throws PDFException, IOException, DocumentException {

        PdfReader reader = new PdfReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWSBoco Product Tool/" +fname);
        PdfStamper stamper = new PdfStamper(reader,new FileOutputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWSBoco Product Tool/"+MainActivity.sAccName+"_Official_"+ MainActivity.ProductCode.getProductCode()+".pdf"));
        PdfContentByte over = null;

         /*ADD SIGNATURE*/

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        paintView.mBitmap.compress(Bitmap.CompressFormat.PNG, 10 , stream);
        Image image = Image.getInstance(stream.toByteArray());
        image.setTransparency(new int[] {0xF0,0xFF});
        image.scaleAbsoluteHeight(400);
        image.scaleAbsoluteWidth((image.getWidth() * 350) / image.getHeight());
        image.setAbsolutePosition(-10,(-100));

        over = stamper.getOverContent(1);
        over.addImage(image);

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

         /* ADD TEXT*/

        over.beginText();;
        BaseFont bf = BaseFont.createFont(BaseFont.COURIER,"Cp1252",false);
        over.setFontAndSize(bf,14);
        over.showTextAligned(PdfContentByte.ALIGN_LEFT,"Signature:", (reader.getPageSize(1).getLeft()+10),(reader.getPageSize(1).getBottom()+280),0);
        over.showTextAligned(PdfContentByte.ALIGN_LEFT, "Date: " + formattedDate,(reader.getPageSize(1).getLeft()+10),(reader.getPageSize(1).getBottom()+300),0);
        over.showTextAligned(PdfContentByte.ALIGN_LEFT, "Account Name: " + MainActivity.sAccName,reader.getPageSize(1).getLeft()+(image.getWidth() * 350) / image.getHeight(),reader.getPageSize(1).getBottom()+280,0);
        over.showTextAligned(PdfContentByte.ALIGN_LEFT, "Customer Name: " + MainActivity.sCustName,reader.getPageSize(1).getLeft()+(image.getWidth() * 350) / image.getHeight(),reader.getPageSize(1).getBottom()+260,0);
        over.showTextAligned(PdfContentByte.ALIGN_LEFT,"Contact Number: " + MainActivity.sCustNum,reader.getPageSize(1).getLeft()+(image.getWidth() * 350) / image.getHeight(),reader.getPageSize(1).getBottom()+240,0);
        over.showTextAligned(PdfContentByte.ALIGN_LEFT,"Customer Email: " + MainActivity.sCustEmail,reader.getPageSize(1).getLeft()+(image.getWidth() * 350) / image.getHeight(),reader.getPageSize(1).getBottom()+220,0 );
        over.endText();
        stamper.close();
        Log.d("Suceess","Sucess");
        return true;

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this,"Loading..", Toast.LENGTH_LONG);

                try {
                    write((MainActivity.sAccName+"_"+ MainActivity.ProductCode.getProductCode()+".pdf"));
                } catch (PDFException | IOException | DocumentException e) {
                    e.printStackTrace();
                }



                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWSBoco Product Tool/"+MainActivity.sAccName+"_Official_"+ MainActivity.ProductCode.getProductCode()+".pdf");
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/*");
                sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{MainActivity.sCustEmail});
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, MainActivity.productChosen.getProduct() +" for account " + MainActivity.sAccName);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, ("Account Name: " + MainActivity.sAccName + "\n" +
                        "Customer Name: "+ MainActivity.sCustName + "\n" +
                        "Customer Email: "+ MainActivity.sCustEmail + "\n" +
                        "Contact Number: "+MainActivity.sCustNum + "\n" +
                        "Product Code: " +MainActivity.ProductCode.getProductCode()+ "\n"));


                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
                startActivity(Intent.createChooser(sharingIntent, "Sharing Option"));
                return true;

            case R.id.clear:
                paintView.clear();
                Toast.makeText(this.getApplicationContext(),"Screen erased",Toast.LENGTH_SHORT).show();

                return true;

            case R.id.delete:

                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to delete all files?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                File folder = new File(Environment.getExternalStorageDirectory() + "/CWSBoco Product Tool/");

                                Toast.makeText(PDFActivity.this,"Files being deleted...",Toast.LENGTH_SHORT).show();
                                if (folder.isDirectory())
                                {
                                    String[] children = folder.list();
                                    for (int i = 0; i < children.length; i++)
                                    {
                                        new File(folder, children[i]).delete();
                                    }
                                }
                                Toast.makeText(PDFActivity.this,"Files deleted succesfully",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

