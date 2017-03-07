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
    private Cursor mCursor;
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

        // This will match: content://org.mig.frontend_personalproject.sqlprovider/clients
        uriMatcher.addURI("org.mig.frontend.sqlprovider", "clients/", 1);

        // This will match: content://org.mig.frontend_personalproject.sqlprovider/clients/id
        uriMatcher.addURI("org.mig.frontend.sqlprovider", "clients/id", 2);

        // the last one from the backend
        // This will match: content://org.mig.frontend_personalproject.sqlprovider/clients/last/backend
        uriMatcher.addURI("org.mig.frontend.sqlprovider", "clients/last/backend", 3);

        // This will match:  content://org.mig.frontend_personalproject.sqlprovider/clients/last/local
        uriMatcher.addURI("org.mig.frontend.sqlprovider", "clients/last/local", 4);

        // This will match: content://org.mig.frontend_personalproject.sqlprovider/deleted
        uriMatcher.addURI("org.mig.frontend.sqlprovider", "deleted", 5);

        // This will match: content://org.mig.frontend_personalproject.sqlprovider/delete/deleted
        uriMatcher.addURI("org.mig.frontend.sqlprovider","delete/deleted", 6);

        // This will match: content://org.mig.frontend_personalproject.sqlprovider/delete/currencies
        uriMatcher.addURI("org.mig.frontend.sqlprovider", "delete/clients", 7);

        // This will match: content://org.mig.frontend_personalproject.sqlprovider/delete/updated
        uriMatcher.addURI("org.mig.frontend.sqlprovider", "delete/updated", 8);

        // This will match: content://org.mig.frontend_personalproject.sqlprovider/updated
        uriMatcher.addURI("org.mig.frontend.sqlprovider", "updated", 9);
    }

    /**
     * we query the database, depending on uriMatcher we can execute
     * different queries.
     * The parameters of the query are the same of a SQLite helper query.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor c;
        Log.d("MigDebug","CP> query " + uri+ " match:" + uriMatcher.match(uri));
        switch (uriMatcher.match(uri)) {
            case 1:
                Log.d("MigDebug", "query to 1. ");
                mCursor = dbAdapter.obtenerClientes();
                break;
            case 2:
                Log.d("MigDebug", "query to 2. " + uri.getLastPathSegment());
                return dbAdapter.obtenerCliente(Long.valueOf(selectionArgs[0]));
            case 3:
                Log.d("MigDebug", "query to 3. " + uri.getLastPathSegment());
                c = dbAdapter.getLastBackendRow();
                return c;
            case 4:
                Log.d("MigDebug", "query to 4. " + uri.getLastPathSegment());
                c = dbAdapter.getLastLocalRow();
                return c;
            case 5:
                Log.d("MigDebug", "query to 5. " + uri.getLastPathSegment());
                c = dbAdapter.getDeleted();
                return c;
            case 9:
                Log.d("MigDebug", "query to 9. " + uri.getLastPathSegment());
                c = dbAdapter.getUpdated();
                return c;
            default:
                break;

        }
        return mCursor;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d("DEBUG", "ContentProvider> delete " + uri + " match:" + uriMatcher.match(uri));
        // Implement this to handle requests to delete one or more rows.
        int rows = 0;
        switch (uriMatcher.match(uri)) {
            case 6:
                rows = dbAdapter.deleteDeleted();
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            case 7:
                rows = dbAdapter.deleteClient(Integer.valueOf(selectionArgs[0]));
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            case 8:
                rows = dbAdapter.deleteUpdated();
                getContext().getContentResolver().notifyChange(uri, null);
                return rows;
            default:
                break;
        }
        return rows;

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
        Client client = new Client();
        client.setId(values.getAsInteger("_id"));
        client.setNombre(values.getAsString("name"));
        client.setDireccion(values.getAsString("direccion"));
        client.setTelefono(values.getAsString("telefono"));
        client.setEmail(values.getAsString("email"));
        client.setId_backend(values.getAsInteger("_id"));
        dbAdapter.insertarCliente(client);
        getContext().getContentResolver().notifyChange(uri, null);
        Uri resultUri = Uri.parse("content://org.mig.frontend.sqlprovider" + "clients");
        return resultUri;

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d("MigDebug","CP> " + uri);


        return 0;

    }
}

