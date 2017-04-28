package me.zongren.realmdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import io.realm.Realm;

/**
 * Created by zongren on 2017/4/28.
 */

public class ContactActivity extends AppCompatActivity {
    private EditText nameText;
    private EditText ageText;
    private String id;
    private Realm realm;
    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        nameText = (EditText) findViewById(R.id.nameEditText);
        ageText = (EditText) findViewById(R.id.ageEditText);

        if (getIntent() != null) {
            id = getIntent().getStringExtra("id");
        }

        realm = Realm.getDefaultInstance();
        if (TextUtils.isEmpty(id)) {
            deleteButton.setVisibility(View.GONE);
            deleteButton.setEnabled(false);
            addButton.setText("Add");
            setTitle("Add Contact");
        } else {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setEnabled(true);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    contact = realm.where(Contact.class).equalTo("id", id).findFirst();
                    nameText.setText(contact.getName());
                    ageText.setText(contact.getAge());
                }
            });
            setTitle("Update Contact");
            addButton.setText("Update");
        }

    }

    private void add() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (contact != null) {
                    contact.setAge(ageText.getText().toString());
                    contact.setName(nameText.getText().toString());
                } else {
                    contact = realm.createObject(Contact.class, UUID.randomUUID().toString());
                    contact.setAge(ageText.getText().toString());
                    contact.setName(nameText.getText().toString());
                }
            }
        });
        setResult(RESULT_OK);
        finish();
    }

    private void delete() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Contact.class).equalTo("id", id).findAll().deleteFirstFromRealm();
            }
        });
        setResult(RESULT_OK);
        finish();
    }
}
