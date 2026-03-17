package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.ViewSchedCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewSchedCommand object
 */
public class ViewSchedCommandParser implements Parser<ViewSchedCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewSchedCommand
     * and returns an AddDocCoViewSchedCommandmmand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewSchedCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DOCTOR, PREFIX_DATE);

        if (!argMultimap.getValue(PREFIX_DOCTOR).isPresent()
                || !argMultimap.getValue(PREFIX_DATE).isPresent()) {
            throw new ParseException("Invalid command format.");
        }

        String doctorName = argMultimap.getValue(PREFIX_DOCTOR).get().trim();
        String dateStr = argMultimap.getValue(PREFIX_DATE).get().trim();

        if (!doctorName.matches("[A-Za-z ]{2,50}")) {
            throw new ParseException("Invalid doctor name.");
        }

        LocalDate date;

        try {
            date = LocalDate.parse(dateStr); // YYYY-MM-DD
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date format. Use YYYY-MM-DD.");
        }

        return new ViewSchedCommand(doctorName, date);
    }
}
