package com.example.mugwe.events;

import static android.provider.BaseColumns._ID;
import static com.example.mugwe.events.EventsProvider.CONTENT_URI;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;
import android.net.Uri;

public class Events extends ListActivity {
    private EventsData events;
    private static final String TABLE_NAME = "myTable";
    private static final String TITLE = "eventTitle";
    private static final String TIME = "eventTime";


    @Override
   /* public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        events = new EventsData(this);
        try {
            addEvent("Hello, Android!");
            Cursor cursor = getEvents();
            showEvents(cursor);
        } finally {
            events.close();
        }
    }*/
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        addEvent("Hello, Android!" );
        Cursor cursor = getEvents();
        showEvents(cursor);
    }

//adding a row

   /* private void addEvent(String string) {
// Insert a new record into the Events data source.
// You would do something similar for delete and update.
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(TITLE, string);
        db.insertOrThrow(TABLE_NAME, null, values);
    }*/
   private void addEvent(String string) {
       final String AUTHORITY = "com.example.events" ;
       final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
// Insert a new record into the Events data source.
// You would do something similar for delete and update.
       ContentValues values = new ContentValues();
       values.put(TIME, System.currentTimeMillis());
       values.put(TITLE, string);
       getContentResolver().insert(CONTENT_URI, values);
   }

// running a query

   private static String[] FROM = {_ID, TIME, TITLE,};
    private static String ORDER_BY = TIME + " DESC";

   /* private Cursor getEvents() {
// Perform a managed query. The Activity will handle closing
// and requerying the cursor when needed.
        SQLiteDatabase db = events.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);


        startManagingCursor(cursor);
        return cursor;
    }*/

  private Cursor getEvents(){
      // Perform a managed query. The Activity will handle closing
      // and re-querying the cursor when needed.

      return managedQuery(CONTENT_URI, FROM, null,null, ORDER_BY);
  }

// displaying query results

  /**  private void showEvents(Cursor cursor) {
        // Stuff them all into a big string
        StringBuilder builder = new StringBuilder("Saved events:\n");
        while (cursor.moveToNext()) {
            // Could use getColumnIndexOrThrow() to get indexes
            long id = cursor.getLong(0);
            long time = cursor.getLong(1);
            String title = cursor.getString(2);
            builder.append(id).append(": ");
            builder.append(time).append(": ");
            builder.append(title).append("\n");
        }
        // Display on the screen
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(builder);
    }
*/

//data binding

    private static int[] TO = {R.id.rowid, R.id.time, R.id.title,};

    private void showEvents(Cursor cursor) {
// Set up data binding
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.item, cursor, FROM, TO);
        setListAdapter(adapter);
    }

}



