package org.tuean.runner;

import org.tuean.database.DatabaseGot;
import org.tuean.entity.ConfigGenerator;
import org.tuean.entity.define.JavaClass;
import org.tuean.generator.JavaGenerator;
import org.tuean.init.Init;
import org.tuean.parser.java.JavaFileParser;
import org.tuean.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class MineRunner {


    public static void javaRun(ConfigGenerator configGenerator, String tableName) {
//        Map<String, String> dbColumnMap = DatabaseGot.getTableColumnInfo(tableName);
//        boolean existFlag = JavaGenerator.check(configGenerator, tableName);
//        JavaClass javaClass = null;
//        if (existFlag) {
//            String filePath = JavaGenerator.getFileName(configGenerator);
//            JavaFileParser javaFileParser = new JavaFileParser();
//            javaClass = javaFileParser.parser(new FileInputStream(filePath));
//
//        } else {
//           javaClass = Init.defaultSetting(configGenerator.getDao());
//        }
//
//        try {
////            JavaGeneratorEntity.createJavaFile(configGenerator, dbColumnMap);
//
//        } catch (Exception var) {
//            Log.getLog().error("generator table error", var);
//        }
    }

}
