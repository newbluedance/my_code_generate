/**
 *    Copyright 2006-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.lcfsxg.generater.java;

import java.util.*;

import static com.lcfsxg.generater.common.OutputUtilities.calculateImports;
import static com.lcfsxg.generater.common.OutputUtilities.newLine;
import static com.lcfsxg.generater.common.StringUtility.stringHasValue;

/**
 * 主类,比InnerClass 多出package 行, imported 行
 *
 * @author Jeff Butler
 */
public class TopLevelClass extends InnerClass implements CompilationUnit {
    
    /** imported 类型 */
    private Set<FullyQualifiedJavaType> importedTypes;

    /** 静态导包 */
    private Set<String> staticImports;
    
    /** The file comment lines. */
    private List<String> fileCommentLines;

    /**
     * Instantiates a new top level class.
     *
     * @param type
     *            the jdbcTypeName
     */
    public TopLevelClass(FullyQualifiedJavaType type) {
        super(type);
        importedTypes = new TreeSet<FullyQualifiedJavaType>();
        fileCommentLines = new ArrayList<String>();
        staticImports = new TreeSet<String>();
    }


    /**
     * Instantiates a new top level class.
     *
     * @param typeName
     *            the jdbcTypeName jdbcName
     */
    public TopLevelClass(String typeName) {
        this(new FullyQualifiedJavaType(typeName));
    }

    /**
     * 添加属性 并添加对应import
     * @param field
     */
    @Override
    public void addField(Field field) {
        addImportedType(field.getType());
        super.addField(field);
    }

    /**
     * 添加多个属性
     * @param fieldList
     */
    public void addFieldList(List<Field> fieldList) {
        for (Field field : fieldList) {
            addField(field);
        }
    }

    /**
     * 设置父类
     * @param superClass
     */
    @Override
    public void setSuperClass(FullyQualifiedJavaType superClass) {
        List<FullyQualifiedJavaType> typeArguments = superClass.getTypeArguments();
        importedTypes.add(superClass);
        importedTypes.addAll(typeArguments);
        super.setSuperClass(superClass);
    }

    /**
     * 设置实现接口
     * @param superInterface
     */
    @Override
    public void addSuperInterface(FullyQualifiedJavaType superInterface) {
        List<FullyQualifiedJavaType> typeArguments = superInterface.getTypeArguments();
        importedTypes.add(superInterface);
        importedTypes.addAll(typeArguments);
        super.addSuperInterface(superInterface);
    }

    /**
     * Gets the imported types.
     *
     * @return Returns the importedTypes.
     */
    @Override
    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return Collections.unmodifiableSet(importedTypes);
    }

    /**
     * Adds the imported jdbcTypeName.
     *
     * @param importedType
     *            the imported jdbcTypeName
     */
    public void addImportedType(String importedType) {
        addImportedType(new FullyQualifiedJavaType(importedType));
    }
    
    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#addImportedType(org.mybatis.generator.api.dom.java.FullyQualifiedJavaType)
     */
    @Override
    public void addImportedType(FullyQualifiedJavaType importedType) {
        if (importedType != null
                && importedType.isExplicitlyImported()
                && !importedType.getPackageName().equals(
                        getType().getPackageName())) {
            importedTypes.add(importedType);
        }
    }

    /**
     * 生成格式好的输出内容
     * @return
     */
    @Override
    public String getFormattedContent() {
        StringBuilder sb = new StringBuilder();

        if (stringHasValue(getType().getPackageName())) {
            sb.append("package "); //$NON-NLS-1$
            sb.append(getType().getPackageName());
            sb.append(';');
            newLine(sb);
            newLine(sb);
        }

        for (String staticImport : staticImports) {
            sb.append("import static "); //$NON-NLS-1$
            sb.append(staticImport);
            sb.append(';');
            newLine(sb);
        }
        
        if (staticImports.size() > 0) {
            newLine(sb);
        }
        
        Set<String> importStrings = calculateImports(importedTypes);
        for (String importString : importStrings) {
            sb.append(importString);
            newLine(sb);
        }

        if (importStrings.size() > 0) {
            newLine(sb);
        }

        sb.append(super.getFormattedContent(0));

        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#isJavaInterface()
     */
    @Override
    public boolean isJavaInterface() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#isJavaEnumeration()
     */
    @Override
    public boolean isJavaEnumeration() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#addFileCommentLine(java.lang.String)
     */
    @Override
    public void addFileCommentLine(String commentLine) {
        fileCommentLines.add(commentLine);
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#getFileCommentLines()
     */
    @Override
    public List<String> getFileCommentLines() {
        return fileCommentLines;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#addImportedTypes(java.util.Set)
     */
    @Override
    public void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {
        this.importedTypes.addAll(importedTypes);
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#getStaticImports()
     */
    @Override
    public Set<String> getStaticImports() {
        return staticImports;
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#addStaticImport(java.lang.String)
     */
    @Override
    public void addStaticImport(String staticImport) {
        staticImports.add(staticImport);
    }

    /* (non-Javadoc)
     * @see org.mybatis.generator.api.dom.java.CompilationUnit#addStaticImports(java.util.Set)
     */
    @Override
    public void addStaticImports(Set<String> staticImports) {
        this.staticImports.addAll(staticImports);
    }
}
