package org.tuean.transfer.impls;

import org.tuean.entity.DBColumnInfo;
import org.tuean.entity.XmlContent;
import org.tuean.entity.XmlNode;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaMethod;
import org.tuean.enums.InitMethod;
import org.tuean.enums.JdbcTypeEnum;
import org.tuean.enums.SqlType;
import org.tuean.transfer.ITransfer;
import org.tuean.util.Util;

import java.util.*;

import static org.tuean.consts.Consts.MAPPER_BLANK;

public class InsertTransfer implements ITransfer {

    private static int line_letters = 88;


    @Override
    public XmlNode transfer(JavaMethod method, List<DBColumnInfo> dbColumnInfoList, String tableName, JavaClass entityClass) {
        XmlNode node = new XmlNode();
        node.setId(method.getMethodName());
        node.setTag(SqlType.insert.name());

        int tier = 1;
        Map<String, Object> tagAttrs = new HashMap<>();
        tagAttrs.put("id", method.getMethodName());
        tagAttrs.put("parameterType", entityClass.getPackageInfo() + "." + method.getArgs().get(0).getArgClassStr());
        node.setTagAttrs(tagAttrs);

        List<XmlContent> fixedContents = new LinkedList<>();

        XmlContent xmlContent = new XmlContent();
        xmlContent.setBlankSize(tier * MAPPER_BLANK);
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(tableName).append("(");
        for (int i = 0; i < dbColumnInfoList.size(); i++) {
            String name = dbColumnInfoList.get(i).getName();
            int l = name.length();
            if (sb.length() + l > line_letters) {
                int lastIndex = xmlContent.getBlankSize();
                xmlContent.setLine(sb.toString());
                fixedContents.add(xmlContent);

                i = i - 1;
                xmlContent = new XmlContent(lastIndex, null);
                sb = new StringBuffer();
                continue;
            }
            sb.append(name);
            if (i != dbColumnInfoList.size() - 1) sb.append(",");
        }
        sb.append(") values (");

        for (int i = 0; i < dbColumnInfoList.size(); i++) {
            String fieldName = Util.dbColumn2JavaField(dbColumnInfoList.get(i).getName());
            String fieldJdbcType = JdbcTypeEnum.getMybatisByDBType(dbColumnInfoList.get(i).getType());
            int thisSize = fieldJdbcType.length() + 13 + fieldName.length();
            if (sb.length() + thisSize > line_letters) {
                int lastIndex = xmlContent.getBlankSize();
                xmlContent.setLine(sb.toString());
                fixedContents.add(xmlContent);

                i = i - 1;
                xmlContent = new XmlContent(lastIndex, null);
                sb = new StringBuffer();
                continue;
            }
            sb.append("#{").append(fieldName).append(", jdbcType=");
            sb.append(fieldJdbcType).append("}");
            if (i != dbColumnInfoList.size() - 1) sb.append(", ");
        }
        sb.append(")");

        xmlContent.setLine(sb.toString());


        fixedContents.add(xmlContent);
        node.setFixedContent(fixedContents);
        return node;
    }

    @Override
    public InitMethod initMethod() {
        return InitMethod.insert;
    }
}
