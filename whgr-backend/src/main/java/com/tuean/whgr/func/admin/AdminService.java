package com.tuean.whgr.func.admin;

import com.tuean.whgr.entity.db.AdminAccount;
import com.tuean.whgr.helper.AdminAccountHelper;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public AdminAccount getInfo() {
        return AdminAccountHelper.get();
    }

}
