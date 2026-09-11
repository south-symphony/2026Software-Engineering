package com.plagiarism;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 中文分词工具类
 * 封装 Jieba 分词器，提供统一分词接口
 */
public class WordSegmenter {

    // 单例分词器，避免重复初始化
    private static final JiebaSegmenter SEGMENTER = new JiebaSegmenter();

    /**
     * 对文本进行精确模式分词
     * @param text 输入文本
     * @return 分词结果列表
     */
    public static List<String> segment(String text) {
        if (text == null || text.isEmpty()) {
            return Collections.emptyList(); // JDK8兼容写法
        }
        List<SegToken> tokens = SEGMENTER.process(text, JiebaSegmenter.SegMode.INDEX);
        return tokens.stream()
                .map(token -> token.word)
                .collect(Collectors.toList());
    }
}
