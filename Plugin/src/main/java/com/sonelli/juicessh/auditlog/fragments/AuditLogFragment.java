package com.sonelli.juicessh.auditlog.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sonelli.juicessh.auditlog.R;
import com.sonelli.juicessh.auditlog.adapters.AuditListAdapter;
import com.sonelli.juicessh.auditlog.loaders.AuditListLoader;

/**
 * A fragment for displaying a list of plugin audit log records.
 */
public class AuditLogFragment extends Fragment {

    private AuditListLoader listLoader;
    private AuditListAdapter listAdapter;

    public AuditLogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_audit_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.listAdapter = new AuditListAdapter(getActivity());
        ListView listView = (ListView) getView().findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Use a Loader to load the connection list into the adapter from the JuiceSSH content provider
        // This keeps DB activity async and off the UI thread to prevent the plugin lagging
        if (listLoader == null) {
            this.listLoader = new AuditListLoader(getActivity(), listAdapter);
            getLoaderManager().initLoader(0, null, listLoader);
        } else {
            getLoaderManager().restartLoader(0, null, listLoader);
        }

    }
}
