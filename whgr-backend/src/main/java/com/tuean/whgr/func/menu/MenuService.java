package com.tuean.whgr.func.menu;

import cn.hutool.core.util.StrUtil;
import com.tuean.whgr.dao.MenusMapper;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.entity.db.Menus;
import com.tuean.whgr.exception.NoPermissionException;
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

    /**
     * id 为空新增，否则更新。role=0 所有人可见，N 表示 role <= N 的账号可见。
     * menu_type=1 内部路由（menu_url 是 /xxx），=2 外部页面（menu_url 是完整 http(s) 地址，前端用 iframe 容器加载）。
     */
    public MineResp save(Menus menu) {
        if (StrUtil.isBlank(menu.getMenuName())) throw new RuntimeException("菜单名称不能为空");
        if (StrUtil.isBlank(menu.getMenuUrl())) throw new RuntimeException("菜单地址不能为空");
        int type = menu.getMenuType() == null ? 1 : menu.getMenuType();
        menu.setMenuType(type);
        String url = menu.getMenuUrl().trim();
        if (type == 2) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                throw new RuntimeException("外部页面地址必须以 http:// 或 https:// 开头");
            }
        } else if (!url.startsWith("/")) {
            throw new RuntimeException("路由必须以 / 开头");
        }
        if (menu.getRole() == null) menu.setRole(0);
        if (menu.getId() == null) {
            menusMapper.insert(menu);
        } else {
            menusMapper.update(menu);
        }
        return MineResp.success();
    }

    /** iframe 容器页取地址：登录即可，但必须是当前账号有权看到的那条外部页面菜单 */
    public MineResp frame(Long id) {
        Menus menu = menusMapper.selectByPrimaryKey(id);
        if (menu == null || menu.getMenuType() == null || menu.getMenuType() != 2) {
            throw new RuntimeException("页面不存在或不是外部页面");
        }
        AdminAccount current = AdminAccountHelper.get();
        int role = (current == null || current.getRole() == null) ? 2 : current.getRole();
        int visible = menu.getRole() == null ? 0 : menu.getRole();
        if (visible != 0 && role > visible) {
            throw new NoPermissionException("无权访问该页面");
        }
        return MineResp.success(menu);
    }

    public MineResp delete(Long id) {
        Menus menu = menusMapper.selectByPrimaryKey(id);
        if (menu == null) throw new RuntimeException("菜单不存在");
        if ("/ai".equals(menu.getMenuUrl())) throw new RuntimeException("AI 入口是主入口，不能删除");
        menusMapper.deleteByPrimaryKey(id);
        return MineResp.success();
    }

}
