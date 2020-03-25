package com.lcfsxg.generater;

import com.lcfsxg.generater.generate.*;

/**
 * @author lichunfeng
 * @since 2019/7/25 11:41
 */
public class MainTest {
    public static void main(String[] args) {
        try {

            new EntityGenerate().generate();
            new ParamGenerate().generate();
            new ResultGenerate().generate();
            new MapperGenerate().generate();
            new ServiceGenerate().generate();
            new ServiceImplGenerate().generate();
            new ConverterGenerate().generate();
            new MapperXmlGenerate().generate();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
