package org.tuean.runner;

import org.tuean.database.DatabaseGot;
import org.tuean.entity.ConfigGenerator;
import org.tuean.generator.JavaGeneratorEntity;
import org.tuean.util.Log;

import java.util.Map;

public class MineRunner {


    public static void run(ConfigGenerator configGenerator, String tableName) {
        Map<String, String> dbColumnMap = DatabaseGot.getTableColumnInfo(tableName);
        boolean existFlag = JavaGeneratorEntity.checkFileExist(configGenerator, tableName);
        if (existFlag) {
            // todo
        } else {
            try {
                JavaGeneratorEntity.createJavaFile(configGenerator, dbColumnMap);
            } catch (Exception var) {
                Log.getLog().error("generator table error", var);
            }
        }
    }

}
