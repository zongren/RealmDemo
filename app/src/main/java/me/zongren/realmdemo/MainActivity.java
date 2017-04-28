package me.zongren.realmdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Contact> list = new ArrayList<>();
        adapter = new ContactAdapter(list);
        adapter.listener = new ContactAdapter.OnContactClickListener() {
            @Override
            public void onClickContact(Contact contact) {
                openContactActivity(contact.getId(), 1);
            }
        };
        recyclerView.setAdapter(adapter);
        updateAdapter();
    }

    private void openContactActivity(String id, int requestCode) {
        Intent intent = new Intent(this, ContactActivity.class);
        if (!TextUtils.isEmpty(id)) {
            intent.putExtra("id", id);
        }
        startActivityForResult(intent, requestCode);
    }

    private void updateAdapter() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Collection<Contact> contacts = realm.where(Contact.class).findAll();
                adapter.contacts.clear();
                adapter.contacts.addAll(contacts);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    onUpdateContact(data);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    onAddContact(data);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onAddContact(Intent data) {
        updateAdapter();
    }

    private void onUpdateContact(Intent data) {
        updateAdapter();
    }

    @Override
    protected void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                openContactActivity(null, 2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
