package com.example.jasenko.cwsboco_product_tool.data;

import android.provider.BaseColumns;

/**
 * Created by Jasenko on 27/12/2017.
 */

public final class ProductsContract {

    private ProductsContract(){}

    public static final class CoatsEntry implements BaseColumns{

        public static final String TABLE_NAME = "coats";
        public static final String COLUMN_COATS_ID = "id";
        public static final String COLUMN_COATS_PRODUCT_ID = "ProductId";
        public static final String COLUMN_COATS_PRODUCT = "Product";
        public static final String COLUMN_COATS_TYPE = "Type";
        public static final String COLUMN_COATS_COLLAR_ID = "CollarId";
        public static final String COLUMN_COATS_COLLAR = "Collar";
        public static final String COLUMN_COATS_CUFFS_ID = "CuffsId";
        public static final String COLUMN_COATS_CUFFS = "Cuffs";
        public static final String COLUMN_COATS_POCKETS_ID = "PocketsId";
        public static final String COLUMN_COATS_POCKETS = "Pockets";
        public static final String COLUMN_COATS_ABS_PRODUCT_CODE = "ABS_ProductCode";
        public static final String COLUMN_COATS_ABS_PRODUCT_NAME = "ABS_ProductName";
        public static final String COLUMN_COATS_LCC_STYLE_CODE = "LCC_StyleCode";
        public static final String COLUMN_COATS_FN = "FN";


    }

    public static final class CoverallEntry implements BaseColumns{

        public static final String TABLE_NAME = "coveralls";
        public static final String COLUMN_COVERALLS_ID = "id";
        public static final String COLUMN_COVERALLS_PRODUCT_ID = "ProductId";
        public static final String COLUMN_COVERALLS_PRODUCT = "Product";
        public static final String COLUMN_COVERALLS_TYPE = "Type";
        public static final String COLUMN_COVERALLS_RULE_POCKET_ID = "RulePocketId";
        public static final String COLUMN_COVERALLS_RULE_POCKET = "RulePocket";
        public static final String COLUMN_COVERALLS_COLLAR_ID = "CollarId";
        public static final String COLUMN_COVERALLS_COLLAR = "Collar";
        public static final String COLUMN_COVERALLS_CUFFS_ID = "CuffsId";
        public static final String COLUMN_COVERALLS_CUFFS = "Cuffs";
        public static final String COLUMN_COVERALLS_POCKETS_ID = "PocketsId";
        public static final String COLUMN_COVERALLS_POCKETS = "Pockets";
        public static final String COLUMN_COVERALLS_ACCESS_ID = "AccessId";
        public static final String COLUMN_COVERALLS_ACCESS = "Access";
        public static final String COLUMN_COVERALLS_FABRIC_ID = "FabricId";
        public static final String COLUMN_COVERALLS_FABRIC = "Fabric";
        public static final String COLUMN_COVERALLS_ABS_PRODUCT_CODE = "ABS_ProductCode";
        public static final String COLUMN_COVERALLS_LCC_STYLE_CODE = "LCC_StyleCode";
    }

    public static final class JacketsEntry implements BaseColumns{

        public static final String TABLE_NAME = "jackets";
        public static final String COLUMN_JACKETS_ID = "id";
        public static final String COLUMN_JACKETS_PRODUCT_ID = "ProductId";
        public static final String COLUMN_JACKETS_PRODUCT = "Product";
        public static final String COLUMN_JACKETS_TYPE = "Type";
        public static final String COLUMN_JACKETS_COLLAR_ID = "CollarId";
        public static final String COLUMN_JACKETS_COLLAR = "Collar";
        public static final String COLUMN_JACKETS_CUFFS_ID = "CuffsId";
        public static final String COLUMN_JACKETS_CUFFS = "Cuffs";
        public static final String COLUMN_JACKETS_POCKETS_ID = "PocketsId";
        public static final String COLUMN_JACKETS_POCKETS = "Pockets";
        public static final String COLUMN_JACKETS_STUD_ID = "StudId";
        public static final String COLUMN_JACKETS_STUD = "Stud";
        public static final String COLUMN_JACKETS_FABRIC_ID = "FabricId";
        public static final String COLUMN_JACKETS_FABRIC = "Fabric";
        public static final String COLUMN_JACKETS_ABS_PRODUCT_CODE = "ABS_ProductCode";
    }

    public static final class CoatColorCombinationsEntry implements BaseColumns{

        public static final String TABLE_NAME ="coat_color_combinations";
        public static final String COLUMN_COAT_COLOR_COMBINATIONS_ID = "id";
        public static final String COLUMN_COAT_COLOR_COMBINATIONS_GARMENT_COLOR = "GarmentColour";
        public static final String COLUMN_COAT_COLOR_COMBINATIONS_COLLAR_COLOR = "CollarColour";
        public static final String COLUMN_COAT_COLOR_COMBINATIONS_CODE = "Code";
    }
}
