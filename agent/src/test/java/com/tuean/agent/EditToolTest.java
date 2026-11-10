package com.tuean.agent;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EditToolTest {

    @TempDir
    Path tmp;

    private final EditTool tool = new EditTool();

    @Test
    void replacesOneOccurrence() throws Exception {
        Path file = write("a\nb\nc\n");

        tool.execute(args(file, "{\"oldText\":\"b\",\"newText\":\"B\"}"));

        assertEquals("a\nB\nc\n", Files.readString(file, StandardCharsets.UTF_8));
    }

    @Test
    void appliesMultipleEditsAgainstOriginalText() throws Exception {
        Path file = write("one\ntwo\nthree\n");

        tool.execute(args(file,
                "{\"oldText\":\"one\",\"newText\":\"1\"}",
                "{\"oldText\":\"three\",\"newText\":\"3\"}"));

        assertEquals("1\ntwo\n3\n", Files.readString(file, StandardCharsets.UTF_8));
    }

    @Test
    void emptyNewTextDeletes() throws Exception {
        Path file = write("keep\ndrop\n");

        tool.execute(args(file, "{\"oldText\":\"drop\\n\",\"newText\":\"\"}"));

        assertEquals("keep\n", Files.readString(file, StandardCharsets.UTF_8));
    }

    @Test
    void rejectsTextThatMatchesTwice() throws Exception {
        Path file = write("x\nx\n");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> tool.execute(args(file, "{\"oldText\":\"x\",\"newText\":\"y\"}")));
        assertTrue(e.getMessage().contains("多处"), e.getMessage());
    }

    @Test
    void rejectsOverlappingEdits() throws Exception {
        Path file = write("aaa\nbbb\nccc\n");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> tool.execute(args(file,
                        "{\"oldText\":\"aaa\\nbbb\",\"newText\":\"X\"}",
                        "{\"oldText\":\"bbb\\nccc\",\"newText\":\"Y\"}")));
        assertTrue(e.getMessage().contains("重叠"), e.getMessage());
        assertEquals("aaa\nbbb\nccc\n", Files.readString(file, StandardCharsets.UTF_8));
    }

    @Test
    void rejectsMissingOldText() throws Exception {
        Path file = write("abc\n");

        assertThrows(IllegalArgumentException.class,
                () -> tool.execute(args(file, "{\"oldText\":\"zzz\",\"newText\":\"y\"}")));
    }

    private Path write(String content) throws Exception {
        Path file = tmp.resolve("f.txt");
        Files.writeString(file, content, StandardCharsets.UTF_8);
        return file;
    }

    private static JsonNode args(Path file, String... edits) throws Exception {
        StringBuilder json = new StringBuilder("{\"path\":\"" + file + "\",\"edits\":[");
        json.append(String.join(",", edits)).append("]}");
        return Json.MAPPER.readTree(json.toString());
    }
}
