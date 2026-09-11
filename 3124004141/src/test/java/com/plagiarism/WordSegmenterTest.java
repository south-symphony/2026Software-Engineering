package com.plagiarism;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordSegmenterTest {

    @Test
    void segment_normalText_shouldReturnCorrectWords() {
        // 测试：正常中文句子分词
        List<String> words = WordSegmenter.segment("今天天气真好");
        assertTrue(words.contains("今天"));
        assertTrue(words.contains("天气"));
    }

    @Test
    void segment_emptyText_shouldReturnEmptyList() {
        // 测试：空文本分词
        assertTrue(WordSegmenter.segment("").isEmpty());
        assertTrue(WordSegmenter.segment(null).isEmpty());
    }

    @Test
    void segment_singleCharacter_shouldWorkProperly() {
        // 测试：单字分词
        List<String> words = WordSegmenter.segment("好");
        assertEquals(1, words.size());
        assertEquals("好", words.get(0));
    }
}
