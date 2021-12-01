//package com.tuean.whgr.func.menu;
//
//import com.tuean.whgr.abs.AbstractListService;
//import com.tuean.whgr.entity.MineResp;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class MenuService extends AbstractListService {
//
//    private static Logger logger = LoggerFactory.getLogger(MenuService.class);
//
//    @Autowired
//    private MenusMapper menusMapper;
//
//    public MineResp menuList(MenuListRequest request) {
//        this.setListMapper(menusMapper);
//        return this.list(request);
//    }
//
//
//    @Override
//    public List transfer(List list) {
//        return list;
//    }
//
//
//    public MineResp insert(Menus menus) {
//        menusMapper.insert(menus);
//        return MineResp.success();
//    }
//
//    public MineResp update(Menus menus) {
//        menusMapper.updateByPrimaryKey(menus);
//        return MineResp.success();
//    }
//}
