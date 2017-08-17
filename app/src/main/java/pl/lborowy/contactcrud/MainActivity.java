package pl.lborowy.contactcrud;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.*;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int CONTACTS_REQUEST_CODE = 997;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sprawdzanie pozwoleń
        // permission granted - uznane pozwolenie
        // jeśli ma pozwolenie, to ok
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            inflateLayout();
        }
        // jak nie, to zapyta o pozwolenie
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, CONTACTS_REQUEST_CODE);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CONTACTS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    inflateLayout();
                }
                else {
                    finish();
                }
            }
            break;
        }
    }

    private Cursor getContactsCursor() {
        Uri uri = RawContacts.CONTENT_URI;
        // from projection
        String[] projection = {RawContacts._ID, Contacts.DISPLAY_NAME};
        // where=?
        String selection = RawContacts.DELETED + "=?";
        // mapowanie argumentów - pierwszy do pierwszego ?, drugi do drugiego ?...
        String[] selectionArgs = {Integer.toString(0)};
        // sortowanie po nazwie
        String sortOrder = Contacts.DISPLAY_NAME + " " + "ASC";
        // zwracanie kursora
        return getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
    }

    private void inflateLayout() {
//        Toast.makeText(this, "Permissions GOOD", Toast.LENGTH_SHORT).show();
        Cursor contactsCursor = getContactsCursor();
        // ustawia kursor w odpowiednim miejscu
        startManagingCursor(contactsCursor);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ContactsListAdapter(this, R.layout.row, contactsCursor, new String[0], new int[0], 0));

    }
}
