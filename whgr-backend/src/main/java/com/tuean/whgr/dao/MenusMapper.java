package com.tuean.whgr.dao;
import com.tuean.whgr.entity.db.Menus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenusMapper {
      Integer insert(Menus menus);
      Integer update(Menus menus);

      /** 按角色取可见菜单：role = 0 或 role >= 当前账号 role */
      List<Menus> selectByRole(@Param("role") Integer role);
}
