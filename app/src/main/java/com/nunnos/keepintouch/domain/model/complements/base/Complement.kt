package com.nunnos.keepintouch.domain.model.complements.base

import com.nunnos.keepintouch.utils.Constants.CONTACTS_SEPARATOR

open class Complement {
    open var id = -1
    open var date = ""
    open var isImportant = false

    open fun isEmpty(): Boolean {
        return true
    }

    fun addContactsFirstTime(contacts: String): String {
        return if (contacts.startsWith(CONTACTS_SEPARATOR)) {
            contacts
        } else {
            CONTACTS_SEPARATOR + contacts
        }
    }

    fun removeContact(contactId: Int, savedContacts: String): String {
        if (!containsContact(contactId, savedContacts)) return savedContacts
        var sc = savedContacts
        sc.replace(CONTACTS_SEPARATOR + contactId + CONTACTS_SEPARATOR, CONTACTS_SEPARATOR)
        sc = sc + contactId + CONTACTS_SEPARATOR
        return sc
    }

    fun containsContact(contactId: Int, contacts: String): Boolean {
        return contacts.contains(CONTACTS_SEPARATOR + contactId + CONTACTS_SEPARATOR)
    }
}