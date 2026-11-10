package com.tuean.whgr.func.admin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.tuean.whgr.dao.AdminAccountMapper;
import com.tuean.whgr.entity.BaseListRequest;
import com.tuean.whgr.entity.ListResponse;
import com.tuean.whgr.entity.MineResp;
import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.helper.AdminAccountHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminAccountMapper adminAccountMapper;

    public AdminAccount getInfo() {
        return AdminAccountHelper.get();
    }

    public MineResp list(BaseListRequest request) {
        int total = adminAccountMapper.countBySearch(request.getSearchKey());
        if (total < 1) return MineResp.success(ListResponse.empty());

        List<AdminAccount> list = adminAccountMapper.selectBySearch(request);
        boolean finished = request.getStartRow() + list.size() >= total;
        return MineResp.success(new ListResponse(total, finished, list));
    }

    /**
     * id 为空新增，否则更新；密码留空表示不修改
     */
    public MineResp save(AdminAccount account) {
        if (StrUtil.isBlank(account.getAccount())) throw new RuntimeException("账号不能为空");

        String operator = currentOperatorName();
        Date now = new Date();
        if (account.getId() == null) {
            if (StrUtil.isBlank(account.getPwd())) throw new RuntimeException("新增账号必须设置密码");
            if (adminAccountMapper.selectByUserName(account.getAccount()) != null) throw new RuntimeException("账号已存在");

            account.setPwd(SecureUtil.md5(account.getPwd()));
            if (account.getRole() == null) account.setRole(1);
            if (account.getStatus() == null) account.setStatus(1);
            account.setCreateTime(now);
            account.setModifyTime(now);
            account.setCreater(operator);
            account.setModifier(operator);
            adminAccountMapper.insertSelective(account);
        } else {
            account.setPwd(StrUtil.isBlank(account.getPwd()) ? null : SecureUtil.md5(account.getPwd()));
            account.setModifyTime(now);
            account.setModifier(operator);
            adminAccountMapper.updateByPrimaryKeySelective(account);
        }
        return MineResp.success();
    }

    public MineResp delete(Long id) {
        AdminAccount current = AdminAccountHelper.get();
        if (current != null && current.getId().equals(id)) throw new RuntimeException("不能删除当前登录账号");
        adminAccountMapper.deleteByPrimaryKey(id);
        return MineResp.success();
    }

    private String currentOperatorName() {
        AdminAccount current = AdminAccountHelper.get();
        return current == null ? "system" : current.getAccount();
    }

}
