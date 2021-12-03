package org.tuean.runner;

import com.google.inject.internal.util.Lists;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.apache.http.util.Asserts;
import org.tuean.consts.Env;
import org.tuean.database.DatabaseGot;
import org.tuean.entity.ConfigGenerator;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaField;
import org.tuean.entity.define.JavaMethod;
import org.tuean.entity.define.JavaVisible;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.generator.JavaGenerator;
import org.tuean.init.Init;
import org.tuean.parser.java.JavaFileParser;
import org.tuean.util.Log;
import org.tuean.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tuean.consts.Consts.JAVA_END;

public class MineRunner {


    public static void javaRun(ConfigGenerator configGenerator, String tableName) {
        Map<String, String> dbParamMap = DatabaseGot.getTableColumnInfo(tableName);
        JavaClass entityClass = generateEntity(dbParamMap, tableName);
        generateDao(tableName, entityClass);
        generateXml(dbParamMap, tableName);
    }

    private static JavaClass generateEntity(Map<String, String> dbParamMap, String tableName) {
        String packageInfo = Env.codeGenerateConfig.getMapper().getEntity();
        String className = Util.uppercaseFirst(tableName);
        String outPath = Util.findEntityLocation(Env.codeGenerateConfig.getMapper(), Env.mavenProject);
        String outFile = outPath + File.separator + className + ".java";
        List<JavaField> fieldList = new ArrayList<>();
        List<JavaMethod> methodList = new ArrayList<>();
        for (String column : dbParamMap.keySet()) {
            String fieldName = Util.dbColumn2JavaField(column);
            String fieldJdbcType = dbParamMap.get(column);
            Class fieldJavaType = JdbcTypeEnum.getByDBType(fieldJdbcType);

            JavaField field = new JavaField(JavaVisible.visiblePrivate(), false, false, fieldJavaType, fieldName);
            fieldList.add(field);
            JavaMethod fieldGetMethod = Util.getMethod(field);
            JavaMethod fieldSetMethod = Util.setMethod(field);
            methodList.add(fieldGetMethod);
            methodList.add(fieldSetMethod);
        }

        JavaClass javaClazz = new JavaClass();
        javaClazz.setFieldList(fieldList);
        javaClazz.setClassName(className);
        javaClazz.setMethodList(methodList);
        javaClazz.setPackageInfo(packageInfo);
        javaClazz.setImportList(null);
        javaClazz.setClassType("class");
        Log.getLog().info(javaClazz.toString());

        javaClazz.setLocationPath(outFile);
        try {
            JavaGenerator.createJavaFile(javaClazz);
        } catch (Exception var) {
            Log.getLog().error(var);
        }
        return javaClazz;
    }

    private static JavaClass generateDao(String tableName, JavaClass entityClass) {
        String packageInfo = Env.codeGenerateConfig.getMapper().getDao();
        String outPath = Util.findDaoLocation(Env.codeGenerateConfig.getMapper(), Env.mavenProject);
        String className = Util.uppercaseFirst(tableName) + "Mapper";
        String outFile = outPath + File.separator + className + ".java";

        JavaClass mapperClass = new JavaClass();
        mapperClass.setClassName(className);
        mapperClass.setClassType("interface");
        mapperClass.setPackageInfo(packageInfo);
        mapperClass.setLocationPath(outFile);
        mapperClass.setImportList(Lists.newArrayList(entityClass.getPackageInfo() + "." + entityClass.getClassName() + JAVA_END));
        List<JavaMethod> methods = new ArrayList<>();
        methods.add(Util.insertMethod(entityClass));
        methods.add(Util.updateMethod(entityClass));
        mapperClass.setMethodList(methods);

        try {
            JavaGenerator.createJavaFile(mapperClass);
        } catch (Exception var) {
            Log.getLog().error(var);
        }

        return mapperClass;
    }

    private static void generateXml(Map<String, String> columns, String tableName) {

    }

}
