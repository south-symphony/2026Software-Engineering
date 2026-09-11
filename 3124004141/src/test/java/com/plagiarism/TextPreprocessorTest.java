package com.plagiarism;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextPreprocessorTest {

    @Test
    void clean_shouldRemoveChinesePunctuation() {
        // 测试：去除中文标点
        String result = TextPreprocessor.clean("你好，世界！");
        assertEquals("你好世界", result);
    }

    @Test
    void clean_shouldRemoveSpaceAndNewline() {
        // 测试：去除空格和换行符
        String result = TextPreprocessor.clean("今天 天气\n真好");
        assertEquals("今天天气真好", result);
    }

    @Test
    void clean_shouldRemoveNumbers() {
        // 测试：去除数字
        String result = TextPreprocessor.clean("今天30度天气好");
        assertEquals("今天度天气好", result);
    }

    @Test
    void clean_emptyInput_shouldReturnEmpty() {
        // 测试：空字符串与null输入
        assertEquals("", TextPreprocessor.clean(""));
        assertEquals("", TextPreprocessor.clean(null));
    }

    @Test
    void clean_shouldKeepEnglishLetters() {
        // 测试：保留英文字母
        String result = TextPreprocessor.clean("hello 世界");
        assertEquals("hello世界", result);
    }
}
