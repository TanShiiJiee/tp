package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.File;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;
import seedu.address.storage.AppointmentManager;
import seedu.address.storage.ScheduleManager;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;

public class EditApptCommandTest {
    private static final String SCHEDULE_FILE_PATH = "data/schedule.json";
    private static final String APPT_FILE_PATH = "data/appointments.json";
    private static final String DOCTOR_NAME = "John Tan";
    private static final String PATIENT_NAME = "Jane Doe";
    private static final int APPT_ID = 1;

    private LocalDate date;

    @BeforeEach
    public void setup() throws Exception {
        date = LocalDate.now().plusDays(1);
        Map<String, String> slots = new LinkedHashMap<>();
        slots.put("09:00", null);
        slots.put("09:30", PATIENT_NAME);
        slots.put("10:00", null);
        writeScheduleWithSlots(DOCTOR_NAME, date.toString(), slots);
        writeAppointmentsWithId(APPT_ID, DOCTOR_NAME, PATIENT_NAME, date.toString(), "09:30");
    }

    @Test
    public void execute_validId_success() throws Exception {
        Model model = new ModelManager();
        Doctor doctor = new DoctorBuilder().withName(DOCTOR_NAME).build();
        Patient patient = new PatientBuilder().withName(PATIENT_NAME).build();
        model.addDoctor(doctor);
        model.addPatient(patient);

        EditApptCommand command = new EditApptCommand(APPT_ID, null, null, "10:00");
        command.execute(model);

        Appointment updated = AppointmentManager.getAppointmentById(APPT_ID);
        assertNotNull(updated);
        assertEquals("10:00", updated.getTime());

        Map<String, String> schedule = ScheduleManager.getScheduleIgnoreCase(DOCTOR_NAME, date.toString());
        assertEquals(null, schedule.get("09:30"));
        assertEquals(PATIENT_NAME, schedule.get("10:00"));
    }

    @Test
    public void execute_invalidTime_showsError() throws Exception {
        Model model = new ModelManager();
        Doctor doctor = new DoctorBuilder().withName(DOCTOR_NAME).build();
        Patient patient = new PatientBuilder().withName(PATIENT_NAME).build();
        model.addDoctor(doctor);
        model.addPatient(patient);

        EditApptCommand command = new EditApptCommand(APPT_ID, null, null, "110:00");
        assertThrows(Exception.class, () -> command.execute(model));
    }

    private void writeScheduleWithSlots(String doctorName, String dateValue, Map<String, String> slots)
            throws Exception {
        Map<String, Map<String, Map<String, String>>> data = new LinkedHashMap<>();
        Map<String, Map<String, String>> doctorSchedule = new LinkedHashMap<>();
        doctorSchedule.put(dateValue, new LinkedHashMap<>(slots));
        data.put(doctorName, doctorSchedule);

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(SCHEDULE_FILE_PATH);
        file.getParentFile().mkdirs();
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
    }

    private void writeAppointmentsWithId(int apptId, String doctorName, String patientName,
                                         String dateValue, String timeValue) throws Exception {
        Map<String, Map<String, String>> data = new LinkedHashMap<>();
        Map<String, String> entry = new LinkedHashMap<>();
        entry.put("doctorName", doctorName);
        entry.put("patientName", patientName);
        entry.put("date", dateValue);
        entry.put("time", timeValue);
        data.put(String.valueOf(apptId), entry);

        ObjectMapper mapper = new ObjectMapper();
        File file = new File(APPT_FILE_PATH);
        file.getParentFile().mkdirs();
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        AppointmentManager.initialise();
    }
}
