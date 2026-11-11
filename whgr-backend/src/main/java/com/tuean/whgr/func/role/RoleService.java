package com.tuean.whgr.func.role;

import cn.hutool.core.util.StrUtil;
import com.tuean.whgr.dao.AdminAccountMapper;
import com.tuean.whgr.dao.AdminRoleMapper;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.entity.db.AdminRole;
import com.tuean.whgr.helper.AdminAccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RoleService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private AdminAccountMapper adminAccountMapper;

    public MineResp list() {
        return MineResp.success(adminRoleMapper.selectAll());
    }

    /** id 为空新增，否则更新；等级 1 是内置管理员角色，不许改等级 */
    public MineResp save(AdminRole role) {
        if (StrUtil.isBlank(role.getRoleName())) throw new RuntimeException("角色名称不能为空");
        if (role.getLevel() == null || role.getLevel() < 1) throw new RuntimeException("权限等级必须是大于 0 的整数");
        if (role.getDataScope() == null) role.setDataScope(1);

        String operator = currentOperatorName();
        Date now = new Date();
        if (role.getId() == null) {
            if (adminRoleMapper.selectByLevel(role.getLevel()) != null) {
                throw new RuntimeException("权限等级 " + role.getLevel() + " 已被占用");
            }
            if (role.getStatus() == null) role.setStatus(1);
            role.setCreateTime(now);
            role.setModifyTime(now);
            role.setCreater(operator);
            role.setModifier(operator);
            adminRoleMapper.insertSelective(role);
        } else {
            AdminRole old = adminRoleMapper.selectByPrimaryKey(role.getId());
            if (old == null) throw new RuntimeException("角色不存在");
            if (Integer.valueOf(1).equals(old.getLevel()) && !Integer.valueOf(1).equals(role.getLevel())) {
                throw new RuntimeException("内置管理员角色（等级 1）不能改等级");
            }
            if (!old.getLevel().equals(role.getLevel())) {
                if (adminAccountMapper.countByRole(old.getLevel()) > 0) {
                    throw new RuntimeException("还有账号在用等级 " + old.getLevel() + "，先改账号再改等级");
                }
                if (adminRoleMapper.selectByLevel(role.getLevel()) != null) {
                    throw new RuntimeException("权限等级 " + role.getLevel() + " 已被占用");
                }
            }
            role.setModifyTime(now);
            role.setModifier(operator);
            adminRoleMapper.updateByPrimaryKeySelective(role);
        }
        return MineResp.success();
    }

    public MineResp delete(Long id) {
        AdminRole role = adminRoleMapper.selectByPrimaryKey(id);
        if (role == null) throw new RuntimeException("角色不存在");
        if (Integer.valueOf(1).equals(role.getLevel())) throw new RuntimeException("内置管理员角色不能删除");
        if (adminAccountMapper.countByRole(role.getLevel()) > 0) throw new RuntimeException("还有账号在用这个角色，不能删除");
        adminRoleMapper.deleteByPrimaryKey(id);
        return MineResp.success();
    }

    private String currentOperatorName() {
        AdminAccount current = AdminAccountHelper.get();
        return current == null ? "system" : current.getAccount();
    }

}
