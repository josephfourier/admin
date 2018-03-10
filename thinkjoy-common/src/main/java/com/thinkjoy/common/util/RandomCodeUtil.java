package com.thinkjoy.common.util;

import java.util.Random;

/**
 * 随机的验证码生成工具（数字或字母加数字）
 *
 * @author Michael
 */
public class RandomCodeUtil {

    private static final String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 生成指定长度的数字验证码
     *
     * @param length
     * @return
     */
    public static String generateNumCode(int length) {

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(new Random().nextInt(10));
        }
        return code.toString();
    }

    /**
     * 生成制定长度的字母加数字验证码
     *
     * @param length
     * @return
     */
    public static String generateCharCode(int length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(generateOneChar());
        }
        return code.toString();
    }

    private static char generateOneChar() {
        int index = new Random().nextInt(36);
        return BASE.charAt(index);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(generateNumCode(10));
        }
    }
}
