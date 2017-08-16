package pl.lborowy.contactcrud;

import android.content.Context;
import android.database.Cursor;
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
    static class ViewHolder {
        TextView contactDisplayName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    }
}
