package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import seedu.address.model.appointment.Appointment;
import seedu.address.storage.AppointmentManager;

public class SchedulePanelTest {

    private static final String APPOINTMENTS_FILE = "data/appointments.json";
    private static final LocalDate FIXED_DATE = LocalDate.of(2026, 3, 20);

    private static boolean javafxStarted;

    private byte[] appointmentsBackup;
    private boolean appointmentsExisted;

    @BeforeAll
    public static void startJavaFx() {
        if (!javafxStarted) {
            try {
                Platform.startup(() -> { });
            } catch (IllegalStateException ignored) {
                // JavaFX was already started by another test.
            }
            javafxStarted = true;
        }
    }

    @BeforeEach
    public void backupAppointments() throws Exception {
        File file = new File(APPOINTMENTS_FILE);
        appointmentsExisted = file.exists();
        appointmentsBackup = appointmentsExisted ? Files.readAllBytes(file.toPath()) : null;
    }

    @AfterEach
    public void restoreAppointments() throws Exception {
        File file = new File(APPOINTMENTS_FILE);
        if (appointmentsExisted) {
            Files.write(file.toPath(), appointmentsBackup);
        } else {
            file.delete();
        }
    }

    @Test
    public void displaySchedule_bookedSlotShowsIdsAndWrapsText() throws Exception {
        Appointment appt = new Appointment(1, "John Tan", 7, "Alice Lim", FIXED_DATE.toString(), "09:00", -1);
        int apptId = AppointmentManager.addAppointment(appt);

        SchedulePanel panel = runOnFxThread(SchedulePanel::new);
        Map<String, String> schedule = new LinkedHashMap<>();
        schedule.put("09:00", "Alice Lim");

        runOnFxThread(() -> {
            panel.displaySchedule(schedule, "John Tan", 1, FIXED_DATE);
            return null;
        });

        GridPane grid = getScheduleGrid(panel);
        Label slot = getLabelAt(grid, 1, 0);

        assertNotNull(slot);
        assertTrue(slot.getText().startsWith("Alice Lim\n("));
        assertTrue(slot.getText().contains("Patient ID: 7"));
        assertTrue(slot.getText().contains("Appt ID: " + apptId));
        assertTrue(slot.isWrapText());
    }

    @Test
    public void displayWeeklySchedule_buildsSevenDayGrid() throws Exception {
        SchedulePanel panel = runOnFxThread(SchedulePanel::new);
        Map<String, Map<String, String>> weeklySchedule = new LinkedHashMap<>();

        for (int i = 0; i < 7; i++) {
            Map<String, String> day = new LinkedHashMap<>();
            day.put("09:00", i == 0 ? "Alice Lim" : null);
            day.put("09:30", null);
            weeklySchedule.put(FIXED_DATE.plusDays(i).toString(), day);
        }

        runOnFxThread(() -> {
            panel.displayWeeklySchedule(weeklySchedule, "John Tan", 1);
            return null;
        });

        GridPane grid = getScheduleGrid(panel);
        assertEquals(8, grid.getColumnConstraints().size());
        assertNotNull(getLabelAt(grid, 1, 0));
        assertEquals("09:00", getLabelAt(grid, 0, 1).getText());
    }

    private static <T> T runOnFxThread(java.util.concurrent.Callable<T> task) throws Exception {
        AtomicReference<T> result = new AtomicReference<>();
        AtomicReference<Throwable> error = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                result.set(task.call());
            } catch (Throwable t) {
                error.set(t);
            } finally {
                latch.countDown();
            }
        });

        latch.await();

        if (error.get() != null) {
            throw new AssertionError(error.get());
        }
        return result.get();
    }

    private GridPane getScheduleGrid(SchedulePanel panel) throws Exception {
        Field field = SchedulePanel.class.getDeclaredField("scheduleGrid");
        field.setAccessible(true);
        return (GridPane) field.get(panel);
    }

    private Label getLabelAt(GridPane grid, int column, int row) {
        for (Node node : grid.getChildren()) {
            Integer col = GridPane.getColumnIndex(node);
            Integer r = GridPane.getRowIndex(node);
            if ((col == null ? 0 : col) == column && (r == null ? 0 : r) == row) {
                return (Label) node;
            }
        }
        return null;
    }
}
