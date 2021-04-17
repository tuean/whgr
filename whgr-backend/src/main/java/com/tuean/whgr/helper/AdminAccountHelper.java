package com.tuean.whgr.helper;


import com.tuean.whgr.entity.db.AdminAccount;

public class AdminAccountHelper {

    private static ThreadLocal<AdminAccount> threadLocal = new ThreadLocal<>();


    public static void put(AdminAccount account) {
        threadLocal.set(account);
    }


    public static AdminAccount get() {
        return threadLocal.get();
    }

}
