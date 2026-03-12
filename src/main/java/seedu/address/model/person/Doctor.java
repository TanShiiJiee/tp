package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a doctor in the app.
 * Extends {@code Person} to support the new 'adddoc' command.
 */
public class Doctor extends Person {
    public Doctor(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }

    // Override toString() to reflect the name as Dr. ...
}
