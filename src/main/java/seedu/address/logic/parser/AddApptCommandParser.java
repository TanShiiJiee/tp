package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;

/**
 * Parses input appointments and creates a new AddApptCommand object
 */
public class AddApptCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddApptCommand
     * and returns an AddApptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddApptCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_TIME,
                                                                    PREFIX_DOCTOR, PREFIX_NAME);


        if (argMultimap.getValue(PREFIX_DATE).isEmpty() || argMultimap.getValue(PREFIX_TIME).isEmpty()) {
            throw new ParseException("Missing date (date/) or time (time/)!");
        }

        String person = argMultimap.getValue(PREFIX_NAME).get(); // This gets whatever is before the first prefix
        String date = argMultimap.getValue(PREFIX_DATE).get();
        String time = argMultimap.getValue(PREFIX_TIME).get();
        String doctor = argMultimap.getValue(PREFIX_DOCTOR).get();
        Appointment appt = new Appointment(doctor, person, date, time);

        return new AddApptCommand(appt);
    }
}
