package main;

import java.util.Random;

/**
 * 递归生成四则运算表达式，控制运算符数量不超过3个
 * 保证减法不出现负数，除法结果为真分数
 */
public class ExpressionGenerator {
    private final int range;      // 数值范围
    private final Random random;
    private final int maxOps;     // 最大运算符数

    public ExpressionGenerator(int range) {
        if (range < 1) {
            throw new IllegalArgumentException("数值范围必须大于等于1");
        }
        this.range = range;
        this.random = new Random();
        this.maxOps = 3;
    }

    // 生成一个表达式节点，返回表达式字符串和对应的值
    public ExprNode generate() {
        return generateExpression(maxOps);
    }

    private ExprNode generateExpression(int remainingOps) {
        if (remainingOps == 0 || (random.nextBoolean() && remainingOps < maxOps)) {
            // 生成操作数
            return generateOperand();
        }

        // 随机选择运算符
        int opIndex = random.nextInt(4);
        char op = "+-×÷".charAt(opIndex);

        // 左右子表达式分配运算符数量
        int leftOps = random.nextInt(remainingOps);
        int rightOps = remainingOps - 1 - leftOps;

        ExprNode left = generateExpression(leftOps);
        ExprNode right = generateExpression(rightOps);

        Fraction result;
        switch (op) {
            case '+':
                result = left.value.add(right.value);
                break;
            case '-':
                // 保证减法结果非负
                if (!left.value.isGreaterOrEqual(right.value)) {
                    // 交换左右
                    ExprNode temp = left;
                    left = right;
                    right = temp;
                }
                result = left.value.subtract(right.value);
                break;
            case '×':
                result = left.value.multiply(right.value);
                break;
            case '÷':
                // 保证除数不为0且结果为真分数，最多重试20次
                int tryCount = 0;
                boolean divideOk = false;
                while (tryCount < 20) {
                    boolean valid = right.value.getNumerator() != 0
                            && left.value.divide(right.value).isProperFraction();
                    if (valid) {
                        divideOk = true;
                        break;
                    }
                    right = generateOperand();
                    tryCount++;
                }

                if (divideOk) {
                    result = left.value.divide(right.value);
                } else {
                    // 无法生成合法除法，降级为加法
                    op = '+';
                    result = left.value.add(right.value);
                }
                break;
            default:
                throw new IllegalStateException("未知运算符");
        }

        String expr = left.expression + " " + op + " " + right.expression;

        // 按优先级决定是否加括号
        if (remainingOps < maxOps && needParentheses(op, left.op, true)) {
            expr = "(" + left.expression + ")" + " " + op + " " + right.expression;
        }
        if (remainingOps < maxOps && needParentheses(op, right.op, false)) {
            expr = left.expression + " " + op + " " + "(" + right.expression + ")";
        }

        return new ExprNode(expr, result, op);
    }

    // 判断是否需要加括号
    private boolean needParentheses(char outerOp, char innerOp, boolean isLeft) {
        if (innerOp == ' ') return false; // 操作数不需要括号

        int outerPriority = getPriority(outerOp);
        int innerPriority = getPriority(innerOp);

        if (innerPriority < outerPriority) {
            return true;
        }
        if (innerPriority == outerPriority) {
            // 同优先级：减法和除法右结合需要括号
            if (!isLeft && (outerOp == '-' || outerOp == '÷')) {
                return true;
            }
        }
        return false;
    }

    private int getPriority(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '×': case '÷': return 2;
            default: return 0;
        }
    }

    // 生成操作数（自然数或真分数）
    private ExprNode generateOperand() {
        // 范围小于2时，只能生成自然数，无法生成真分数
        if (range < 2 || random.nextBoolean()) {
            // 自然数：0 ~ range-1
            long num = Math.abs(random.nextInt(range));
            Fraction f = new Fraction(num);
            return new ExprNode(String.valueOf(num), f, ' ');
        } else {
            // 真分数：分母范围 2 ~ range-1
            int denominatorBound = range - 1;
            // 分母至少为2
            long denominator = random.nextInt(denominatorBound - 1) + 2;
            // 分子范围 1 ~ denominator-1
            long numerator = random.nextInt((int) (denominator - 1)) + 1;

            // 30%概率生成带分数（范围足够大时）
            if (random.nextDouble() < 0.3 && range > 2) {
                long integer = random.nextInt(range - 1) + 1;
                numerator = integer * denominator + numerator;
            }

            Fraction f = new Fraction(numerator, denominator);
            return new ExprNode(f.toString(), f, ' ');
        }
    }

    // 表达式节点：包含表达式字符串、值、根运算符
    public static class ExprNode {
        public String expression;
        public Fraction value;
        public char op; // 根运算符，操作数为' '

        public ExprNode(String expression, Fraction value, char op) {
            this.expression = expression;
            this.value = value;
            this.op = op;
        }
    }
}
