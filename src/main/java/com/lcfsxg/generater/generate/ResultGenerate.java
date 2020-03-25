package com.lcfsxg.generater.generate;

import com.lcfsxg.generater.config.xml.Model;
import com.lcfsxg.generater.generate.beanConfig.BeanConfig;
import com.lcfsxg.generater.java.Field;
import com.lcfsxg.generater.java.FullyQualifiedJavaType;
import com.lcfsxg.generater.java.JavaVisibility;
import com.lcfsxg.generater.java.TopLevelClass;
import com.lcfsxg.generater.utils.JavaTypeConstant;
import com.lcfsxg.generater.utils.MyStringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lichunfeng
 * @since 2019/8/1 16:24
 */
public class ResultGenerate extends BaseGenerate {

    /**
     * 开始生成,主要业务入口
     *
     * @return
     */
    @Override
    String generateOne(Model model) {
        //定义类编写单元
        TopLevelClass topLevelClass = new TopLevelClass(model.getResultFullName());

        //类相关
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.setSuperClass(new FullyQualifiedJavaType(JavaTypeConstant.COMMON_BASE_RESULT_STR));
        //类注释和类注解
        topLevelClass.addJavaDocLine(getDefaultJavaDocLine(baseConfig.getBaseInfo().getAuthor()));
        topLevelClass.addAnnotation("@Data");
        //属性
        topLevelClass.addFieldList(getFieldList(beanConfigList));

        //导包
        topLevelClass.addImportedTypes(getBaseImportedTypes());

        outFile(topLevelClass.getFormattedContent(),baseConfig.getBaseInfo().getProjectPath(),model.getResultFullName());
        return topLevelClass.getFormattedContent();
    }

    /**
     * 获取基本需要导入的包
     *
     * @return
     */
    Set<FullyQualifiedJavaType> getBaseImportedTypes() {
        Set<FullyQualifiedJavaType> javaTypes = new HashSet<>();
        javaTypes.add(JavaTypeConstant.LOMBOK_DATA);
        return javaTypes;
    }

    /**
     * 获取所有 Field
     *
     * @param beanConfigs
     * @return
     */
    public static List<Field> getFieldList(List<BeanConfig> beanConfigs) {
        List<Field> fields = new ArrayList<>();
        Field serField = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
        serField.setVisibility(JavaVisibility.PRIVATE);
        serField.setFinal(true);
        serField.setStatic(true);
        serField.setInitializationString(MyStringUtils.getSerialVersionUID());
        fields.add(serField);

        for (BeanConfig bf : beanConfigs) {
            Field f = new Field(bf.getFieldName(), new FullyQualifiedJavaType(bf.getFieldType()));
            f.setVisibility(JavaVisibility.PRIVATE);
            f.addJavaDocLine(bf.getComments());
            fields.add(f);
        }
        return fields;
    }


}
