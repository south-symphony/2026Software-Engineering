package com.plagiarism;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 相似度计算工具类
 * 基于词袋模型和余弦相似度计算文本重复率
 */
public class SimilarityCalculator {

    /**
     * 计算两个分词列表的余弦相似度
     * @param words1 原文分词结果
     * @param words2 抄袭版分词结果
     * @return 相似度值，范围 0.0 ~ 1.0
     */
    public static double cosineSimilarity(List<String> words1, List<String> words2) {
        Map<String, Integer> freq1 = getWordFrequency(words1);
        Map<String, Integer> freq2 = getWordFrequency(words2);
        return calculateCosine(freq1, freq2);
    }

    /**
     * 统计词频，生成词-频率映射
     */
    private static Map<String, Integer> getWordFrequency(List<String> words) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }
        return freqMap;
    }

    /**
     * 计算两个词频向量的余弦值
     */
    private static double calculateCosine(Map<String, Integer> freq1, Map<String, Integer> freq2) {
        // 获取所有词的并集
        Set<String> allWords = new HashSet<>(freq1.keySet());
        allWords.addAll(freq2.keySet());

        // 计算点积
        double dotProduct = 0.0;
        for (String word : allWords) {
            dotProduct += freq1.getOrDefault(word, 0) * freq2.getOrDefault(word, 0);
        }

        // 计算两个向量的模长
        double norm1 = calculateNorm(freq1);
        double norm2 = calculateNorm(freq2);

        // 避免除零异常
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        return dotProduct / (norm1 * norm2);
    }

    /**
     * 计算向量的模长（平方和开根号）
     */
    private static double calculateNorm(Map<String, Integer> freqMap) {
        double sum = 0.0;
        for (int count : freqMap.values()) {
            sum += Math.pow(count, 2);
        }
        return Math.sqrt(sum);
    }
}
