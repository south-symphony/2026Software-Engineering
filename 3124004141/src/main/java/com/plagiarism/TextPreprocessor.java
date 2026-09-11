package com.plagiarism;

/**
 * 文本预处理工具类
 * 清洗无效字符，仅保留中英文有效内容
 */
public class TextPreprocessor {

    /**
     * 清洗文本：去除标点、数字、空格、换行，仅保留中文和英文字母
     * @param text 原始文本
     * @return 清洗后的纯文本
     */
    public static String clean(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        // 正则匹配：保留中文(\u4e00-\u9fa5)和英文字母(a-zA-Z)
        return text.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z]", "");
    }
}
