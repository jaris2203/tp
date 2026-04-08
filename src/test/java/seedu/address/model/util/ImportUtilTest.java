package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ImportUtilTest {

    @TempDir
    Path tempDir;

    private Path writeCsv(String content) throws IOException {
        Path file = tempDir.resolve("input.csv");
        Files.writeString(file, content);
        return file;
    }

    @Test
    public void parseCsv_validFile_returnsRows() throws Exception {
        String csv = "header1,header2,header3,header4,header5,header6,header7,header8,header9\n"
                + "0,Alice,91234567,alice@example.com,123 Road 123456,box-1,12,remark,extra\n"
                + "1,Bob,98765432,bob@example.com,456 Road 234567,box-2,6,remark,extra\n";
        Path file = writeCsv(csv);

        List<String[]> rows = ImportUtil.parseCsv(file.toString());
        assertEquals(2, rows.size());
    }

    @Test
    public void parseCsv_nonexistentFile_throwsIoException() {
        String nonExistent = tempDir.resolve("missing.csv").toString();
        assertThrows(IOException.class, () -> ImportUtil.parseCsv(nonExistent));
    }

    @Test
    public void parseCsv_headerOnly_returnsEmptyList() throws Exception {
        String csv = "header1,header2,header3,header4,header5,header6,header7,header8,header9\n";
        Path file = writeCsv(csv);

        List<String[]> rows = ImportUtil.parseCsv(file.toString());
        assertEquals(0, rows.size());
    }

    @Test
    public void parseCsv_shortRows_skipsInvalidRows() throws Exception {
        // Row with fewer than 9 columns should be silently skipped
        String csv = "h1,h2,h3,h4,h5,h6,h7,h8,h9\n"
                + "0,Alice,91234567,alice@example.com,123 Road 123456,box-1,12,remark,extra\n"
                + "bad,short,row\n"; // only 3 columns, skipped
        Path file = writeCsv(csv);

        List<String[]> rows = ImportUtil.parseCsv(file.toString());
        assertEquals(1, rows.size());
    }

    @Test
    public void parseCsv_quotedFields_stripsQuotes() throws Exception {
        String csv = "h1,h2,h3,h4,h5,h6,h7,h8,h9\n"
                + "0,\"Alice\",\"91234567\",alice@example.com,123 Road 123456,box-1,12,remark,extra\n";
        Path file = writeCsv(csv);

        List<String[]> rows = ImportUtil.parseCsv(file.toString());
        assertEquals(1, rows.size());
        assertEquals("Alice", rows.get(0)[1]);
        assertEquals("91234567", rows.get(0)[2]);
    }

    @Test
    public void parseCsv_emptyFile_returnsEmptyList() throws Exception {
        Path file = writeCsv("");
        List<String[]> rows = ImportUtil.parseCsv(file.toString());
        assertEquals(0, rows.size());
    }
}
