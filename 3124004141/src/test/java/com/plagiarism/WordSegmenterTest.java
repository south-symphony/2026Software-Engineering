package com.plagiarism;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordSegmenterTest {

    @Test
    void segment_normalText_shouldReturnCorrectWords() {
        List<String> words = WordSegmenter.segment("今天天气真好");
        assertTrue(words.contains("今天"));
        assertTrue(words.contains("天气"));
    }

    @Test
    void segment_emptyText_shouldReturnEmptyList() {
        assertTrue(WordSegmenter.segment("").isEmpty());
        assertTrue(WordSegmenter.segment(null).isEmpty());
    }

    @Test
    void segment_singleCharacter_shouldWorkProperly() {
        List<String> words = WordSegmenter.segment("好");
        assertEquals(1, words.size());
        assertEquals("好", words.get(0));
    }
}
