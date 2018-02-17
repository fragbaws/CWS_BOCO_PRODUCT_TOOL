package my.app.jasenko.cwsboco_product_tool.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import my.app.jasenko.cwsboco_product_tool.Coats.CoatsSpecActivity;
import my.app.jasenko.cwsboco_product_tool.Coveralls.CoverallsSpecActivity;
import my.app.jasenko.cwsboco_product_tool.Jackets.JacketsSpecActivity;
import my.app.jasenko.cwsboco_product_tool.Trousers.TrousersSpecActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static my.app.jasenko.cwsboco_product_tool.data.ProductsContract.CoatColorCombinationsEntry;
import static my.app.jasenko.cwsboco_product_tool.data.ProductsContract.JacketsEntry;
import static my.app.jasenko.cwsboco_product_tool.data.ProductsContract.CoverallEntry;
import static my.app.jasenko.cwsboco_product_tool.data.ProductsContract.CoatsEntry;

/**
 * Created by Jasenko on 27/12/2017.
 */

public class ProductsDBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    private static String DATABASE_PATH = "";
    private SQLiteDatabase mDataBase;
    private final Context mContext;
    public static final String DATABASE_NAME = "products.db";
    private boolean mNeedUpdate = false;

    public ProductsDBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {

        if (mNeedUpdate) {
            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("Error Copying DataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DATABASE_NAME);
        OutputStream mOutput = new FileOutputStream(DATABASE_PATH + DATABASE_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }

    /* -------- GARMENT/COLLAR COLOR COMBINATION QUERIES -------- */

    public ArrayList<String> getCoatGarmentColor() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select garment color...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = null;
            selectQuery = "SELECT distinct " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("GarmentColour"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoatCollarColor() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select collar color...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_COLLAR_COLOR +
                    "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME + "\nWHERE "+CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR+"=\""+ CoatsSpecActivity.spinners[4].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("CollarColour"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoverallGarmentColor(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select garment color...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "";

            if(CoverallsSpecActivity.spinners[0].getSpinnerName().equals("Flame Retardant"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + ProductsContract.FlameRetardantColorCombinationsEntry.TABLE_NAME;
            }
            else if(CoverallsSpecActivity.spinners[0].getSpinnerName().equals("Engineering"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + ProductsContract.EngineeringColorCombinationsEntry.TABLE_NAME;
            }
            else {
                selectQuery = "SELECT distinct " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME;
            }

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("GarmentColour"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoverallCollarColor() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select collar color...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery;

            if(CoverallsSpecActivity.spinners[0].getSpinnerName().equals("Flame Retardant"))
            {
                list.add("NULL");
                return list;
            }
            else if(CoverallsSpecActivity.spinners[0].getSpinnerName().equals("Engineering"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_COLLAR_COLOR +
                        "\nFROM " + ProductsContract.EngineeringColorCombinationsEntry.TABLE_NAME + "\nWHERE "+ ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_GARMENT_COLOR+"=\""+ CoverallsSpecActivity.spinners[7].getSpinnerName()+"\"";
            }
            else {
                selectQuery = "SELECT distinct " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_COLLAR_COLOR +
                        "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME + "\nWHERE " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR + "=\"" + CoverallsSpecActivity.spinners[7].getSpinnerName() + "\"";
            }

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("CollarColour"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getJacketCollarColor() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select collar color...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "";

            if(JacketsSpecActivity.spinners[0].getSpinnerName().equals("Flame Retardant"))
            {
                list.add("NULL");
                return list;
            }
            else if(JacketsSpecActivity.spinners[0].getSpinnerName().equals("Food / Catering"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.FoodCateringColorCombinationsEntry.COLUMN_FOOD_CATERING_COMBINATIONS_COLLAR_COLOR +
                        "\nFROM " + ProductsContract.FoodCateringColorCombinationsEntry.TABLE_NAME + "\nWHERE "+ ProductsContract.FoodCateringColorCombinationsEntry.COLUMN_FOOD_CATERING_COMBINATIONS_GARMENT_COLOR+"=\""+ JacketsSpecActivity.spinners[7].getSpinnerName()+"\"";

            } else if (JacketsSpecActivity.spinners[0].getSpinnerName().equals("Engineering")) {
                selectQuery = "SELECT distinct " + ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_COLLAR_COLOR +
                        "\nFROM " + ProductsContract.EngineeringColorCombinationsEntry.TABLE_NAME + "\nWHERE "+ ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_GARMENT_COLOR+"=\""+ JacketsSpecActivity.spinners[7].getSpinnerName()+"\"";
            }
            else {
                selectQuery = "SELECT distinct " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_COLLAR_COLOR +
                        "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME + "\nWHERE " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR + "=\"" + JacketsSpecActivity.spinners[7].getSpinnerName() + "\"";
            }

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("CollarColour"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getJacketGarmentColor(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select garment color...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "";

            if(JacketsSpecActivity.spinners[0].getSpinnerName().equals("Flame Retardant"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + ProductsContract.FlameRetardantColorCombinationsEntry.TABLE_NAME;
            }
            else if(JacketsSpecActivity.spinners[0].getSpinnerName().equals("Engineering"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + ProductsContract.EngineeringColorCombinationsEntry.TABLE_NAME;
            }
            else if(JacketsSpecActivity.spinners[0].getSpinnerName().equals("Food / Catering"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.FoodCateringColorCombinationsEntry.COLUMN_FOOD_CATERING_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + ProductsContract.FoodCateringColorCombinationsEntry.TABLE_NAME;
            }
            else {
                selectQuery = "SELECT distinct " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME;
            }

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("GarmentColour"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getTrouserGarmentColor(){
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select garment color...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery;

            if(TrousersSpecActivity.spinners[0].getSpinnerName().equals("Flame Retardant"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_GARMENT_COLOR +
                            "\nFROM " + ProductsContract.FlameRetardantColorCombinationsEntry.TABLE_NAME;
            }
            else if(TrousersSpecActivity.spinners[0].getSpinnerName().equals("Food / Catering"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.TrouserColorCombinationsEntry.COLUMN_TROUSER_COLOR_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + ProductsContract.TrouserColorCombinationsEntry.TABLE_NAME;
            }
            else if(TrousersSpecActivity.spinners[0].getSpinnerName().equals("Engineering"))
            {
                selectQuery = "SELECT distinct " + ProductsContract.TrouserColorCombinationsEntry.COLUMN_TROUSER_COLOR_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + ProductsContract.TrouserColorCombinationsEntry.TABLE_NAME +
                        "\nWHERE " + ProductsContract.TrouserColorCombinationsEntry.COLUMN_TROUSER_COLOR_COMBINATIONS_GARMENT_COLOR + " != \"WHITE\""
                        + " and " + ProductsContract.TrouserColorCombinationsEntry.COLUMN_TROUSER_COLOR_COMBINATIONS_GARMENT_COLOR + " != \"BLACK\"";
            }
            else
            {
                selectQuery = "SELECT distinct " + ProductsContract.TrouserColorCombinationsEntry.COLUMN_TROUSER_COLOR_COMBINATIONS_GARMENT_COLOR +
                        "\nFROM " + ProductsContract.TrouserColorCombinationsEntry.TABLE_NAME +
                        "\nWHERE " + ProductsContract.TrouserColorCombinationsEntry.COLUMN_TROUSER_COLOR_COMBINATIONS_GARMENT_COLOR + " != \"BLACK\"";

            }

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("GarmentColour"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    /* -------- COAT QUERIES ------- */
    public ArrayList<String> getCoatProductTypes() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("Select product type...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "SELECT distinct " + ProductsContract.CoatsEntry.COLUMN_COATS_TYPE +
                    " FROM " + ProductsContract.CoatsEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Type"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoatCuffs() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select cuffs...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoatsEntry.COLUMN_COATS_CUFFS +
                    "\nFROM " + CoatsEntry.TABLE_NAME + "\nWHERE " + CoatsEntry.COLUMN_COATS_TYPE + "=\"" + CoatsSpecActivity.spinners[0].getSpinnerName()+"\"";
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Cuffs"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoatPockets() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select pockets...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoatsEntry.COLUMN_COATS_POCKETS +
                    "\nFROM " + CoatsEntry.TABLE_NAME + "\nWHERE " + CoatsEntry.COLUMN_COATS_TYPE + "= \"" + CoatsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and " +
                    CoatsEntry.COLUMN_COATS_CUFFS + "=\"" + CoatsSpecActivity.spinners[1].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Pockets"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoatCollar() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select collar...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoatsEntry.COLUMN_COATS_COLLAR +
                    "\nFROM " + CoatsEntry.TABLE_NAME + "\nWHERE " + CoatsEntry.COLUMN_COATS_TYPE + "= \"" + CoatsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and " +
                    CoatsEntry.COLUMN_COATS_CUFFS + "=\"" + CoatsSpecActivity.spinners[1].getSpinnerName()+"\"" + " and " +CoatsEntry.COLUMN_COATS_POCKETS + "=\"" + CoatsSpecActivity.spinners[2].getSpinnerName()+"\"" ;

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Collar"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public String getCoatProductCode(){

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        String productCode = new String();
        String colorCode = new String();

        try {

            String selectQueryProductCode = "SELECT " + CoatsEntry.COLUMN_COATS_ABS_PRODUCT_CODE +
                    "\nFROM " + CoatsEntry.TABLE_NAME + "\nWHERE " + CoatsEntry.COLUMN_COATS_TYPE + "= \"" + CoatsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and " +
                    CoatsEntry.COLUMN_COATS_CUFFS + "=\"" + CoatsSpecActivity.spinners[1].getSpinnerName()+"\"" + " and " +CoatsEntry.COLUMN_COATS_POCKETS + "=\"" + CoatsSpecActivity.spinners[2].getSpinnerName()+"\"" +
                    " and " + CoatsEntry.COLUMN_COATS_COLLAR + "=\"" + CoatsSpecActivity.spinners[3].getSpinnerName() +"\"";

            String selectQueryColorCode = "SELECT " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_CODE +
                    "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME + "\nWHERE "+CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR+"=\""+ CoatsSpecActivity.spinners[4].getSpinnerName()+"\"" +
                    " and " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_COLLAR_COLOR+"=\"" + CoatsSpecActivity.spinners[5].getSpinnerName()+"\"";


            Cursor cursor1 = db.rawQuery(selectQueryProductCode, null);
            while(cursor1.moveToNext()) {
                productCode = cursor1.getString(cursor1.getColumnIndex("ABS_ProductCode"));
            }
            cursor1.close();

            Cursor cursor2 = db.rawQuery(selectQueryColorCode,null);
            while(cursor2.moveToNext()) {
                colorCode = cursor2.getString(cursor2.getColumnIndex("Code"));
            }
            cursor2.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return (productCode+colorCode) ;
    }

    /* -------- COVERALL QUERIES ------- */
    public ArrayList<String> getCoverallProductTypes() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("Select product type...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "SELECT distinct " + CoverallEntry.COLUMN_COVERALLS_TYPE +
                    " FROM " + ProductsContract.CoverallEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Type"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoverallCollars() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select collar...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoverallEntry.COLUMN_COVERALLS_COLLAR +
                    "\nFROM " + CoverallEntry.TABLE_NAME + "\nWHERE " + CoverallEntry.COLUMN_COVERALLS_TYPE + "= \"" + CoverallsSpecActivity.spinners[0].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Collar"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoverallRulePockets() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select rule pocket...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoverallEntry.COLUMN_COVERALLS_RULE_POCKET +
                    "\nFROM " + CoverallEntry.TABLE_NAME + "\nWHERE " + CoverallEntry.COLUMN_COVERALLS_TYPE + "= \"" + CoverallsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_COLLAR +"= \""+CoverallsSpecActivity.spinners[1].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("RulePocket"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoverallCuffs() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select cuffs...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoverallEntry.COLUMN_COVERALLS_CUFFS +
                    "\nFROM " + CoverallEntry.TABLE_NAME + "\nWHERE " + CoverallEntry.COLUMN_COVERALLS_TYPE + "= \"" + CoverallsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_COLLAR +"= \""+CoverallsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "+ CoverallEntry.COLUMN_COVERALLS_RULE_POCKET +"= \""+CoverallsSpecActivity.spinners[2].getSpinnerName()+"\"";
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Cuffs"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoverallPockets() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select pockets...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoverallEntry.COLUMN_COVERALLS_POCKETS +
                    "\nFROM " + CoverallEntry.TABLE_NAME + "\nWHERE " + CoverallEntry.COLUMN_COVERALLS_TYPE + "= \"" + CoverallsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_COLLAR +"= \""+CoverallsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "+ CoverallEntry.COLUMN_COVERALLS_RULE_POCKET +"= \""+CoverallsSpecActivity.spinners[2].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_CUFFS +"= \""+CoverallsSpecActivity.spinners[3].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Pockets"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoverallAccess() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select access...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoverallEntry.COLUMN_COVERALLS_ACCESS +
                    "\nFROM " + CoverallEntry.TABLE_NAME + "\nWHERE " + CoverallEntry.COLUMN_COVERALLS_TYPE + "= \"" + CoverallsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_COLLAR +"= \""+CoverallsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "+ CoverallEntry.COLUMN_COVERALLS_RULE_POCKET +"= \""+CoverallsSpecActivity.spinners[2].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_CUFFS +"= \""+CoverallsSpecActivity.spinners[3].getSpinnerName()+"\"" +" and "+ CoverallEntry.COLUMN_COVERALLS_POCKETS +"= \""+CoverallsSpecActivity.spinners[4].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Access"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getCoverallFabrics() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select fabric...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + CoverallEntry.COLUMN_COVERALLS_FABRIC +
                    "\nFROM " + CoverallEntry.TABLE_NAME + "\nWHERE " + CoverallEntry.COLUMN_COVERALLS_TYPE + "= \"" + CoverallsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_COLLAR +"= \""+CoverallsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "+ CoverallEntry.COLUMN_COVERALLS_RULE_POCKET +"= \""+CoverallsSpecActivity.spinners[2].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_CUFFS +"= \""+CoverallsSpecActivity.spinners[3].getSpinnerName()+"\"" +" and "+ CoverallEntry.COLUMN_COVERALLS_POCKETS +"= \""+CoverallsSpecActivity.spinners[4].getSpinnerName()+"\"" +" and "
                    + CoverallEntry.COLUMN_COVERALLS_ACCESS+"= \""+CoverallsSpecActivity.spinners[5].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Fabric"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public String getCoverallProductCode(){

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        String productCode = new String();
        String colorCode = new String();

        try {

            String selectQueryProductCode = "SELECT distinct " + CoverallEntry.COLUMN_COVERALLS_ABS_PRODUCT_CODE +
                    "\nFROM " + CoverallEntry.TABLE_NAME + "\nWHERE " + CoverallEntry.COLUMN_COVERALLS_TYPE + "= \"" + CoverallsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_COLLAR +"= \""+CoverallsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "+ CoverallEntry.COLUMN_COVERALLS_RULE_POCKET +"= \""+CoverallsSpecActivity.spinners[2].getSpinnerName()+"\"" + " and "
                    + CoverallEntry.COLUMN_COVERALLS_CUFFS +"= \""+CoverallsSpecActivity.spinners[3].getSpinnerName()+"\"" +" and "+ CoverallEntry.COLUMN_COVERALLS_POCKETS +"= \""+CoverallsSpecActivity.spinners[4].getSpinnerName()+"\"" +" and "
                    + CoverallEntry.COLUMN_COVERALLS_ACCESS+"= \""+CoverallsSpecActivity.spinners[5].getSpinnerName()+"\"" +" and "+ CoverallEntry.COLUMN_COVERALLS_FABRIC +"=\""+CoverallsSpecActivity.spinners[6].getSpinnerName()+"\"";

            String selectQueryColorCode;

            if(CoverallsSpecActivity.spinners[0].getSpinnerName().equals("Flame Retardant"))
            {
                selectQueryColorCode = "SELECT " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_CODE +
                        "\nFROM " + ProductsContract.FlameRetardantColorCombinationsEntry.TABLE_NAME + "\nWHERE " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_GARMENT_COLOR + "=\"" + CoverallsSpecActivity.spinners[7].getSpinnerName() + "\"";
            }
            else {
                selectQueryColorCode = "SELECT " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_CODE +
                        "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME + "\nWHERE " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR + "=\"" + CoverallsSpecActivity.spinners[7].getSpinnerName() + "\"" +
                        " and " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_COLLAR_COLOR + "=\"" + CoverallsSpecActivity.spinners[8].getSpinnerName() + "\"";
            }


            Cursor cursor1 = db.rawQuery(selectQueryProductCode, null);
            while(cursor1.moveToNext()) {
                productCode = cursor1.getString(cursor1.getColumnIndex("ABS_ProductCode"));
            }
            cursor1.close();

            Cursor cursor2 = db.rawQuery(selectQueryColorCode,null);
            while(cursor2.moveToNext()) {
                colorCode = cursor2.getString(cursor2.getColumnIndex("Code"));
            }
            cursor2.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return (productCode+colorCode) ;
    }

    /* ------- JACKET QUERIES ------- */
    public ArrayList<String> getJacketProductTypes() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("Select product type...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "SELECT distinct " + JacketsEntry.COLUMN_JACKETS_TYPE +
                    " FROM " + JacketsEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Type"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getJacketCollars() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select collar...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + JacketsEntry.COLUMN_JACKETS_COLLAR +
                    "\nFROM " + JacketsEntry.TABLE_NAME + "\nWHERE " + JacketsEntry.COLUMN_JACKETS_TYPE + "= \"" + JacketsSpecActivity.spinners[0].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Collar"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getJacketCuffs() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select cuffs...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + JacketsEntry.COLUMN_JACKETS_CUFFS +
                    "\nFROM " + JacketsEntry.TABLE_NAME
                    + "\nWHERE " +JacketsEntry.COLUMN_JACKETS_TYPE + "= \"" + JacketsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + JacketsEntry.COLUMN_JACKETS_COLLAR +"= \""+JacketsSpecActivity.spinners[1].getSpinnerName()+"\"";
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Cuffs"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getJacketPockets() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select pockets...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + JacketsEntry.COLUMN_JACKETS_POCKETS +
                    "\nFROM " + JacketsEntry.TABLE_NAME
                    + "\nWHERE " + JacketsEntry.COLUMN_JACKETS_TYPE + "= \"" + JacketsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + JacketsEntry.COLUMN_JACKETS_COLLAR +"= \""+JacketsSpecActivity.spinners[1].getSpinnerName()+"\""+ " and "
                    + JacketsEntry.COLUMN_JACKETS_CUFFS +"= \""+JacketsSpecActivity.spinners[2].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Pockets"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getJacketStuds() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select stud...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + JacketsEntry.COLUMN_JACKETS_STUD +
                    "\nFROM " + JacketsEntry.TABLE_NAME
                    + "\nWHERE " + JacketsEntry.COLUMN_JACKETS_TYPE + "= \"" + JacketsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + JacketsEntry.COLUMN_JACKETS_COLLAR +"= \""+JacketsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "
                    + JacketsEntry.COLUMN_JACKETS_CUFFS +"= \""+JacketsSpecActivity.spinners[2].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_POCKETS +"= \""+JacketsSpecActivity.spinners[3].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Stud"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getJacketFabrics() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select fabric...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + JacketsEntry.COLUMN_JACKETS_FABRIC +
                    "\nFROM " + JacketsEntry.TABLE_NAME +
                    "\nWHERE " + JacketsEntry.COLUMN_JACKETS_TYPE + "= \"" + JacketsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + JacketsEntry.COLUMN_JACKETS_COLLAR +"= \""+JacketsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "
                    + JacketsEntry.COLUMN_JACKETS_CUFFS +"= \""+JacketsSpecActivity.spinners[2].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_POCKETS +"= \""+JacketsSpecActivity.spinners[3].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_STUD+"= \""+JacketsSpecActivity.spinners[4].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Fabric"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getJacketSleeves() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select sleeve...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + JacketsEntry.COLUMN_JACKETS_SLEEVE +
                    "\nFROM " + JacketsEntry.TABLE_NAME +
                    "\nWHERE " + JacketsEntry.COLUMN_JACKETS_TYPE + "= \"" + JacketsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + JacketsEntry.COLUMN_JACKETS_COLLAR +"= \""+JacketsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "
                    + JacketsEntry.COLUMN_JACKETS_CUFFS +"= \""+JacketsSpecActivity.spinners[2].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_POCKETS +"= \""+JacketsSpecActivity.spinners[3].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_STUD+"= \""+JacketsSpecActivity.spinners[4].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_FABRIC+"= \""+JacketsSpecActivity.spinners[5].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Sleeve"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public String getJacketProductCode(){

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        String productCode = new String();
        String colorCode = new String();

        try {

            String selectQueryProductCode = "SELECT distinct " + JacketsEntry.COLUMN_JACKETS_ABS_PRODUCT_CODE +
                    "\nFROM " + JacketsEntry.TABLE_NAME +
                    "\nWHERE " + JacketsEntry.COLUMN_JACKETS_TYPE + "= \"" + JacketsSpecActivity.spinners[0].getSpinnerName()+"\"" + " and "
                    + JacketsEntry.COLUMN_JACKETS_COLLAR +"= \""+JacketsSpecActivity.spinners[1].getSpinnerName()+"\""+" and "
                    + JacketsEntry.COLUMN_JACKETS_CUFFS +"= \""+JacketsSpecActivity.spinners[2].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_POCKETS +"= \""+JacketsSpecActivity.spinners[3].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_STUD+"= \""+JacketsSpecActivity.spinners[4].getSpinnerName()+"\"" +" and "
                    + JacketsEntry.COLUMN_JACKETS_FABRIC +"=\""+JacketsSpecActivity.spinners[5].getSpinnerName()+"\""+ " and "
                    + JacketsEntry.COLUMN_JACKETS_SLEEVE +"=\""+JacketsSpecActivity.spinners[6].getSpinnerName()+"\"";

            String selectQueryColorCode;

            if(JacketsSpecActivity.spinners[0].getSpinnerName().equals("Flame Retardant"))
            {
                selectQueryColorCode = "SELECT " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_CODE +
                        "\nFROM " + ProductsContract.FlameRetardantColorCombinationsEntry.TABLE_NAME
                        + "\nWHERE " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_GARMENT_COLOR + "=\"" + JacketsSpecActivity.spinners[7].getSpinnerName() + "\"";
            }
            else if(JacketsSpecActivity.spinners[0].getSpinnerName().equals("Food / Catering")) {
                selectQueryColorCode = "SELECT distinct " + ProductsContract.FoodCateringColorCombinationsEntry.COLUMN_FOOD_CATERING_COMBINATIONS_CODE +
                                        "\nFROM " + ProductsContract.FoodCateringColorCombinationsEntry.TABLE_NAME +
                                         "\nWHERE " + ProductsContract.FoodCateringColorCombinationsEntry.COLUMN_FOOD_CATERING_COMBINATIONS_GARMENT_COLOR + "=\"" + JacketsSpecActivity.spinners[7].getSpinnerName() + "\""
                                         + " and " + ProductsContract.FoodCateringColorCombinationsEntry.COLUMN_FOOD_CATERING_COMBINATIONS_COLLAR_COLOR + "=\"" + JacketsSpecActivity.spinners[8].getSpinnerName() +"\"";
            }
            else if(JacketsSpecActivity.spinners[0].getSpinnerName().equals("Engineering"))
            {
                selectQueryColorCode = "SELECT distinct " + ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_CODE +
                                    "\nFROM " + ProductsContract.EngineeringColorCombinationsEntry.TABLE_NAME +
                                    "\nWHERE " + ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_GARMENT_COLOR + "=\"" + JacketsSpecActivity.spinners[7].getSpinnerName() +"\""
                                    + " and " + ProductsContract.EngineeringColorCombinationsEntry.COLUMN_ENGINEERING_COMBINATIONS_COLLAR_COLOR + "=\"" + JacketsSpecActivity.spinners[8].getSpinnerName() + "\"";
            }
            else{
                selectQueryColorCode = "SELECT distinct " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_CODE +
                        "\nFROM " + CoatColorCombinationsEntry.TABLE_NAME +
                        "\nWHERE " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR +"=\"" + JacketsSpecActivity.spinners[7].getSpinnerName()+"\""
                        + " and " + CoatColorCombinationsEntry.COLUMN_COAT_COLOR_COMBINATIONS_COLLAR_COLOR +"=\"" + JacketsSpecActivity.spinners[8].getSpinnerName() +"\"";
            }

            Cursor cursor1 = db.rawQuery(selectQueryProductCode, null);
            while(cursor1.moveToNext()) {
                productCode = cursor1.getString(cursor1.getColumnIndex("ABS_ProductCode"));
            }
            cursor1.close();

            Cursor cursor2 = db.rawQuery(selectQueryColorCode,null);
            while(cursor2.moveToNext()) {
                colorCode = cursor2.getString(cursor2.getColumnIndex("Code"));
            }
            cursor2.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return (productCode+colorCode) ;
    }

    /* ------- TROUSER QUERIES ------- */

    public ArrayList<String> getTrouserProductTypes() {

        ArrayList<String> list = new ArrayList<String>();
        list.add("Select product type...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "SELECT distinct " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_TYPE +
                    "\nFROM " + ProductsContract.TrousersEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Type"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getTrouserWaists() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select waist...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_WAIST +
                    "\nFROM " + ProductsContract.TrousersEntry.TABLE_NAME
                    + "\nWHERE " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_TYPE + "= \"" + TrousersSpecActivity.spinners[0].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Waist"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getTrouserPockets() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select pockets...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_POCKETS
                    + "\nFROM " + ProductsContract.TrousersEntry.TABLE_NAME
                    + "\nWHERE " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_TYPE + "= \"" + TrousersSpecActivity.spinners[0].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_WAIST + "= \"" + TrousersSpecActivity.spinners[1].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Pocket"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getTrouserFly() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select fly...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_FLY
                    + "\nFROM " + ProductsContract.TrousersEntry.TABLE_NAME
                    + "\nWHERE " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_TYPE + "= \"" + TrousersSpecActivity.spinners[0].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_WAIST + "= \"" + TrousersSpecActivity.spinners[1].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_POCKETS + "= \"" + TrousersSpecActivity.spinners[2].getSpinnerName()+"\"";


            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Fly"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public ArrayList<String> getTrouserFabric() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Select fabric...");
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {

            String selectQuery = "SELECT distinct " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_FABRIC
                    + "\nFROM " + ProductsContract.TrousersEntry.TABLE_NAME
                    + "\nWHERE " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_TYPE + "= \"" + TrousersSpecActivity.spinners[0].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_WAIST + "= \"" + TrousersSpecActivity.spinners[1].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_POCKETS + "= \"" + TrousersSpecActivity.spinners[2].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_FLY + "= \"" + TrousersSpecActivity.spinners[3].getSpinnerName()+"\"";

            Cursor cursor = db.rawQuery(selectQuery, null);
            while (cursor.moveToNext()) {
                String value = cursor.getString(cursor.getColumnIndex("Fabric"));
                list.add(value);
            }
            cursor.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return list;
    }

    public String getTrouserProductCode(){

        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();
        String productCode = "";
        String colorCode = "";

        try {

            String selectQueryProductCode = "SELECT distinct " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_ABS_PRODUCT_CODE
                    + "\nFROM " + ProductsContract.TrousersEntry.TABLE_NAME
                    + "\nWHERE " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_TYPE + "= \"" + TrousersSpecActivity.spinners[0].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_WAIST + "= \"" + TrousersSpecActivity.spinners[1].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_POCKETS + "= \"" + TrousersSpecActivity.spinners[2].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_FLY + "= \"" + TrousersSpecActivity.spinners[3].getSpinnerName()+"\""
                    + " and " + ProductsContract.TrousersEntry.COLUMN_TROUSERS_FABRIC + "= \"" + TrousersSpecActivity.spinners[4].getSpinnerName()+"\"";

            String selectQueryColorCode;

            if(TrousersSpecActivity.spinners[0].getSpinnerName().equals("Flame Retardant"))
            {
                selectQueryColorCode = "SELECT " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_CODE +
                        "\nFROM " + ProductsContract.FlameRetardantColorCombinationsEntry.TABLE_NAME
                        + "\nWHERE " + ProductsContract.FlameRetardantColorCombinationsEntry.COLUMN_FLAME_RETARDANT_COMBINATIONS_GARMENT_COLOR + "=\"" + TrousersSpecActivity.spinners[5].getSpinnerName() + "\"";
            }
            else {
                selectQueryColorCode = "SELECT " + ProductsContract.TrouserColorCombinationsEntry.COLUMN_TROUSER_COLOR_COMBINATIONS_CODE +
                        "\nFROM " + ProductsContract.TrouserColorCombinationsEntry.TABLE_NAME +
                        "\nWHERE " + ProductsContract.TrouserColorCombinationsEntry.COLUMN_TROUSER_COLOR_COMBINATIONS_GARMENT_COLOR + "=\"" + TrousersSpecActivity.spinners[5].getSpinnerName() + "\"";
            }


            Cursor cursor1 = db.rawQuery(selectQueryProductCode, null);
            while(cursor1.moveToNext()) {
                productCode = cursor1.getString(cursor1.getColumnIndex("ABS_ProductCode"));
            }
            cursor1.close();

            Cursor cursor2 = db.rawQuery(selectQueryColorCode,null);
            while(cursor2.moveToNext()) {
                colorCode = cursor2.getString(cursor2.getColumnIndex("Code"));
            }
            cursor2.close();
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return (productCode+colorCode) ;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(SQL_DELETE_ENTRIES);
        //onCreate(db);

    }
}
