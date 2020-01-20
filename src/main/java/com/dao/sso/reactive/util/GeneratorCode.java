package com.dao.sso.reactive.util;

import java.util.Arrays;

/**
 * @author fengchao
 * @date 2018-07-18
 */
public class GeneratorCode {
    /**
     * 生成6位授权码
     */
    public static int generatorCode(int length) {
        // 将数组随机打乱，据算法原理可知：
        // 重复概率 = 1/10 * 1/9 * 1/8 * 1/7 * 1/6 * 1/5 * 1/4 * 1/3 * 1/2 * 1/1 = 1/3628800，
        // 即重复概率为三百多万分之一，满足要求。
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int num = 10; num > 1; --num) {
            int idx = StdRandom.uniform(num);
            int temp = arr[idx];
            arr[idx] = arr[num - 1];
            arr[num - 1] = temp;
        }
        // 第一个元素不能为0，否则位数不够
        if (0 == arr[0]) {
            int ndx = StdRandom.uniform(1, 10);
            arr[0] = arr[ndx];
            arr[ndx] = 0;
        }
        // 将数组前六位转化为整数
        int rs = 0;
        StringBuilder builder = new StringBuilder();
        for (int idx = 0; idx < length; ++idx) {
            rs = rs * 10 + arr[idx];
        }
        if (rs < 100000) {
            System.out.println("5位数字:" + Arrays.toString(arr));
        }
        return rs;
    }
}
