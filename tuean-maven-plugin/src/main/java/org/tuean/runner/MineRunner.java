package org.tuean.runner;

import org.tuean.database.DatabaseGot;
import org.tuean.entity.ConfigGenerator;
import org.tuean.entity.DaoMethodConfig;
import org.tuean.generator.JavaGeneratorEntity;
import org.tuean.parser.JavaFileParser;
import org.tuean.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class MineRunner {


    public static void javaRun(ConfigGenerator configGenerator, String tableName) {
        Map<String, String> dbColumnMap = DatabaseGot.getTableColumnInfo(tableName);
        boolean existFlag = JavaGeneratorEntity.checkFileExist(configGenerator, tableName);
        if (existFlag) {
            String filePath = JavaGeneratorEntity.getFileName(configGenerator);
            JavaFileParser<DaoMethodConfig> javaFileParser = new JavaFileParser<>();
            try {
                javaFileParser.parser(new FileInputStream(filePath));
            } catch (FileNotFoundException fileNotFoundException) {
                Log.getLog().error(fileNotFoundException);
            }
            DaoMethodConfig daoMethodConfig = javaFileParser.get();

        } else {
            try {
                JavaGeneratorEntity.createJavaFile(configGenerator, dbColumnMap);
            } catch (Exception var) {
                Log.getLog().error("generator table error", var);
            }
        }
    }

}
