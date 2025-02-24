package com.example.mylms;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "LMS5071";

    // Table names
    public static final String TABLE_PUBLISHER = "Publisher";
    public static final String TABLE_BRANCH = "Branch";
    public static final String TABLE_MEMBER = "Member";
    public static final String TABLE_BOOK_AUTHOR = "Book_Author";
    public static final String TABLE_BOOK_COPY = "Book_Copy";
    public static final String TABLE_BOOK_LOAN = "Book_Loan";
    public static final String TABLE_BOOK = "Book";

    // Columns names for Publisher table
    public static final String PUBLISHER_COL_1 = "NAME";
    public static final String PUBLISHER_COL_2 = "ADDRESS";
    public static final String PUBLISHER_COL_3 = "PHONE";

    // Columns names for Branch table
    public static final String BRANCH_COL_1 = "BRANCH_ID";
    public static final String BRANCH_COL_2 = "BRANCH_NAME";
    public static final String BRANCH_COL_3 = "ADDRESS";

    // Columns names for Member table
    public static final String MEMBER_COL_1 = "CARD_NO";
    public static final String MEMBER_COL_2 = "NAME";
    public static final String MEMBER_COL_3 = "ADDRESS";
    public static final String MEMBER_COL_4 = "PHONE";
    public static final String MEMBER_COL_5 = "UNPAID_DUES";

    // Columns names for Book_Author table
    public static final String BOOK_AUTHOR_COL_1 = "BOOK_ID";
    public static final String BOOK_AUTHOR_COL_2 = "AUTHOR_NAME";

    // Columns names for Book_Copy table
    public static final String BOOK_COPY_COL_1 = "BOOK_ID";
    public static final String BOOK_COPY_COL_2 = "BRANCH_ID";
    public static final String BOOK_COPY_COL_3 = "ACCESS_NO";

    // Columns names for Book_Loan table
    public static final String BOOK_LOAN_COL_1 = "ACCESS_NO";
    public static final String BOOK_LOAN_COL_2 = "BRANCH_ID";
    public static final String BOOK_LOAN_COL_3 = "CARD_NO";
    public static final String BOOK_LOAN_COL_4 = "DATE_OUT";
    public static final String BOOK_LOAN_COL_5 = "DATE_DUE";
    public static final String BOOK_LOAN_COL_6 = "DATE_RETURNED";

    // Columns names for Book table
    public static final String BOOK_COL_1 = "BOOK_ID";
    public static final String BOOK_COL_2 = "TITLE";
    public static final String BOOK_COL_3 = "PUBLISHER_NAME";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating Publisher table
        db.execSQL("CREATE TABLE " + TABLE_PUBLISHER + " (" +
                PUBLISHER_COL_1 + " VARCHAR(20) PRIMARY KEY," +
                PUBLISHER_COL_2 + " VARCHAR(30)," +
                PUBLISHER_COL_3 + " VARCHAR(10))");

        // Creating Branch table
        db.execSQL("CREATE TABLE " + TABLE_BRANCH + " (" +
                BRANCH_COL_1 + " VARCHAR(5) PRIMARY KEY," +
                BRANCH_COL_2 + " VARCHAR(20)," +
                BRANCH_COL_3 + " VARCHAR(30))");

        // Creating Member table
        db.execSQL("CREATE TABLE " + TABLE_MEMBER + " (" +
                MEMBER_COL_1 + " VARCHAR(10) PRIMARY KEY," +
                MEMBER_COL_2 + " VARCHAR(20)," +
                MEMBER_COL_3 + " VARCHAR(30)," +
                MEMBER_COL_4 + " VARCHAR(10)," +
                MEMBER_COL_5 + " NUMBER(5,2))");

        // Creating Book_Author table
        db.execSQL("CREATE TABLE " + TABLE_BOOK_AUTHOR + " (" +
                BOOK_AUTHOR_COL_1 + " VARCHAR(13)," +
                BOOK_AUTHOR_COL_2 + " VARCHAR(20)," +
                "PRIMARY KEY(" + BOOK_AUTHOR_COL_1 + ", " + BOOK_AUTHOR_COL_2 + ")," +
                "FOREIGN KEY(" + BOOK_AUTHOR_COL_1 + ") REFERENCES " + TABLE_BOOK + ")");

        // Creating Book_Copy table
        db.execSQL("CREATE TABLE " + TABLE_BOOK_COPY + " (" +
                BOOK_COPY_COL_1 + " VARCHAR(13)," +
                BOOK_COPY_COL_2 + " VARCHAR(5)," +
                BOOK_COPY_COL_3 + " VARCHAR(5)," +
                "PRIMARY KEY(" + BOOK_COPY_COL_3 + ", " + BOOK_COPY_COL_2 + ")," +
                "FOREIGN KEY(" + BOOK_COPY_COL_1 + ") REFERENCES " + TABLE_BOOK + "," +
                "FOREIGN KEY(" + BOOK_COPY_COL_2 + ") REFERENCES " + TABLE_BRANCH + ")");

        // Creating Book_Loan table
        db.execSQL("CREATE TABLE " + TABLE_BOOK_LOAN + " (" +
                BOOK_LOAN_COL_1 + " VARCHAR(5)," +
                BOOK_LOAN_COL_2 + " VARCHAR(5)," +
                BOOK_LOAN_COL_3 + " VARCHAR(10)," +
                BOOK_LOAN_COL_4 + " DATE," +
                BOOK_LOAN_COL_5 + " DATE," +
                BOOK_LOAN_COL_6 + " DATE," +
                "PRIMARY KEY(" + BOOK_LOAN_COL_1 + ", " + BOOK_LOAN_COL_2 + ", " +
                BOOK_LOAN_COL_3 + ", " + BOOK_LOAN_COL_4 + ")," +
                "FOREIGN KEY(" + BOOK_LOAN_COL_1 + ", " + BOOK_LOAN_COL_2 + ") REFERENCES " + TABLE_BOOK_COPY + "," +
                "FOREIGN KEY(" + BOOK_LOAN_COL_3 + ") REFERENCES " + TABLE_MEMBER + "," +
                "FOREIGN KEY(" + BOOK_LOAN_COL_2 + ") REFERENCES " + TABLE_BRANCH + ")");

        // Creating Book table
        db.execSQL("CREATE TABLE " + TABLE_BOOK + " (" +
                BOOK_COL_1 + " VARCHAR(13) PRIMARY KEY," +
                BOOK_COL_2 + " VARCHAR(30)," +
                BOOK_COL_3 + " VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUBLISHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BRANCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_COPY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK_LOAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(db);
    }

////METHOD FOR BOOK TABLE
    public boolean insertBook(String id, String title, String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_COL_1, id);
        contentValues.put(BOOK_COL_2, title);
        contentValues.put(BOOK_COL_3, publisher);
        long result = db.insert(TABLE_BOOK, null, contentValues);
        return result != -1;
    }

    public boolean updateBook(String id, String title, String publisher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_COL_2, title);
        contentValues.put(BOOK_COL_3, publisher);
        int result = db.update(TABLE_BOOK, contentValues, BOOK_COL_1 + " = ?", new String[]{id});
        return result > 0;
    }

    public boolean deleteBook(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOK, BOOK_COL_1 + " = ?", new String[]{id});
        return result > 0;
    }
////////////////////////////////////////////////////////


    ////METHOD FOR PUBLISHER TABLE
    public boolean insertPublisher(String name, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PUBLISHER_COL_1, name);
        contentValues.put(PUBLISHER_COL_2, address);
        contentValues.put(PUBLISHER_COL_3, phone);
        long result = db.insert(TABLE_PUBLISHER, null, contentValues);
        return result != -1;
    }

    public boolean updatePublisher(String name, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PUBLISHER_COL_2, address);
        contentValues.put(PUBLISHER_COL_3, phone);
        int result = db.update(TABLE_PUBLISHER, contentValues, PUBLISHER_COL_1 + " = ?", new String[]{name});
        return result > 0;
    }

    public boolean deletePublisher(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PUBLISHER, PUBLISHER_COL_1 + " = ?", new String[]{name});
        return result > 0;
    }
/////////////////////////////////////////////////////////

    ////METHOD FOR BRANCH TABLE
    public boolean insertBranch(String id, String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BRANCH_COL_1, id);
        contentValues.put(BRANCH_COL_2, name);
        contentValues.put(BRANCH_COL_3, address);
        long result = db.insert(TABLE_BRANCH, null, contentValues);
        return result != -1;
    }

    public boolean updateBranch(String id, String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BRANCH_COL_2, name);
        contentValues.put(BRANCH_COL_3, address);
        int result = db.update(TABLE_BRANCH, contentValues, BRANCH_COL_1 + " = ?", new String[]{id});
        return result > 0;
    }

    public boolean deleteBranch(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BRANCH, BRANCH_COL_1 + " = ?", new String[]{id});
        return result > 0;
    }
//////////////////////////////////////////////////////////////////



    ////METHOD FOR MEMBER TABLE
    public boolean insertMember(String cardNo, String name, String address, String phone, String unpaidDues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEMBER_COL_1, cardNo);
        contentValues.put(MEMBER_COL_2, name);
        contentValues.put(MEMBER_COL_3, address);
        contentValues.put(MEMBER_COL_4, phone);
        contentValues.put(MEMBER_COL_5, unpaidDues);
        long result = db.insert(TABLE_MEMBER, null, contentValues);
        return result != -1;
    }

    public boolean updateMember(String cardNo, String name, String address, String phone, String unpaidDues) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEMBER_COL_2, name);
        contentValues.put(MEMBER_COL_3, address);
        contentValues.put(MEMBER_COL_4, phone);
        contentValues.put(MEMBER_COL_5, unpaidDues);
        int result = db.update(TABLE_MEMBER, contentValues, MEMBER_COL_1 + " = ?", new String[]{cardNo});
        return result > 0;
    }

    public boolean deleteMember(String cardNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_MEMBER, MEMBER_COL_1 + " = ?", new String[]{cardNo});
        return result > 0;
    }
/////////////////////////////////////////////////////////////////////



    ////METHOD FOR BOOK AUTHOR TABLE
    public boolean insertBookAuthor(String bookID, String authorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_AUTHOR_COL_1, bookID);
        contentValues.put(BOOK_AUTHOR_COL_2, authorName);
        long result = db.insert(TABLE_BOOK_AUTHOR, null, contentValues);
        return result != -1;

    }

    public boolean updateBookAuthor(String bookID,String newAuthorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_AUTHOR_COL_2, newAuthorName);
        int result = db.update(TABLE_BOOK_AUTHOR, contentValues,
                BOOK_AUTHOR_COL_1 + " = ? AND " +
                        BOOK_AUTHOR_COL_2 + " = ?",
                new String[]{bookID});
        return result > 0;
    }


    public boolean deleteBookAuthor(String bookID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOK_AUTHOR, BOOK_AUTHOR_COL_1 + " = ?", new String[]{bookID});
        return result > 0;
    }
/////////////////////////////////////////////////////



    ////METHOD FOR BOOK_COPY TABLE
    public boolean insertBookCopy(String bookID, String branchID, String accessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_COPY_COL_1, bookID);
        contentValues.put(BOOK_COPY_COL_2, branchID);
        contentValues.put(BOOK_COPY_COL_3, accessNo);
        long result = db.insert(TABLE_BOOK_COPY, null, contentValues);
        return result != -1;
    }
    public boolean updateBookCopy(String bookID, String branchID, String accessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_COPY_COL_1, bookID);
        contentValues.put(BOOK_COPY_COL_2, branchID);
        int result = db.update(TABLE_BOOK_COPY, contentValues, BOOK_COPY_COL_3 + " = ?", new String[]{accessNo});
        return result > 0;
    }

    public boolean deleteBookCopy(String accessNo, String branchID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOK_COPY, BOOK_COPY_COL_3 + " = ? AND " + BOOK_COPY_COL_2 + " = ?", new String[]{accessNo, branchID});
        return result > 0;
    }
////////////////////////////////////////////////////

    ////METHOD FOR BOOK_LOAN
    public boolean insertBookLoan(String accessNo, String branchID, String cardNo, String dateOut, String dateDue, String dateReturned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_LOAN_COL_1, accessNo);
        contentValues.put(BOOK_LOAN_COL_2, branchID);
        contentValues.put(BOOK_LOAN_COL_3, cardNo);
        contentValues.put(BOOK_LOAN_COL_4, dateOut);
        contentValues.put(BOOK_LOAN_COL_5, dateDue);
        contentValues.put(BOOK_LOAN_COL_6, dateReturned);
        long result = db.insert(TABLE_BOOK_LOAN, null, contentValues);
        return result != -1;
    }
    public boolean updateBookLoan(String accessNo, String branchID, String cardNo, String dateOut, String dateDue, String dateReturned) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOK_LOAN_COL_2, branchID);
        contentValues.put(BOOK_LOAN_COL_3, cardNo);
        contentValues.put(BOOK_LOAN_COL_4, dateOut);
        contentValues.put(BOOK_LOAN_COL_5, dateDue);
        contentValues.put(BOOK_LOAN_COL_6, dateReturned);
        int result = db.update(TABLE_BOOK_LOAN, contentValues,
                BOOK_LOAN_COL_1 + " = ? AND " +
                        BOOK_LOAN_COL_2 + " = ? AND " +
                        BOOK_LOAN_COL_3 + " = ? AND " +
                        BOOK_LOAN_COL_4 + " = ?",
                new String[]{accessNo, branchID, cardNo, dateOut});
        return result > 0;
    }

    public boolean deleteBookLoan(String accessNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_BOOK_AUTHOR, BOOK_AUTHOR_COL_1 + " = ?", new String[]{accessNo});
        return result > 0;

    }
/////////////////////////////////////////


}
