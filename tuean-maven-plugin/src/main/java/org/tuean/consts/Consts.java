package org.tuean.consts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Consts {

    public static final String SETTING_DIR = "tuean";

    public static final String SETTING_FILE_NAME = "codeGenerator.yaml";

    public static final String DB_COLUMN_NAME = "column_name";

    public static final String DB_COLUMN_DATA_TYPE = "data_type";

    public static final String DB_COLUMN_KEY = "column_key";

    public static final List<String> DB_COLUMNS = Arrays.asList(DB_COLUMN_NAME, DB_COLUMN_DATA_TYPE, DB_COLUMN_KEY);

    public static final String COMMA = ",";

    public static final String DB_INFO_SQL = "select " + String.join(COMMA, DB_COLUMNS) + " from information_schema.COLUMNS where table_name='%s'";

    public static final String POINT = ".";

    public static final String JAVA_END = ";";

    public static final String CONFIG_LINE_SEPARATOR = "line.separator";

    public static final String EMPTY_STR = "";

    public static final String BLANK_SPACE = " ";

    public static final String FINAL = "final ";

    public static final String STATIC = "static ";

    public static final String ARG_DEFAULT = "default";

    public static final int NEXT_BLANK = 4;

    public static final String JAVA_ANNOTATION = "@";

    public static final String LEFT = "(";
    public static final String RIGHT = ")";

    public static final String EQUAL = "=";


}
