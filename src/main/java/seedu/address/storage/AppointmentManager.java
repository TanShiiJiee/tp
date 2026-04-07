package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.model.appointment.Appointment;

public class AppointmentManager {
    private static final String FILE_PATH = "data/appointments.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        initialise();
    }

    public static void initialise() {
        try {
            File file = new File(FILE_PATH);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() || file.length() == 0) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, new LinkedHashMap<String, AppointmentData>());
            }
        } catch (IOException e) {
            System.err.println("Could not create appointments.json: " + e.getMessage());
        }
    }

    public static synchronized int addAppointment(Appointment appt) throws IOException {
        Map<String, AppointmentData> data = readAppointments();
        int nextId = data.keySet().stream()
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(-1) + 1;

        data.put(String.valueOf(nextId),
                new AppointmentData(appt.getDocName(), appt.getPatName(), appt.getDate(), appt.getTime()));
        writeAppointments(data);
        appt.setApptID(nextId);
        return nextId;
    }

    public static synchronized Appointment getAppointmentById(int apptId) throws IOException {
        Map<String, AppointmentData> data = readAppointments();
        AppointmentData record = data.get(String.valueOf(apptId));
        if (record == null) {
            return null;
        }

        return new Appointment(record.doctorName, record.patientName, record.date, record.time, apptId);
    }

    public static synchronized void updateAppointment(int apptId, Appointment appt) throws IOException {
        Map<String, AppointmentData> data = readAppointments();
        String key = String.valueOf(apptId);
        if (!data.containsKey(key)) {
            throw new IOException("Appointment id not found: " + apptId);
        }

        data.put(key, new AppointmentData(appt.getDocName(), appt.getPatName(), appt.getDate(), appt.getTime()));
        writeAppointments(data);
    }

    public static synchronized void deleteAppointment(int apptId) throws IOException {
        Map<String, AppointmentData> data = readAppointments();
        if (data.remove(String.valueOf(apptId)) == null) {
            throw new IOException("Appointment id not found: " + apptId);
        }

        writeAppointments(data);
    }

    private static Map<String, AppointmentData> readAppointments() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new LinkedHashMap<>();
        }

        return mapper.readValue(file, new TypeReference<LinkedHashMap<String, AppointmentData>>() {});
    }

    private static void writeAppointments(Map<String, AppointmentData> data) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
    }

    private static final class AppointmentData {
        public String doctorName;
        public String patientName;
        public String date;
        public String time;

        public AppointmentData() {}

        public AppointmentData(String doctorName, String patientName, String date, String time) {
            this.doctorName = doctorName;
            this.patientName = patientName;
            this.date = date;
            this.time = time;
        }
    }
}
