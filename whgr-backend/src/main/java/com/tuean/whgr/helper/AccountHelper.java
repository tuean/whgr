package com.tuean.whgr.helper;


import com.tuean.whgr.entity.db.AdminAccount;

public class AccountHelper {


    public static String getOperator() {
        AdminAccount adminAccount = AdminAccountHelper.get();
        return adminAccount.getAccount();
    }

}
