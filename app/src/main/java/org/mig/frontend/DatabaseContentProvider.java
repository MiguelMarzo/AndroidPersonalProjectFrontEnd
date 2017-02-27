package org.mig.frontend;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class DatabaseContentProvider extends ContentProvider {

    // We set uriMatcher to get params passed to URIs.
    // So we can give different values depending on those params
    private  UriMatcher uriMatcher;
    // Our data:
    private MatrixCursor mCursor;
    private DbAdapter dbAdapter;
    /**
     * default constructor.
     */
    public DatabaseContentProvider() {

    }

    /**
     * called when provider is started, so we use it to initialize data.
     */
    @Override
    public boolean onCreate() {
        Log.d("MigDebug","CP> onCreate, init data.");
        dbAdapter = new DbAdapter(getContext());
        dbAdapter.open();

        initUris();
        return true;
    }
    /**
     * init content provider Uris
     * we set some kind of uri patterns to route them to different queries
     */
    private void initUris() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // This will match: content://org.mig.frontend_personalproject.sqlprovider.clients/clients
        uriMatcher.addURI("org.mig.frontend.sqlprovider.clients", "clients/", 1);

        // This will match: content://org.mig.frontend_personalproject.sqlprovider.clients/clients/2
        uriMatcher.addURI("org.mig.frontend.sqlprovider.clients", "clients/*/", 2);
    }




    /**
     * we query the database, depending on uriMatcher we can execute
     * different queries.
     * The parameters of the query are the same of a SQLite helper query.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Log.d("MigDebug","CP> query " + uri+ " match:" + uriMatcher.match(uri));
        switch (uriMatcher.match(uri)) {
            case 1:
                Log.d("MigDebug","query to 1. ");
                return dbAdapter.obtenerClientes();
            case 2:
                Log.d("MigDebug","query to 2. " + uri.getLastPathSegment());
                break;
            default:	break;
        }
        return mCursor;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("MigDebug","CP> " + uri);
        //return dbAdapter.borrarCliente(Client c);
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        Log.d("MigDebug","CP> " + uri);
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d("MigDebug","CP> insert " + uri);

        //dbAdapter.insertarCliente(values.getAsString("client"));

        Uri resultUri = Uri.parse("content://org.mig.frontend.sqlprovider.clients/1");
        return resultUri;

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d("MigDebug","CP> " + uri);


        return 0;

    }
}

