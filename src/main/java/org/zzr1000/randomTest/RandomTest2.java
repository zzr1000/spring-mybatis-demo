package org.zzr1000.randomTest;

import org.junit.Test;

import java.util.Random;

public class RandomTest2 {

    @Test
    public void getRandomNumber1() {//
        String ranStr = Math.random() + "";
        System.out.println(ranStr);
        int pointIndex = ranStr.indexOf(".");//indexOf
        System.out.println(pointIndex);
        System.out.println(ranStr.substring(pointIndex + 1, pointIndex + 3));//取小数点之后的第1位到第3位
    }

    //如果要生成[0,n]的随机整数的话，只需要Math.random()乘以n+1，
    //生成[0,n+1)的浮点数，再强制类型转换为int类型，只取其整数部分，
    //即可得到[0,n]的整数。
    @Test
    public void getRandomNumber2() {//
        System.out.println(Math.random());//[0,1)
        int randomA = (int)(Math.random()*10);//生成[0,9]之间的随机整数。
        System.out.println(randomA);

        int m = 5;
        int n = 20;
        int randomB = m + (int)(Math.random()*(n+1-m)); //生成从m到n的随机整数[m,n]
        System.out.println(randomB);
    }

    //使用Random类实现getRandomNumber2方法
    @Test
    public void getRandomNumber3() {
        int max = 20;
        int min = 10;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        System.out.println(s);
    }

}
