package org.mig.frontend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DbAdapter {

    // Este objeto nos permite meterle mano a SQLite
    private SQLiteDatabase db;

    // Aquí tenemos nuestro SqliteHelper que se encarga de crear y actualizar
    private SQLiteHelper dbHelper;

    // El contexto nos servirá para referirnos a la Activity en la que estamos
    private final Context contexto;

    /**
     * DbAdapter
     * Constructor de la clase
     * @param contexto Será la activity que usa esta clase
     */
    public DbAdapter(Context contexto) {
        this.contexto = contexto;
    }


    /**
     * open
     * Usa el SqLiteHelper para encargase de abrir la conexión.
     * El SqLiteHelper lo primero que hará es crear la BD si no existía.
     * @return Devuelve un objeto de clase SQLiteDatabase para gestionar la BD
     * @throws SQLException
     */
    public SQLiteDatabase open() throws SQLException {
        // Crea un objeto asistente de base de datos de clase SqLiteHelper.
        dbHelper = new SQLiteHelper(contexto);

        // Abre la base de datos en modo escritura (lectura permitida).
        db = dbHelper.getWritableDatabase();

        Log.d("DEBUG","BD obtenida: " + db.toString());

        // Devuelve el objeto de tipo SQLiteDatabase.
        return db;
    }

    /**
     * close
     * Cierra la base de datos mediante el dbHelper
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * insertarCliente
     * Inserta un registro con los campos titulo y cuerpo en la base de datos.
     *
     * @param client
     * @return Devuelve el número de registro insertado 0 -1 en caso de error
     */
    public long insertarCliente(Client client) {
        // Creamos un registro
        ContentValues registro = new ContentValues();

        // Agrega los datos.
        //registro.put("_id", client.getId());
        registro.put("direccion", client.direccion);
        registro.put("email", client.email);
        registro.put("nombre", client.nombre);
        registro.put("telefono", client.telefono);
        registro.put("id_backend", client.getId_backend());
        Log.d("MigDebug","ESTOY APUNTO DE INSERTAR EN BD LOCAL");

        // Inserta el registro y devuelve el resultado.
        return db.insert("clients", null, registro);
    }

    /**
     * borrarCliente
     * Borra el registro que tiene el id especificado.
     *
     * @param idCliente id del registro a borrar
     * @return Devuelve el nº de registros afectados.
     */
    public int deleteClient(long idCliente) {
        addToDeleted(idCliente);
        return db.delete("clients", "_id" + "=?", new String[]{String.valueOf(idCliente)});
    }
    private void addToDeleted(long id) {
        Cursor cursor = db.query(true, "clients", new String[]{"id_backend"}, "_id" + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int id_backend = cursor.getInt(cursor.getColumnIndex("id_backend"));

        ContentValues row = new ContentValues();
        row.put("id_backend", id_backend);

        db.insert("deleted", null, row);
    }


    public int deleteUpdated() {
        return db.delete("updated", null, null);
    }

    /**
     * obtenerClientes
     * Obtiene todos los registros de la tabla todo.
     *
     * @return Cursor Devuelve un cursor con los registros obtenidos.
     */
    public Cursor obtenerClientes() {
        Log.d("DEBUG","OBTENER CLIENTES");
        return db.query("clients", new String[] {"_id","nombre", "email", "direccion", "telefono", "id_backend"}, null, null, null, null, null);

    }

    /**
     * obtenerCliente
     * Obtiene el registro que tiene el id especificado.
     *
     * @param idCliente id del registro que se quiere obtener.
     * @return Cursor un cursor con el registro obtenido.
     * @throws SQLException
     */
    public Cursor obtenerCliente (long idCliente) throws SQLException {
        Cursor row = db.query(true, "clients", new String[]{"_id", "nombre", "email", "direccion", "telefono", "id_backend"},
                "_id" + "=?", new String[]{String.valueOf(idCliente)}, null, null, null, null);

        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }

    /**
     * actualizarCliente
     * Hace un UPDATE de los valores del registro cuyo id es idCliente.
     *
     * @param  idCliente id del registro que se quiere modificar.
     * @param  client
     * @return int cantidad registros han sido afectados.
     */
    public int actualizarCliente(long idCliente, Client client) {
        addToUpdated(idCliente, client);
        // Creamos un registro
        ContentValues cliente = new ContentValues();

        // Agrega los datos.
        cliente.put("direccion", client.direccion);
        cliente.put("email", client.email);
        cliente.put("nombre", client.nombre);
        cliente.put("telefono", client.telefono);
        //cliente.put("id_backend", client.getId_backend());

        // Inserta el registro y devuelve el resultado.
        return db.update("clients", cliente,
                "_id=" + idCliente, null);
    }

    private long addToUpdated(long id, Client client) {
        ContentValues row = new ContentValues();
        row.put("nombre", client.getNombre());
        row.put("_id", id);
        row.put("email", client.getEmail());
        row.put("telefono", client.getTelefono());
        row.put("direccion", client.getDireccion());
        row.put("id_backend", client.getId_backend());

        return db.insert("updated", null, row);
    }
    public Cursor getDeleted() {
        return db.query("deleted", new String[]{"id_backend"}, null, null, null, null, null);
    }

    public Cursor getLastBackendRow() throws SQLException {
        Cursor row = db.query(true, "clients", new String[]{"_id", "nombre", "direccion", "email", "telefono", "id_backend"},
                null, null, null, null, "id_backend" + " DESC", " 1");
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }

    public Cursor getLastLocalRow() throws SQLException {
        Cursor row = db.query(true, "clients", new String[]{"_id", "nombre", "direccion", "email", "telefono", "id_backend"},
                "id_backend" + " = 0", null, null, null, null, null); // limit 1 ???

        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }

    public Cursor getUpdated() {
        return db.query("updated", new String[]{"_id", "nombre", "email", "direccion", "telefono", "id_backend"}, null, null, null, null, null);
    }

    public int deleteDeleted() {
        return db.delete("deleted", null, null);
    }

}
