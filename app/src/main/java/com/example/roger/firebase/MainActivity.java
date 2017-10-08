package com.example.roger.firebase;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    //Para leer y escribir en la base de datos, necesitas una instancia de DatabaseReference:
    DatabaseReference myRef = database.getReference();

    EditText mostrar,escribir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostrar = (EditText) findViewById(R.id.ed1);
        escribir = (EditText) findViewById(R.id.ed2);
    }

    public  void EscribirFireBase(View f){

        // Write a message to the database
        //myRef.setValue("Hello, World!");
        String valor = escribir.getText().toString().trim();
        if(valor.matches("")){
            Toast.makeText(this,"Debe escribir algo para guardad",Toast.LENGTH_LONG).show();
        }else{
            myRef.child("chats").child("one").child("title").setValue(valor);
        }

    }


    public  void LeerFirebase(View g){
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                //HashMap <String,Map<String,String>> value =dataSnapshot.getValue(HashMap.class);
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                mostrar.setText(""+value.size()+" "+value.containsKey("chats"));
                mostrar.append("  "+value.values());

                //Log.d("","Values is "+value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.v("","Failes to read value",error.toException());
                mostrar.setText(""+error.toException());
            }
        });

    }

}
