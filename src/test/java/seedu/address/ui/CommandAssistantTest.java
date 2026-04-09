package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CommandAssistantTest {

    private final CommandAssistant commandAssistant = new CommandAssistant();

    @Test
    public void getSuggestion_emptyInput_returnsEmptySuggestion() {
        assertEquals("", commandAssistant.getSuggestion(""));
    }

    @Test
    public void getSuggestion_addCommand_showsAllRequiredFields() {
        assertEquals(" n/NAME p/PHONE e/EMAIL a/ADDRESS b/BOX_NAME:EXPIRY_DATE [r/REMARKS] [t/TAG]...",
                commandAssistant.getSuggestion("add"));
    }

    @Test
    public void getSuggestion_addCommandWithStartedFields_showsRemainingFields() {
        assertEquals(" p/PHONE e/EMAIL a/ADDRESS b/BOX_NAME:EXPIRY_DATE [r/REMARKS] [t/TAG]...",
                commandAssistant.getSuggestion("add n/John Doe"));
    }

    @Test
    public void getSuggestion_assignCommandWithMissingPhone_showsNextRequiredField() {
        assertEquals(" p/PHONE [n/NAME p/PHONE]...", commandAssistant.getSuggestion("assign n/Alex"));
    }

    @Test
    public void getSuggestion_assignCommandWithGroupedNamesAndPhones_preservesPositionalPattern() {
        assertEquals(" n/NAME [n/NAME p/PHONE]...",
                commandAssistant.getSuggestion("assign n/D1 n/D2 p/1111 p/2222"));
    }

    @Test
    public void getSuggestion_editCommandAfterIndex_showsEditableFields() {
        assertEquals(" [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [r/REMARKS] [t/TAG]...",
                commandAssistant.getSuggestion("edit 1"));
    }

    @Test
    public void getSuggestion_addBoxCommand_showsMissingFieldsAndRepeatableBoxes() {
        assertEquals(" [b/BOX_NAME:EXPIRY_DATE]...",
                commandAssistant.getSuggestion("addbox n/John Doe b/box-1:2026-01-01"));
    }

    @Test
    public void getSuggestion_deleteBoxCommand_showsRepeatableBoxes() {
        assertEquals(" [b/BOX_NAME]...", commandAssistant.getSuggestion("deletebox n/John Doe b/box-1"));
    }

    @Test
    public void getSuggestion_editBoxCommand_showsOptionalEditableFields() {
        assertEquals(" [nb/NEW_BOX_NAME] [ex/EXPIRY_DATE]",
                commandAssistant.getSuggestion("editbox n/John Doe b/box-1"));
    }

    @Test
    public void getSuggestion_deleteCommand_showsAcceptedTargets() {
        assertEquals(" INDEX", commandAssistant.getSuggestion("delete"));
    }

    @Test
    public void getSuggestion_filterCommandWithDriverPrefix_showsRepeatableDriverFields() {
        assertEquals(" [d/MORE_DRIVER_NAMES]...", commandAssistant.getSuggestion("filter d/Alex"));
    }

    @Test
    public void getSuggestion_filterCommandWithoutArgs_showsBoxOrDriverFormats() {
        assertEquals(" BOX_NAME [MORE_BOX_NAMES]... or d/DRIVER_NAME [d/MORE_DRIVER_NAMES]...",
                commandAssistant.getSuggestion("filter"));
    }

    @Test
    public void getSuggestion_filterCommandWithBoxKeyword_showsRepeatableBoxFields() {
        assertEquals(" [MORE_BOX_NAMES]...", commandAssistant.getSuggestion("filter box-1"));
    }

    @Test
    public void getSuggestion_exportCommandWithoutPath_showsDefaultDataFolderHint() {
        assertEquals(" [FILE_NAME.html]", commandAssistant.getSuggestion("export"));
    }

    @Test
    public void getSuggestion_importCommandWithoutPath_showsDataFolderCsvHint() {
        assertEquals(" FILE_NAME.csv", commandAssistant.getSuggestion("import"));
    }

    @Test
    public void getSuggestion_markCommandAfterIndex_showsStatusHint() {
        assertEquals(" STATUS", commandAssistant.getSuggestion("mark 1"));
    }

    @Test
    public void getSuggestion_noArgumentCommand_returnsEmptySuggestion() {
        assertEquals("", commandAssistant.getSuggestion("help"));
    }

    @Test
    public void getSuggestion_uniquePartialCommand_completesCommandWordAndTemplate() {
        assertEquals("mark INDEX REMARK", commandAssistant.getSuggestion("re"));
    }

    @Test
    public void getSuggestion_ambiguousPartialCommand_returnsEmptySuggestion() {
        assertEquals("", commandAssistant.getSuggestion("a"));
    }
}
