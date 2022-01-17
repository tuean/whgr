package org.tuean.transfer;

import org.tuean.entity.DBColumnInfo;
import org.tuean.entity.XmlNode;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaMethod;
import org.tuean.enums.InitMethod;

import java.util.List;

public interface ITransfer {

    XmlNode transfer(JavaMethod method, List<DBColumnInfo> dbColumnInfoList, String tableName, JavaClass entityClass);

    InitMethod initMethod();

}
