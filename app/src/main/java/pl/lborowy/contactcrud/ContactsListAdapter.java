package pl.lborowy.contactcrud;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by RENT on 2017-08-16.
 */

public class ContactsListAdapter extends SimpleCursorAdapter {

    private final Context context;
    private final Cursor cursor;

    public ContactsListAdapter(Context context, int layout, Cursor cursor, String[] from, int[] to, int flags) {
        super(context, layout, cursor, from, to, flags);
        // context i cursor dać do zmiennych
        this.context = context;
        this.cursor = cursor;
    }

    // mechanizm viewHolder'a (musi być klasa statyczna)
    // łapie referencje tylko do tego co jest na ekranie, a nie poza nim
    static class ViewHolder {
        TextView contactDisplayName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        if (convertView == null) {
            // pobieramy View
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.row, null, false);

            viewHolder = new ViewHolder();
            viewHolder.contactDisplayName = rowView.findViewById(R.id.contactName);
            // przypisanie viewHolder do widoku
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // kursor przeniesiony na odpowiednią pozycję
        cursor.moveToPosition(position);
        // teraz możemy brać odpowiednie rzeczy
//        viewHolder.contactDisplayName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        String columnName = ContactsContract.Contacts.DISPLAY_NAME;
        int columnIndex = cursor.getColumnIndex(columnName);
        viewHolder.contactDisplayName.setText(cursor.getString(columnIndex));

        return rowView;


    }
}
