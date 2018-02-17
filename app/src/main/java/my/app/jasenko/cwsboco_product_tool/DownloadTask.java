package my.app.jasenko.cwsboco_product_tool;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Jasenko on 14/01/2018.
 */

public class DownloadTask extends AsyncTask {

    public Context mContext;
    public boolean downloaded = false;

    private String server = "news.sj-help.com";
    private int port = 21;
    private String user = "serg124";
    private String pass = "Serg8595";


    @Override
    protected Object doInBackground(Object[] objects) {

        FTPClient ftp = null;

        File direct = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWSBoco Product Tool");
        if (!direct.exists()) {
            direct.mkdirs();
        }

        try {
            ftp = new FTPClient();
            ftp.connect(server, port);
            Log.d("FTP", "Connected. Reply: " + ftp.getReplyString());

            ftp.login(user, pass);
            Log.d("FTP", "Logged in");
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            Log.d("FTP","Downloading");
            ftp.enterLocalPassiveMode();

            OutputStream outputStream = null;
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(
                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/CWSBoco Product Tool/"+MainActivity.sAccName+"_"+MainActivity.ProductCode.getProductCode()+".pdf"));
                ftp.retrieveFile("/news.sj-help.com/wwwroot/News/wp-content/uploads/"+MainActivity.ProductCode.getProductCode()+".pdf", outputStream);

            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp != null) {
                try {
                    ftp.logout();
                    ftp.disconnect();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        downloaded = true;
        return null;
    }
}
