package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage patientDataStorage;
    private AddressBookStorage doctorDataStorage;
    private AddressBookStorage scheduleDataStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given storage components.
     */
    public StorageManager(AddressBookStorage patientDataStorage,
                          AddressBookStorage doctorDataStorage,
                          AddressBookStorage scheduleDataStorage,
                          UserPrefsStorage userPrefsStorage) {
        this.patientDataStorage = patientDataStorage;
        this.doctorDataStorage = doctorDataStorage;
        this.scheduleDataStorage = scheduleDataStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        // We needed to get rid of the addressbook file in the future, and
        // asked Copilot to suggest if this should be done for the MVP
        // and if so then how would data be displayed on screen, Copilot
        // suggested keeping it to not break the code for now
        // Code below written by Copilot
        // Legacy method required by AddressBookStorage interface.
        // Returns patient data path as a fallback for status bar display.
        return patientDataStorage.getAddressBookFilePath();
    }

    @Override
    public Path getPatientsFilePath() {
        return patientDataStorage.getAddressBookFilePath();
    }

    @Override
    public Path getDoctorsFilePath() {
        return doctorDataStorage.getAddressBookFilePath();
    }

    @Override
    public Path getScheduleFilePath() {
        return scheduleDataStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        // Code by Copilot
        throw new UnsupportedOperationException("Use readPatientData() or readDoctorData() instead.");
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        // Code by Copilot
        throw new UnsupportedOperationException("Use readPatientData() or readDoctorData() instead.");
    }

    @Override
    public Optional<ReadOnlyAddressBook> readPatientData() throws DataLoadingException {
        return patientDataStorage.readAddressBook();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readDoctorData() throws DataLoadingException {
        return doctorDataStorage.readAddressBook();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readScheduleData() throws DataLoadingException {
        return scheduleDataStorage.readAddressBook();
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        // Code by Copilot
        throw new UnsupportedOperationException("Use savePatientData() or saveDoctorData() instead.");
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        // Code by Copilot
        throw new UnsupportedOperationException("Use savePatientData() or saveDoctorData() instead.");
    }

    @Override
    public void savePatientData(ReadOnlyAddressBook patientData) throws IOException {
        patientDataStorage.saveAddressBook(patientData);
    }

    @Override
    public void saveDoctorData(ReadOnlyAddressBook doctorData) throws IOException {
        doctorDataStorage.saveAddressBook(doctorData);
    }

    @Override
    public void saveScheduleData(ReadOnlyAddressBook scheduleData) throws IOException {
        scheduleDataStorage.saveAddressBook(scheduleData);
    }

}
