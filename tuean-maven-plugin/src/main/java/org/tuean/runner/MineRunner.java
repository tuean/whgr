package org.tuean.runner;

import com.google.inject.internal.util.Lists;
import org.tuean.consts.Env;
import org.tuean.database.DatabaseGot;
import org.tuean.entity.ConfigGenerator;
import org.tuean.entity.DBColumnInfo;
import org.tuean.entity.XmlNode;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaField;
import org.tuean.entity.define.JavaMethod;
import org.tuean.entity.define.JavaVisible;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.generator.JavaGenerator;
import org.tuean.generator.XmlGenerator;
import org.tuean.parser.java.JavaInterfaceFileParser;
import org.tuean.parser.xml.XmlFileParser;
import org.tuean.util.InitUtil;
import org.tuean.util.Log;
import org.tuean.util.PathUtil;
import org.tuean.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static org.tuean.consts.Consts.JAVA_END;

public class MineRunner {


    public static void javaRun(ConfigGenerator configGenerator, String tableName) {
        List<DBColumnInfo> dbColumnInfos = DatabaseGot.getTableColumnInfo(tableName);
        // java entity file will always make a pure start
        JavaClass entityClass = generateEntity(dbColumnInfos, tableName, configGenerator);
        // check dao mapper exist
        JavaClass mapperClass = generateDao(tableName, entityClass, dbColumnInfos, configGenerator);
        generateXml(dbColumnInfos, tableName, mapperClass, entityClass, configGenerator);
    }

    private static JavaClass generateEntity(List<DBColumnInfo> dbColumnInfos, String tableName, ConfigGenerator configGenerator) {
        String packageInfo = Env.codeGenerateConfig.getMapper().getEntity();
        String className = configGenerator.getEntity();
        String outPath = Util.findEntityLocation(Env.codeGenerateConfig.getMapper(), Env.mavenProject);
        String outFile = outPath + File.separator + className + ".java";
        List<JavaField> fieldList = new ArrayList<>();
        List<JavaMethod> methodList = new ArrayList<>();
        Set<String> importList = new HashSet<>();

        for (DBColumnInfo columnInfo : dbColumnInfos) {
            String fieldName = Util.dbColumn2JavaField(columnInfo.getName());
            String fieldJdbcType = columnInfo.getType();
            Class fieldJavaType = JdbcTypeEnum.getByDBType(fieldJdbcType);

            JavaField field = new JavaField(JavaVisible.visiblePrivate(), false, false, fieldJavaType, fieldName);
            fieldList.add(field);
            JavaMethod fieldGetMethod = InitUtil.getMethod(field);
            JavaMethod fieldSetMethod = InitUtil.setMethod(field);
            methodList.add(fieldGetMethod);
            methodList.add(fieldSetMethod);

            Util.chooseInput(fieldJavaType, importList);
        }

        JavaClass javaClazz = new JavaClass();
        javaClazz.setFieldList(fieldList);
        javaClazz.setClassName(className);
        javaClazz.setMethodList(methodList);
        javaClazz.setPackageInfo(packageInfo);
        javaClazz.setImportList(new ArrayList<>(importList));
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

    private static JavaClass generateDao(String tableName, JavaClass entityClass, List<DBColumnInfo> dbColumnInfos, ConfigGenerator configGenerator) {
        String packageInfo = Env.codeGenerateConfig.getMapper().getDao();
        String outPath = Util.findDaoLocation(Env.codeGenerateConfig.getMapper(), Env.mavenProject);
//        String className = Util.tableName2ClassName(tableName);
        String className = configGenerator.getDao();
        String outFile = outPath + File.separator + className + ".java";
        File oldFile = new File(outFile);
        JavaClass initClass = InitUtil.initDaoClass(className, packageInfo, outFile, entityClass, dbColumnInfos);
        JavaClass mapperClass = initClass;
        if (oldFile.exists()) {
            try {
                JavaInterfaceFileParser parser = new JavaInterfaceFileParser();
                JavaClass javaClass = parser.parser(new FileInputStream(oldFile));
                mapperClass = Util.unionDaoJavaClass(initClass, javaClass);
            } catch (Exception var) {
                Log.getLog().info("parser old java file error");
                throw new RuntimeException("parser old java file error");
            }
        }

        try {
            JavaGenerator.createJavaFile(mapperClass);
        } catch (Exception var) {
            Log.getLog().error(var);
        }

        return mapperClass;
    }

    private static void generateXml(List<DBColumnInfo> dbColumnInfos, String tableName, JavaClass mapperClass, JavaClass entityClass, ConfigGenerator configGenerator) {
        String xmlPath = PathUtil.configPath2sysPath(Env.codeGenerateConfig.getMapper().getXml());
        String xmlFileName = configGenerator.getXml() + ".xml";
        String fileDirPath = PathUtil.getResourcePath(Env.mavenProject, xmlPath);
        String filePath = fileDirPath + File.separator + xmlFileName;
        File mapperFile = new File(filePath);

        XmlNode initMapperNode = InitUtil.initMapperXml(dbColumnInfos, tableName, mapperClass, entityClass);
        if (mapperFile.exists()) {
            try {
                XmlFileParser parser = new XmlFileParser();
                XmlNode oldNode = parser.parser(new FileInputStream(mapperFile));
                initMapperNode = Util.unionXml(initMapperNode, oldNode);
            } catch (Exception var) {
                Log.getLog().info("parser old xml file error");
                Log.getLog().error(var);
                throw new RuntimeException("parser old xml file error");
            }
        }


        try {
            Util.resort(initMapperNode);
            XmlGenerator.generateXml(initMapperNode, filePath);
        } catch (Exception var) {
            Log.getLog().error(var);
        }
    }

}
