package com.lcfsxg.generater.utils;

import java.io.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lichunfeng
 * @since 2019/8/1 19:27
 */
public class MyStringUtils {
    private static Pattern LINE_PATTERN = Pattern.compile("_(\\w)");
    /**
     * 字符串蛇形转驼峰
     */
    public static String lineToHump(String str) {
        Matcher matcher = LINE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String getSerialVersionUID() {
        StringBuffer uid = new StringBuffer(19);
        for (int i = 0; i <= 16; i++) {   //生成一个6位的序列号
            int spy;
            if (i == 0) {
                spy = (int) (new Random().nextInt(8) + 1);
            } else {
                spy = (int) (Math.random() * 10);
            }
            uid.append(spy);
        }
        uid.append("L");
        return uid.toString();
    }
    /**
     *
     * @param s
     * @param path
     */
    public static void outFile(String s, String path) {
        File file = new File(path);
        try {
            createNewFile(file);
            FileOutputStream fop = new FileOutputStream(file);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fop, "utf-8");

            BufferedWriter writer = new BufferedWriter(outputWriter);
            writer.write(s);
            writer.flush();
            System.out.println("已经生成文件到:" + path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static void createNewFile(File file){
        if(!file.getParentFile().exists()){
            try {
                file.getParentFile().mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
