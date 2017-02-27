package org.mig.frontend;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private String contentUri = "content://org.mig.frontend.sqlprovider";
    private EditText editTextNombre, editTextEmail, editTextDireccion;
    private Button button;
    private boolean isUpdating = false;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextDireccion = (EditText) findViewById(R.id.editTextDireccion);
        button = (Button) findViewById(R.id.button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Long id = extras.getLong("id");
            if (id != null) {
                Cursor cursor = getContentResolver().query(
                        Uri.parse(contentUri + "/client/id"),   // The content URI
                        new String[]{"_id", "nombre", "direccion", "email", "telefono"},
                        "",                        // The columns to return for each row
                        new String[]{String.valueOf(id)},                     // Selection criteria
                        ""
                );

                if (cursor.getCount() > 0) {
                    isUpdating = true;
                    client = new Client();
                    cursor.moveToFirst();
                    while (cursor.isAfterLast() == false) {
                        client.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                        client.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                        client.setDireccion(cursor.getString(cursor.getColumnIndex("direccion")));
                        client.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        client.setTelefono(cursor.getString(cursor.getColumnIndex("telefono")));

                        editTextNombre.setText(client.getNombre());
                        editTextEmail.setText(client.getEmail());
                        editTextDireccion.setText(String.valueOf(client.getDireccion()));
                        button.setText("Update client");

                        cursor.moveToNext();
                    }
                }

            }
        }
    }

    public void addClient(View view) {
        ContentValues contentValues = new ContentValues();
        Uri uri = Uri.parse(contentUri);

        contentValues.put("nombre", String.valueOf(editTextNombre.getText()));
        contentValues.put("email", String.valueOf(editTextEmail.getText()));
        contentValues.put("direccion", Integer.valueOf(String.valueOf(editTextDireccion.getText())));


        if (isUpdating) {
            contentValues.put("_id", client.getId());
            contentValues.put("id_backend", client.getId_backend());

            int rows = getContentResolver().update(
                    uri,
                    contentValues,
                    null,
                    null
            );
            Toast.makeText(AddActivity.this, "Updated", Toast.LENGTH_LONG).show();
            return;
        }

        contentValues.put("id_backend", 0);

        Uri resultUri = getContentResolver().insert(
                uri,   // The content URI
                contentValues
        );

        Toast.makeText(AddActivity.this, "Inserted", Toast.LENGTH_LONG).show();

    }
}
