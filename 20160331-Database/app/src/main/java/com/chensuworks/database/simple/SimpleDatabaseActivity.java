package com.chensuworks.database.simple;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;

import com.chensuworks.database.R;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class SimpleDatabaseActivity extends Activity {

    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_database);

        runDatabaseExample();
    }

    public void runDatabaseExample() {
        if (Arrays.binarySearch(databaseList(), "test.db") >= 0) {
            deleteDatabase("test.db");
        }

        mDatabase = openOrCreateDatabase("test.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        mDatabase.setLocale(Locale.getDefault());
        mDatabase.setVersion(1);

        Log.d(TAG, "Created database: " + mDatabase.getPath());
        Log.d(TAG, "Database Verison: " + mDatabase.getVersion());
        Log.d(TAG, "Database Page Size: " + mDatabase.getPageSize());
        Log.d(TAG, "Database Max Size: " + mDatabase.getMaximumSize());

        Log.d(TAG, "Database Open? " + mDatabase.isOpen());
        Log.d(TAG, "Database ReadOnly? " + mDatabase.isReadOnly());
        Log.d(TAG, "Database locked by current thread? " + mDatabase.isDbLockedByCurrentThread());

        // Create table using execSQL()
        mDatabase.execSQL("CREATE TABLE tbl_authors (id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, lastname TEXT);");

        // Create table using SQLiteStatement.execute()
        SQLiteStatement sqLiteStatement = mDatabase.compileStatement("CREATE TABLE tbl_books (id INTEGER PRIMARY KEY AUTOINCREMENT, title NEXT, dateadded DATE, authorid INTEGER NOT NULL CONSTRAINT authorid REFERENCES tbl_authors(id) ON DELETE CASCADE);");
        sqLiteStatement.execute();

        // Create SQL triggers to enforce foreign key constraints
        mDatabase.execSQL("CREATE TRIGGER fk_insert_book BEFORE INSERT ON tbl_books FOR EACH ROW BEGIN SELECT RAISE(ROLLBACK, 'insert on table \"tbl_books\" violates foreign key constraint \"fk_authorid\"') WHERE (SELECT id FROM tbl_authors WHERE id = NEW.authorid) IS NULL; END;");
        mDatabase.execSQL("CREATE TRIGGER fk_update_book BEFORE UPDATE ON tbl_books FOR EACH ROW BEGIN SELECT RAISE(ROLLBACK, 'update on table \"tbl_books\" violates foreign key constraint \"fk_authorid\"') WHERE  (SELECT id FROM tbl_authors WHERE id = NEW.authorid) IS NULL; END;");
        mDatabase.execSQL("CREATE TRIGGER fk_delete_author BEFORE DELETE ON tbl_authors FOR EACH ROW BEGIN SELECT RAISE(ROLLBACK, 'delete on table \"tbl_authors\" violates foreign key constraint \"fk_authorid\"') WHERE (SELECT authorid FROM tbl_books WHERE authorid = OLD.id) IS NOT NULL; END;");

        // Add records within a transaction
        addSomeBooks();

        // SELECT * FROM tbl_books;
        Cursor cursor = mDatabase.query("tbl_books", null, null, null, null, null, null);
        logCursorInfo(cursor);
        cursor.close();

        // SELECT title, id
        // FROM tbl_books
        // ORDER BY title ASC;
        String asColumnsToReturn2[] = {"title", "id"};
        String strSortOrder2 = "title ASC";
        cursor = mDatabase.query("tbl_books", asColumnsToReturn2, null, null, null, null, strSortOrder2);
        logCursorInfo(cursor);
        cursor.close();

        // SELECT tbl_books.title, tbl_books.id, tbl_authors.firstname, tbl_authors.lastname, tbl_books.authorid
        // FROM tbl_books INNER JOIN tbl_authors
        // on tbl_books.authorid=tbl_authors.id
        // ORDER BY title ASC;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("tbl_books, tbl_authors");
        queryBuilder.appendWhere("tbl_books.authorid=tbl_authors.id");
        String asColumnsToReturn[] = {"tbl_books.title", "tbl_books.id", "tbl_authors.firstname", "tbl_authors.lastname", "tbl_books.authorid"};
        String strSortOrder = "title ASC";
        cursor = queryBuilder.query(mDatabase, asColumnsToReturn, null, null, null, null, strSortOrder);
        logCursorInfo(cursor);
        cursor.close();

        // SELECT tbl_books.title, tbl_books.id, tbl_authors.firstname, tbl_authors.lastname, tbl_books.authorid
        // FROM tbl_books INNER JOIN tbl_authors
        // on tbl_books.authorid=tbl_authors.id
        // WHERE title LIKE '%Prince%'
        // ORDER BY title ASC;
        SQLiteQueryBuilder queryBuilder2 = new SQLiteQueryBuilder();
        queryBuilder2.setTables("tbl_books, tbl_authors");
        queryBuilder2.appendWhere("(tbl_books.authorid=tbl_authors.id) AND (tbl_books.title LIKE '%Prince%')");
        cursor = queryBuilder2.query(mDatabase, asColumnsToReturn, null, null, null, null, strSortOrder);
        logCursorInfo(cursor);
        cursor.close();

        // SELECT title AS Name, 'tbl_books' AS OriginalTable
        // FROM tbl_books
        // WHERE Name LIKE '%ow%'
        // UNION
        // SELECT (firstname||' '||lastname) AS Name, 'tbl_authors' AS OriginalTable
        // FROM tbl_authors
        // WHERE Name LIKE '%ow%'
        // ORDER BY Name ASC;
        String sqlUnionExample = "SELECT title AS Name, 'tbl_books' AS OriginalTable from tbl_books WHERE Name LIKE ? UNION SELECT (firstname||' '|| lastname) AS Name, 'tbl_authors' AS OriginalTable from tbl_authors WHERE Name LIKE ? ORDER BY Name ASC;";
        cursor = mDatabase.rawQuery(sqlUnionExample, new String[] {"%ow%", "%ow%"});
        logCursorInfo(cursor);
        cursor.close();

        // SELECT * FROM tbl_books WHERE id=9;
        cursor = mDatabase.query("tbl_books", null, "id=?", new String[] {"9"}, null, null, null);
        logCursorInfo(cursor);
        cursor.close();

        // Update Le Petit Prince Book to new title 'The Little Prince' (Book Record Id 9)
        updateBookTitle("The Little Prince", 9);

        // SELECT * FROM tbl_books WHERE id=9;
        cursor = mDatabase.query("tbl_books", null, "id=?", new String[] {"9"}, null, null, null);
        logCursorInfo(cursor);
        cursor.close();

        // Delete Stephen Colbert (Author Record Id 2)
        try {
            deleteAuthor(2);
        } catch (SQLiteConstraintException e) {
            Log.d(TAG, "Error Code 19 is Constraint Violated. Colbert still has books!");
        } catch (Exception e) {
            Log.d(TAG, "Delete failed; perhaps a commit failed.");
        }

        // Deletes done in the right order to avoid constraint violations will succeed
        deleteBook(8); // Delete Colbert's book
        deleteAuthor(2); // Delete Colbert
        deleteBooksByAuthor(1); // Delete all books by J.K. Rowling

        // SELECT * FROM tbl_books
        cursor = mDatabase.query("tbl_books", null, null, null, null, null, null);
        logCursorInfo(cursor);
        cursor.close();

        // Drop the tables
        mDatabase.execSQL("DROP TABLE tbl_books;");
        mDatabase.execSQL("DROP TABLE tbl_authors;");

        // Drop the triggers
        mDatabase.execSQL("DROP TRIGGER IF EXISTS fk_insert_book;");
        mDatabase.execSQL("DROP TRIGGER IF EXISTS fk_update_book;");
        mDatabase.execSQL("DROP TRIGGER IF EXISTS fk_delete_author;");

        // Close the database
        mDatabase.close();

        // Delete the database file itself
        deleteDatabase("test.db");
    }

    public void logCursorInfo(Cursor cursor) {
        String rowHeaders = "|| ";

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            rowHeaders = rowHeaders.concat(cursor.getColumnName(i) + " || ");
        }
        // rowHeaders = "|| id || title || dateadded || authorid ||";

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            String rowResults = "|| ";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                rowResults = rowResults.concat(cursor.getString(i) + " || ");
            }
            cursor.moveToNext();
        }
    }

    public void addSomeBooks() {
        mDatabase.beginTransaction();

        try {
            Date today = new Date(System.currentTimeMillis());

            Author author = new Author("J.K.", "Rowling");
            addAuthor(author);
            addBook(new Book("Harry Potter and the Sorcerer's Stone", today, author));
            addBook(new Book("Harry Potter and the Chamber of Secrets", today, author));
            addBook(new Book("Harry Potter and the Prisoner of Azkaban", today, author));
            addBook(new Book("Harry Potter and the Goblet of Fire", today, author));
            addBook(new Book("Harry Potter and the Order of the Phoenix", today, author));
            addBook(new Book("Harry Potter and the Half-Blood Prince", today, author));
            addBook(new Book("Harry Potter and the Deathly Hallows", today, author));

            Author author2 = new Author("Stephen", "Colbert");
            addAuthor(author2);
            addBook(new Book("I Am America (And So Can You!)", today, author2));

            Author author3 = new Author("Antoine", "de Saint-Exupery");
            addAuthor(author3);
            addBook(new Book("Le Petit Prince", today, author3));

            mDatabase.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            mDatabase.endTransaction();
        }
    }

    public void addBook(Book newBook) {
        ContentValues values = new ContentValues();
        values.put("title", newBook.mTitle);
        values.put("dateadded", newBook.mDateAdded.toString());
        values.put("authorid", newBook.mAuthor.mAuthorId);
        newBook.mBookId = mDatabase.insertOrThrow("tbl_books", null, values);
    }

    public void addAuthor(Author newAuthor) {
        ContentValues values = new ContentValues();
        values.put("firstname", newAuthor.mFirstName);
        values.put("lastname", newAuthor.mLastName);
        newAuthor.mAuthorId = mDatabase.insertOrThrow("tbl_authors", null, values);
    }

    public void updateBookTitle(String newTitle, Integer bookId) {
        ContentValues values = new ContentValues();
        values.put("title", newTitle);
        mDatabase.update("tbl_books", values, "id=?", new String[] {bookId.toString()});
    }

    public void deleteBook(Integer bookId) {
        mDatabase.delete("tbl_books", "id=?", new String[]{bookId.toString()});
    }

    public void deleteBooksByAuthor(Integer authorID) {
        int numBooksDeleted = mDatabase.delete("tbl_books", "authorid=?", new String[] {authorID.toString()});
    }

    public void deleteAuthor(Integer authorId) {
        mDatabase.delete("tbl_authors", "id=?", new String[] {authorId.toString()});
    }

    public class Author {
        private String mFirstName;
        private String mLastName;
        private long mAuthorId;

        public Author(String firstName, String lastName) {
            mFirstName = firstName;
            mLastName = lastName;
            mAuthorId = -1;
        }
    }

    public class Book {
        private String mTitle;
        private Date mDateAdded;
        private long mBookId;
        private Author mAuthor;

        public Book(String title, Date dateAdded, Author author) {
            mTitle = title;
            mDateAdded = dateAdded;
            mAuthor = author;
            mBookId = -1;
        }
    }
}
