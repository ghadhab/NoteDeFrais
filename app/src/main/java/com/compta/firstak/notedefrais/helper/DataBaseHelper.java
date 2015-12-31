package com.compta.firstak.notedefrais.helper;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;


        import java.util.LinkedList;
        import java.util.List;

/**
 * Created by Haithem on 14/05/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "AzurDB";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create category table
        String CREATE_CATEGORY_TABLE = "CREATE TABLE Category ( " +
                "category_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_name TEXT, "+
                "subcategory_name TEXT )";

        // create category table
        db.execSQL(CREATE_CATEGORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS Category");

        // create fresh table
        this.onCreate(db);
    }


    //////////////////CRUD Category////////////////////
    // Category table name
    private static final String TABLE_CATEGORY = "Category";

    // Category Table Columns names
    private static final String KEY_ID_CATEGORY = "category_id";
    private static final String KEY_NAME_CATEGORY = "category_name";
    private static final String KEY_SUB_CATEGORY = "subcategory_name";

    private static final String[] COLUMNS_CATEGORY = {KEY_ID_CATEGORY,KEY_NAME_CATEGORY,KEY_SUB_CATEGORY};

/*
    public void addCategory(Category category){
        //for logging
        Log.d("addCategory", category.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_NAME_CATEGORY, category.getCategory_name()); // get title
        values.put(KEY_SUB_CATEGORY, category.getSubcategory_name()); // get author

        // 3. insert
        db.insert(TABLE_CATEGORY, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }



    public Category getCategory(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_CATEGORY, // a. table
                        COLUMNS_CATEGORY, // b. column names
                        " category_id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build category object
        Category category = new Category();
        category.setCategory_id(Integer.parseInt(cursor.getString(0)));
        category.setCategory_name(cursor.getString(1));
        category.setSubcategory_name(cursor.getString(2));

        Log.d("getCategory("+id+")", category.toString());

        // 5. return category
        return category;
    }

    // Get All Categoriess
    public List<Category> getAllCategories() {
        List<Category> categories = new LinkedList<Category>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_CATEGORY;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build category and add it to list
        Category category = null;
        if (cursor.moveToFirst()) {
            do {
                category = new Category();
                category.setCategory_id(Integer.parseInt(cursor.getString(0)));
                category.setCategory_name(cursor.getString(1));
                category.setSubcategory_name(cursor.getString(2));

                // Add category to categories
                categories.add(category);
            } while (cursor.moveToNext());
        }

        Log.d("getAllCategories()", categories.toString());

        // return Categories
        return categories;
    }

    */
}
