package org.mig.frontend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SqLiteHelper
 * La clase se encarga de inicializar la BBDD -si no existe-, de actualizarla
 *
 * @author Miguel Marzo
 *
 */
class SQLiteHelper extends SQLiteOpenHelper {

    public static final String NOMBRE_BD = "hibernatemvc.db";
    public static final int VERSION_BD = 1;
    public static final String SQLCREAR = "create table clients "+
            " (_id integer primary key autoincrement, " +
            " nombre text not null," +
            " email text not null," +
            " direccion text not null," +
            " telefono text not null," +
            " id_backend integer not null default 0);" +
            " insert into clients values(null, 'Mig', 'mig@mail', 'asdasd', '11234', 8);"+
            " insert into clients values(null, 'Mig', 'mig@mail', 'asdasd', '11234', 8);"+
            " insert into clients values(null, 'Mig', 'mig@mail', 'asdasd', '11234', 8);";
    public static final String SQLDDLDELETED = "CREATE TABLE " + "deleted" +
            " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " " + "id_backend" + " INTEGER NOT NULL);";
    public static final String SQLDDLUPDATED = "CREATE TABLE " + "updated" +
            " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " " + "nombre" + " TEXT NOT NULL," +
            " " + "email" + " TEXT NOT NULL," +
            " " + "direccion" + " TEXT NOT NULL," +
            " " + "telefono" + " TEXT NOT NULL," +
            " " + "id_backend" + " INTEGER NOT NULL DEFAULT 0);";

    /**
     * Constructor
     * llama al constructor padre
     * Le pasa el contexto, que se refiere al activity actual
     * @param contexto
     */
    SQLiteHelper(Context contexto) {
        super(contexto, NOMBRE_BD, null, VERSION_BD);
    }

    /**
     * onCreate
     * Se ejecuta en caso de que la BD no exista
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecuta la sentencia de creación de la tabla notas.
        db.execSQL("DROP TABLE IF EXISTS clients");
        db.execSQL("DROP TABLE IF EXISTS updated");
        db.execSQL("DROP TABLE IF EXISTS deleted");
        db.execSQL(SQLCREAR);
        db.execSQL(SQLDDLDELETED);
        db.execSQL(SQLDDLUPDATED);
        Log.d("DEBUG", "Ok, DB CREATED!");
    }

    /**
     * onUpgrade
     * Se ejecuta de forma automáticamente en caso de que
     * estemos en una nueva versión.
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Sacamos un log para contar lo que ha pasado
        Log.w("SqLiteHelper", "Actualizando desde versión " + oldVersion
                + " a " + newVersion + ", los datos será eliminados");

        // En este caso en el upgrade realmente
        // lo que hacemos es cargarnos lo que hay...
        db.execSQL("DROP TABLE IF EXISTS clients");
        db.execSQL("DROP TABLE IF EXISTS updated");
        db.execSQL("DROP TABLE IF EXISTS deleted");

        // ... y lo volvemos a generar
        onCreate(db);
        Log.d("DEBUG","Ok, BD regenerada");
    }

}
