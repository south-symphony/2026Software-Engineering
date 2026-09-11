package com.plagiarism;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SimilarityCalculatorTest {

    @Test
    void cosineSimilarity_identicalTexts_shouldBeOne() {
        // 测试：完全相同文本，相似度为1
        List<String> words = List.of("今天", "天气", "好");
        double sim = SimilarityCalculator.cosineSimilarity(words, words);
        assertEquals(1.0, sim, 0.01);
    }

    @Test
    void cosineSimilarity_totallyDifferent_shouldBeZero() {
        // 测试：完全不同文本，相似度为0
        List<String> words1 = List.of("今天", "天气");
        List<String> words2 = List.of("明天", "心情");
        double sim = SimilarityCalculator.cosineSimilarity(words1, words2);
        assertEquals(0.0, sim, 0.01);
    }

    @Test
    void cosineSimilarity_partialOverlap_shouldBeBetweenZeroAndOne() {
        // 测试：部分重复文本，相似度在0到1之间
        List<String> words1 = List.of("今天", "天气", "好");
        List<String> words2 = List.of("今天", "天气", "差");
        double sim = SimilarityCalculator.cosineSimilarity(words1, words2);
        assertTrue(sim > 0 && sim < 1);
    }

    @Test
    void cosineSimilarity_emptyList_shouldBeZero() {
        // 测试：空列表相似度为0
        List<String> words1 = List.of();
        List<String> words2 = List.of("今天");
        assertEquals(0.0, SimilarityCalculator.cosineSimilarity(words1, words2));
    }
}
