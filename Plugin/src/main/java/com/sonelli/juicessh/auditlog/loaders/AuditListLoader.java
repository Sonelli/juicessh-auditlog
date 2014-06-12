package com.sonelli.juicessh.auditlog.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.sonelli.juicessh.auditlog.adapters.AuditListAdapter;
import com.sonelli.juicessh.pluginlibrary.PluginContract;

public class AuditListLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private AuditListAdapter adapter;

    /**
     * Creates a {@link android.support.v4.content.Loader} to fetch all plugin
     * audit items from the database on a background thread (similar to an {@link android.os.AsyncTask}.
     * Once the connections are loaded it will populate the associated listview/spinner adapter.
     *
     * @param context
     * @param adapter
     */
    public AuditListLoader(Context context, AuditListAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        return new CursorLoader(
                context,
                PluginContract.PluginLog.CONTENT_URI,
                PluginContract.PluginLog.PROJECTION,
                null,
                null,
                PluginContract.PluginLog.SORT_ORDER_DEFAULT
        );

    }

    /**
     * Swaps out the associated adapter's cursor for a populated one
     * once the loader has fetched all of the connections from the DB
     *
     * @param cursorLoader
     * @param cursor
     */
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (adapter != null) {
            adapter.swapCursor(cursor);
        }
    }

    /**
     * Flip back to the original state before connections were loaded
     * and set the associated adapter's cursor to null.
     *
     * @param cursorLoader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        if (adapter != null) {
            adapter.swapCursor(null);
        }
    }
}
