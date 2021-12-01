package org.tuean.runner;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MineRunner {


    public static void javaRun(ConfigGenerator configGenerator, String tableName) {
        Map<String, String> dbParamMap = DatabaseGot.getTableColumnInfo(tableName);
        generateEntity(dbParamMap, tableName);

    }

    private static void generateEntity(Map<String, String> dbParamMap, String tableName) {
        String packageInfo = Env.codeGenerateConfig.getMapper().getEntity();
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
        javaClazz.setClassName(Util.uppercaseFirst(tableName));
        javaClazz.setMethodList(methodList);
        javaClazz.setPackageInfo(packageInfo);
        javaClazz.setImportList(null);
        javaClazz.setClassType("class");
        Log.getLog().info(javaClazz.toString());
        String outPath = Util.findEntityLocation(Env.codeGenerateConfig.getMapper(), Env.mavenProject);

        try {
            JavaGenerator.createJavaFile(outPath, javaClazz);
        } catch (Exception var) {
            Log.getLog().error(var);
        }
    }

    private static void generateDao(Map<String, String> dbParamMap, String tableName) {

    }

}
