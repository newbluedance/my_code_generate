package com.lcfsxg.generater.generate;

import com.lcfsxg.generater.config.xml.Model;
import com.lcfsxg.generater.java.FullyQualifiedJavaType;
import com.lcfsxg.generater.java.Interface;
import com.lcfsxg.generater.java.JavaVisibility;
import com.lcfsxg.generater.utils.JavaTypeConstant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lichunfeng
 * @since 2019/8/1 16:24
 */
public class ConverterGenerate extends BaseGenerate{

    /**
     * 开始生成,主要业务入口
     *
     * @return
     */
    @Override
    String generateOne(Model model) {
        //定义类编写单元
        Interface anInterface = new Interface(model.getConverterFullName());
        //类相关
        anInterface.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType superInterFace = new FullyQualifiedJavaType(JavaTypeConstant.BASE_CONVERTER_STR);

        superInterFace.addTypeArgument(new FullyQualifiedJavaType(model.getEntityFullName()));
        superInterFace.addTypeArgument(new FullyQualifiedJavaType(model.getParamFullName()));
        superInterFace.addTypeArgument(new FullyQualifiedJavaType(model.getResultFullName()));
        anInterface.addSuperInterface(superInterFace);
        //类注释和类注解
        anInterface.addJavaDocLine(getDefaultJavaDocLine(baseConfig.getBaseInfo().getAuthor()));
        anInterface.addAnnotation("@Mapper(componentModel = \"spring\")");
        //导包
        anInterface.addImportedTypes(getBaseImportedTypes());

        anInterface.addFieldLines(model.getEntityName()+"Converter INSTANCE = Mappers.getMapper("+model.getEntityName()+"Converter.class);");

        outFile(anInterface.getFormattedContent(),baseConfig.getBaseInfo().getProjectPath(),model.getConverterFullName());
        return anInterface.getFormattedContent();
    }

    /**
     * 获取基本需要导入的包
     * @return
     */
    static Set<FullyQualifiedJavaType> getBaseImportedTypes() {
        Set<FullyQualifiedJavaType> javaTypes = new HashSet<>();
        javaTypes.add(new FullyQualifiedJavaType("org.mapstruct.Mapper"));
        javaTypes.add(new FullyQualifiedJavaType("org.mapstruct.factory.Mappers"));
        return javaTypes;
    }


}
