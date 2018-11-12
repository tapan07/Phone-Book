package com.onefc.contactbook.presenter;

import android.support.v4.app.Fragment;

import com.onefc.contactbook.ContactFragment;
import com.onefc.contactbook.callback.ContactCallback;
import com.onefc.contactbook.model.ContactDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactPresenter {
    private static final int NUMBER_TEN = 10;
    private static final int NUMBER_ELEVEN = 11;
    private static final int NUMBER_TWELVE = 12;
    private static String pattern = "[^0-9]";
    private Map<String, Boolean> mNumberStatus;
    List<ContactDetail> contacts;

    private ContactCallback mCallback;

    public ContactPresenter(Fragment fragment) {
        mCallback = (ContactFragment) fragment;
        mNumberStatus = new HashMap<>();
        contacts = new ArrayList<>();
    }


    private boolean hasDuplicate(String number) {
        if (mNumberStatus.containsKey(number)) {
            return true;
        }
        mNumberStatus.put(number, true);
        return false;
    }

    private boolean isValidNumber(int length) {
        return length == NUMBER_TEN || length == NUMBER_ELEVEN || length == NUMBER_TWELVE;
    }

    public boolean isValidData(String number) {
        number = number.replaceAll(pattern, "");
        int length = number.length();
        if (isValidNumber(length) && !hasDuplicate(number)) {
            return true;
        }
        return false;
    }
}
