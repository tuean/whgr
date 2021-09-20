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
 */
public enum JdbcTypeEnum {

    // string
    CHAR                    ("char",        String.class),
    VARCHAR                 ("varchar",     String.class),
    VARBINARY               ("varbinary",   String.class),
    BINARY                  ("binary",      String.class),
    TINYBLOB                ("tinyblob",    String.class),
    TINYTEXT                ("tinytext",    String.class),
    TEXT                    ("text",        String.class),
    BLOB                    ("blob",        String.class),
    MEDIUMTEXT              ("mediumtext",  String.class),
    MEDIUMBLOB              ("mediumblob",  String.class),
    LONGTEXT                ("longtext",    String.class),
    LONGBLOB                ("longblob",    String.class),
    ENUM                    ("enum",        List.class),
    SET                     ("set",         List.class),

    // numeric
    BIT                     ("bit",         Byte.class),
    TINYINT                 ("tinyint",     Integer.class),
    BOOL                    ("bool",        Boolean.class),
    BOOLEAN                 ("boolean",     Boolean.class),
    SMALLINT                ("smallint",    Integer.class),
    MEDIUMINT               ("mediumint",   Integer.class),
    INT                     ("int",         Integer.class),
    INTEGER                 ("integer",     Integer.class),
    BIGINT                  ("bigint",      Long.class),
    FLOAT                   ("float",       Double.class),
    DOUBLE                  ("double",      Double.class),
    DECIMAL                 ("decimal",     BigDecimal.class),
    DEC                     ("dec",         BigDecimal.class),


    // date
    DATE                    ("date",        LocalDate.class),
    DATETIME                ("datetime",    LocalDateTime.class),
    TIMESTAMP               ("timestamp",   LocalDateTime.class),
    TIME                    ("time",        LocalTime.class),
    YEAR                    ("year",        Integer.class)
    ;


    private String dbType;

    private Class javaType;

    JdbcTypeEnum(String dbType, Class javaType) {
        this.dbType = dbType;
        this.javaType = javaType;
    }

    public static Class getByDBType(String dbType) {
        for (JdbcTypeEnum e : values()) {
            if (e.getDbType().equals(dbType)) return e.getJavaType();
        }
        return null;
    }

    public String getDbType() {
        return dbType;
    }

    public Class getJavaType() {
        return javaType;
    }
}
