package com.example.studentauth.util;

public class Validater {
    /**
     * 验证数据是否为空
     *
     * @param str
     * @return
     */
    public static boolean validationEmpty(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return true;
    }

    public static boolean validationEmpty(Long str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        return true;
    }
}
