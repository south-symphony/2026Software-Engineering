package com.plagiarism;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SimilarityCalculatorTest {

    @Test
    void cosineSimilarity_identicalTexts_shouldBeOne() {
        List<String> words = Arrays.asList("今天", "天气", "好");
        double sim = SimilarityCalculator.cosineSimilarity(words, words);
        assertEquals(1.0, sim, 0.01);
    }

    @Test
    void cosineSimilarity_totallyDifferent_shouldBeZero() {
        List<String> words1 = Arrays.asList("今天", "天气");
        List<String> words2 = Arrays.asList("明天", "心情");
        double sim = SimilarityCalculator.cosineSimilarity(words1, words2);
        assertEquals(0.0, sim, 0.01);
    }

    @Test
    void cosineSimilarity_partialOverlap_shouldBeBetweenZeroAndOne() {
        List<String> words1 = Arrays.asList("今天", "天气", "好");
        List<String> words2 = Arrays.asList("今天", "天气", "差");
        double sim = SimilarityCalculator.cosineSimilarity(words1, words2);
        assertTrue(sim > 0 && sim < 1);
    }

    @Test
    void cosineSimilarity_emptyList_shouldBeZero() {
        List<String> words1 = Arrays.asList();
        List<String> words2 = Arrays.asList("今天");
        assertEquals(0.0, SimilarityCalculator.cosineSimilarity(words1, words2));
    }
}
