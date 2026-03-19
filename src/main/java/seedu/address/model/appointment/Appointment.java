package seedu.address.model.appointment;

/**
 * Creates an Appointment object
 */
public class Appointment {
    private String patientName;
    private String doctorName;
    private String date;
    private String time;

    /**
     * Initialises an Appointment object with the doctorname, patient name, and the date and time
     * @param doctorName
     * @param patientName
     * @param date
     * @param time
     */
    public Appointment(String doctorName, String patientName, String date, String time) {
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.date = date;
        this.time = time;

    }

    public String getPatName() {
        return this.patientName;
    }

    public String getDocName() {
        return this.doctorName;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Appointment) {
            return (this.patientName.equals(((Appointment) obj).getPatName())
                    && this.date.equals(((Appointment) obj).getDate())
                    && this.time.equals(((Appointment) obj).getTime()));
        } else {
            return false;
        }
    }

}
