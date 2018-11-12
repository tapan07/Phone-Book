package com.onefc.contactbook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onefc.contactbook.adapter.CustomRecyclerAdapter;
import com.onefc.contactbook.callback.ContactCallback;
import com.onefc.contactbook.model.ContactDetail;
import com.onefc.contactbook.presenter.ContactPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        ContactCallback {

    private static int LOADER_CONTACTS = 100;
    private static final int PERMISSION_CONTACTS = 200;


    private static final String[] PROJECTION =
            {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};


    private RecyclerView mContactsList;
    private CustomRecyclerAdapter mCursorAdapter;

    private List<ContactDetail> mContacts;

    private ContactPresenter mPresenter;

    public ContactFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new ContactPresenter(this);
        setUPAdapter(view);
        checkPermission();
    }

    private void setUPAdapter(View view) {
        mContactsList = (RecyclerView) view.findViewById(R.id.list_contacts);
        mContacts = new ArrayList<>();
        mCursorAdapter = new CustomRecyclerAdapter(mContacts);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mContactsList.setLayoutManager(llm);
        mContactsList.setAdapter(mCursorAdapter);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_CONTACTS);
        } else {
            LoaderManager.getInstance(getActivity()).initLoader(LOADER_CONTACTS, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == LOADER_CONTACTS) {
            return new CursorLoader(getContext(), ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, null);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                boolean isValid = mPresenter.isValidData(number);
                if (isValid) {
                    ContactDetail contactDetail = new ContactDetail(name, number);
                    mContacts.add(contactDetail);
                }
            }
        }
        mCursorAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mContacts.clear();
        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateContacts(List<ContactDetail> contacts) {
        mContacts = contacts;
        mCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CONTACTS) {
            LoaderManager.getInstance(getActivity()).initLoader(LOADER_CONTACTS, null, this);
        }
    }
}
