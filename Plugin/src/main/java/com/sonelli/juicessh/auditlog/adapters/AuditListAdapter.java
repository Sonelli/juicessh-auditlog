package com.sonelli.juicessh.auditlog.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sonelli.juicessh.auditlog.R;
import com.sonelli.juicessh.pluginlibrary.PluginContract;

import java.util.UUID;

/**
 * Loads JuiceSSH plugin audit records from a cursor and provides an adapter
 * that can be used in a ListView or Spinner.
 */
public class AuditListAdapter extends CursorAdapter {

    public static final String TAG = "AuditListAdapter";

    private LayoutInflater inflater;

    /**
     * Loads JuiceSSH plugin audit records from the JuiceSSH database.
     *
     * @param context A context used to obtain a layout inflater
     */
    public AuditListAdapter(Context context) {
        super(context, null, false);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns the UUID connection ID for the item at a given position, or null if not available
     *
     * @param position The position
     * @return The UUID connection ID
     */
    public UUID getId(int position) {

        UUID id = null;

        if (getCursor() != null) {
            getCursor().moveToPosition(position);
            int idIndex = getCursor().getColumnIndex(PluginContract.PluginLog.COLUMN_ID);
            if (idIndex > -1) {
                id = UUID.fromString(getCursor().getString(idIndex));
            }
        }

        return id;

    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.audit_list_item, null, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        int nameColumn = cursor.getColumnIndex(PluginContract.PluginLog.COLUMN_PACKAGE_NAME);
        int dateColumn = cursor.getColumnIndex(PluginContract.PluginLog.COLUMN_MODIFIED);
        int messageColumn = cursor.getColumnIndex(PluginContract.PluginLog.COLUMN_MESSAGE);

        if (nameColumn > -1) {

            TextView nameView = (TextView) view.findViewById(R.id.plugin_name);
            TextView messageView = (TextView) view.findViewById(R.id.message);

            String name = cursor.getString(nameColumn);
            nameView.setText(name);

            String message = cursor.getString(messageColumn);
            messageView.setText(message);

        }

    }

}
