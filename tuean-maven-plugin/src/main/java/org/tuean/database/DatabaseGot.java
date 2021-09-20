package org.tuean.database;

import org.tuean.consts.Consts;
import org.tuean.consts.Env;
import org.tuean.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DatabaseGot {

    public static Map<String, String> getTableColumnInfo(String table) {
        Map<String, String> map = new HashMap<>();
        try {
            Connection con = DatabaseRegister.getConnection(Env.codeGenerateConfig.getDb());
            String sql = Consts.DB_INFO_SQL + table;
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                String columnName = rs.getString(0);
                String columnType = rs.getString(1);
                map.put(columnName, columnType);
            }
            return map;
        } catch (Exception var) {
            Log.getLog().error("get " + table + "column info error");
            Log.getLog().error(var);
            throw new RuntimeException("get " + table + "column info error");
        }


    }


}
