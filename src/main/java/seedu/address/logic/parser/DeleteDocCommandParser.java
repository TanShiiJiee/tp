package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteDocCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteDocCommand object
 */
public class DeleteDocCommandParser implements Parser<DeleteDocCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteDocCommand
     * and returns a DeleteDocCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDocCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteDocCommand(index);
        } catch (ParseException pe) {
            if (args.trim().matches("-?\\d+")) {
                throw new ParseException(Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
            }
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDocCommand.MESSAGE_USAGE), pe);
        }
    }

}
