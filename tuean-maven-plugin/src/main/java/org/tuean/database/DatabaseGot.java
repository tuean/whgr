package org.tuean.database;

import org.tuean.consts.Consts;
import org.tuean.consts.Env;
import org.tuean.entity.DBColumnInfo;
import org.tuean.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseGot {

    public static List<DBColumnInfo> getTableColumnInfo(String table) {
        List<DBColumnInfo> list = new ArrayList<>();
        try {
            Connection con = DatabaseRegister.getConnection(Env.codeGenerateConfig.getDb());
            String sql = String.format(Consts.DB_INFO_SQL, table);
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                String columnName = rs.getString(1);
                String columnType = rs.getString(2);
                String columnKey = rs.getString(3);
                list.add(new DBColumnInfo(columnName, columnType, columnKey));
            }
            return list;
        } catch (Exception var) {
            Log.getLog().error("get " + table + " column info error");
            Log.getLog().error(var);
            throw new RuntimeException("get " + table + " column info error");
        }


    }


}
