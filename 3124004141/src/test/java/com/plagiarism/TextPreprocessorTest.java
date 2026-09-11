package com.plagiarism;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextPreprocessorTest {

    @Test
    void clean_shouldRemoveChinesePunctuation() {
        String result = TextPreprocessor.clean("你好，世界！");
        assertEquals("你好世界", result);
    }

    @Test
    void clean_shouldRemoveSpaceAndNewline() {
        String result = TextPreprocessor.clean("今天 天气\n真好");
        assertEquals("今天天气真好", result);
    }

    @Test
    void clean_shouldRemoveNumbers() {
        String result = TextPreprocessor.clean("今天30度天气好");
        assertEquals("今天度天气好", result);
    }

    @Test
    void clean_emptyInput_shouldReturnEmpty() {
        assertEquals("", TextPreprocessor.clean(""));
        assertEquals("", TextPreprocessor.clean(null));
    }

    @Test
    void clean_shouldKeepEnglishLetters() {
        String result = TextPreprocessor.clean("hello 世界");
        assertEquals("hello世界", result);
    }
}
