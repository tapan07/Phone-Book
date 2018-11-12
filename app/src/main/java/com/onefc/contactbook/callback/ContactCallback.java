package com.onefc.contactbook.callback;

import com.onefc.contactbook.model.ContactDetail;

import java.util.List;

public interface ContactCallback {

    void updateContacts(List<ContactDetail> contacts);
}
