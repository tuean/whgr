package com.tuean.whgr.func.menu;

import com.tuean.whgr.dao.MenusMapper;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.helper.AdminAccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    @Autowired
    private MenusMapper menusMapper;

    /** 侧边菜单和 AI 入口用的是同一份数据，都按当前账号角色过滤 */
    public MineResp listAll() {
        AdminAccount current = AdminAccountHelper.get();
        Integer role = (current == null || current.getRole() == null) ? 2 : current.getRole();
        return MineResp.success(menusMapper.selectByRole(role));
    }

}
