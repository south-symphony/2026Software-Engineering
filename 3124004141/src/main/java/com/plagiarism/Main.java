package com.plagiarism;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 程序主入口
 * 处理命令行参数，调度各模块，输出查重结果
 */
public class Main {

    public static void main(String[] args) {
        // 1. 校验命令行参数数量
        if (args.length != 3) {
            System.out.println("用法: java -jar main.jar [原文路径] [抄袭版路径] [输出路径]");
            System.exit(1);
        }

        String origPath = args[0];
        String copyPath = args[1];
        String outputPath = args[2];

        try {
            // 2. 读取两个文件内容
            String origText = readFile(origPath);
            String copyText = readFile(copyPath);

            // 3. 文本预处理
            String cleanOrig = TextPreprocessor.clean(origText);
            String cleanCopy = TextPreprocessor.clean(copyText);

            // 4. 中文分词
            List<String> origWords = WordSegmenter.segment(cleanOrig);
            List<String> copyWords = WordSegmenter.segment(cleanCopy);

            // 5. 计算余弦相似度
            double similarity = SimilarityCalculator.cosineSimilarity(origWords, copyWords);

            // 6. 保留两位小数写入输出文件（JDK8兼容写法）
            String result = String.format("%.2f", similarity);
            Files.write(Paths.get(outputPath), result.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            System.err.println("文件操作错误: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("运行错误: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * 读取文件，优先 UTF-8 编码，失败自动降级为 GBK
     * @param filePath 文件绝对路径
     * @return 文件内容字符串
     * @throws IOException 文件读取失败
     */
    private static String readFile(String filePath) throws IOException {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // UTF-8 解码失败，尝试 GBK 编码
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return new String(bytes, Charset.forName("GBK"));
        }
    }
}
