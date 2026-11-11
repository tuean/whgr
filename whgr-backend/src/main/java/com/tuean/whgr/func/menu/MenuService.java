package com.tuean.whgr.func.menu;

import cn.hutool.core.util.StrUtil;
import com.tuean.whgr.dao.MenusMapper;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.entity.db.Menus;
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

    /** 菜单管理页：全量，不过滤角色 */
    public MineResp listAllForManage() {
        return MineResp.success(menusMapper.selectAll());
    }

    /** id 为空新增，否则更新。role=0 所有人可见，N 表示 role <= N 的账号可见 */
    public MineResp save(Menus menu) {
        if (StrUtil.isBlank(menu.getMenuName())) throw new RuntimeException("菜单名称不能为空");
        if (StrUtil.isBlank(menu.getMenuUrl()) || !menu.getMenuUrl().startsWith("/")) {
            throw new RuntimeException("路由必须以 / 开头");
        }
        if (menu.getRole() == null) menu.setRole(0);
        if (menu.getMenuType() == null) menu.setMenuType(1);
        if (menu.getId() == null) {
            menusMapper.insert(menu);
        } else {
            menusMapper.update(menu);
        }
        return MineResp.success();
    }

    public MineResp delete(Long id) {
        Menus menu = menusMapper.selectByPrimaryKey(id);
        if (menu == null) throw new RuntimeException("菜单不存在");
        if ("/ai".equals(menu.getMenuUrl())) throw new RuntimeException("AI 入口是主入口，不能删除");
        menusMapper.deleteByPrimaryKey(id);
        return MineResp.success();
    }

}
