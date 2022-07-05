package org.tuean.enums;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * the mapping info can be found at
 *      {@docRoot https://www.w3schools.com/mysql/mysql_datatypes.asp}
 *
 * all mybatis jdbcType can be found at
 *      {@docRoot https://mybatis.org/mybatis-3/apidocs/reference/org/apache/ibatis/type/JdbcType.html}
 */
public enum JdbcTypeEnum {

    // string
    CHAR                    ("char",        String.class,   "VARCHAR"),
    VARCHAR                 ("varchar",     String.class,   "VARCHAR"),
    VARBINARY               ("varbinary",   String.class,   "VARCHAR"),
    BINARY                  ("binary",      String.class,   "BINARY"),
    TINYBLOB                ("tinyblob",    String.class,   "BLOB"),
    TINYTEXT                ("tinytext",    String.class,   "VARCHAR"),
    TEXT                    ("text",        String.class,   "VARCHAR"),
    BLOB                    ("blob",        String.class,   "BLOB"),
    MEDIUMTEXT              ("mediumtext",  String.class,   "VARCHAR"),
    MEDIUMBLOB              ("mediumblob",  String.class,   "BLOB"),
    LONGTEXT                ("longtext",    String.class,   "VARCHAR"),
    LONGBLOB                ("longblob",    String.class,   "BLOG"),
    ENUM                    ("enum",        List.class,     "VARCHAR"),
    SET                     ("set",         List.class,     "VARCHAR"),

    // numeric
    BIT                     ("bit",         Byte.class,     "BIT"),
    TINYINT                 ("tinyint",     Integer.class,  "TINYINT"),
    BOOL                    ("bool",        Boolean.class,  "BOOLEAN"),
    BOOLEAN                 ("boolean",     Boolean.class,  "BOOLEAN"),
    SMALLINT                ("smallint",    Integer.class,  "SMALLINT"),
    MEDIUMINT               ("mediumint",   Integer.class,  "INTEGER"),
    INT                     ("int",         Integer.class,  "INTEGER"),
    INTEGER                 ("integer",     Integer.class,  "INTEGER"),
    BIGINT                  ("bigint",      Long.class,     "BIGINT"),
    FLOAT                   ("float",       Double.class,   "FLOAT"),
    DOUBLE                  ("double",      Double.class,   "DOUBLE"),
    DECIMAL                 ("decimal",     BigDecimal.class,"DECIMAL"),
    DEC                     ("dec",         BigDecimal.class,"DECIMAL"),


    // date
    DATE                    ("date",        LocalDate.class, "TIMESTAMP"),
    DATETIME                ("datetime",    Date.class, "TIMESTAMP"),
    TIMESTAMP               ("timestamp",   Date.class, "TIMESTAMP"),
    TIME                    ("time",        LocalTime.class, "TIMESTAMP"),
    YEAR                    ("year",        Integer.class, "TIMESTAMP")
    ;


    private String dbType;

    private Class javaType;

    private String mybatisType;

    JdbcTypeEnum(String dbType, Class javaType, String mybatisType) {
        this.dbType = dbType;
        this.javaType = javaType;
        this.mybatisType = mybatisType;
    }

    public static Class getByDBType(String dbType) {
        for (JdbcTypeEnum e : values()) {
            if (e.getDbType().equals(dbType)) return e.getJavaType();
        }
        return null;
    }

    public static String getMybatisByDBType(String dbType) {
        for (JdbcTypeEnum e : values()) {
            if (e.getDbType().equals(dbType)) return e.getMybatisType();
        }
        return null;
    }

    public String getDbType() {
        return dbType;
    }

    public Class getJavaType() {
        return javaType;
    }

    public String getMybatisType() {
        return mybatisType;
    }
}
