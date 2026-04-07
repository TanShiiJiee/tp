package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWDOC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEWTIME;

import seedu.address.logic.commands.EditApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * parses the input tp creat an EditApptCommand
 */
public class EditApptCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EditApptCommand
     * and returns an EditApptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditApptCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_APPT_ID,
                PREFIX_NEWDATE, PREFIX_NEWTIME, PREFIX_NEWDOC, PREFIX_NEWNAME);

        if (argMultimap.getValue(PREFIX_APPT_ID).isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException("Missing required fields to identify the appointment! "
                    + "Need id/.");
        }

        String idValue = argMultimap.getValue(PREFIX_APPT_ID).get().trim();
        int apptId;
        try {
            apptId = Integer.parseInt(idValue);
        } catch (NumberFormatException e) {
            throw new ParseException("Appointment id must be a non-negative integer.");
        }

        if (apptId < 0) {
            throw new ParseException("Appointment id must be a non-negative integer.");
        }

        String newDoc = argMultimap.getValue(PREFIX_NEWDOC).orElse(null);
        String newDate = argMultimap.getValue(PREFIX_NEWDATE).orElse(null);
        String newTime = argMultimap.getValue(PREFIX_NEWTIME).orElse(null);
        String newPat = argMultimap.getValue(PREFIX_NEWNAME).orElse(null);

        return new EditApptCommand(apptId, newPat, newDoc, newDate, newTime);
    }
}
