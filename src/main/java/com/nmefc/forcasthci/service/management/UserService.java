package com.nmefc.forcasthci.service.management;

import com.nmefc.forcasthci.entity.management.Role;
import com.nmefc.forcasthci.entity.management.User;
import com.nmefc.forcasthci.entity.management.UserExample;
import com.nmefc.forcasthci.entity.management.association.UserRoleAssociation;
import com.nmefc.forcasthci.exception.ServiceException;
import com.nmefc.forcasthci.service.BaseService;

import java.util.List;

/**
 * @Author: QuYuan
 * @Description:
 * @Date: Created in 9:53 2019/2/22
 * @Modified By:
 */
public interface UserService extends BaseService<User,UserExample,Integer>{
    /**
     * @description: 查询账号是否唯一
     * @author: QuYuan
     * @date: 21:36 2019/2/24
     * @param: [user, example]
     * @return: java.util.List<com.nmefc.forcasthci.entity.management.User>
     */
    List<User> accountDetected(User user) throws ServiceException;
    /**
     * @description: 将用户与角色关联
     * @author: QuYuan
     * @date: 19:32 2019/2/26
     * @param: [user]
     * @return: int
     */
    int saveRelativity(User user) throws ServiceException;

    /**
     * @description: 根据id解除所有相关的角色关联
     * @author: QuYuan
     * @date: 20:20 2019/2/27
     * @param: [id]
     * @return: int
     */
    int deleteRelativity(Integer id) ;


    /**
     * @description: 查找指定id的用户信息及其角色信息
     * @author: QuYuan
     * @date: 20:19 2019/2/27
     * @param: [id]
     * @return: com.nmefc.forcasthci.entity.management.User
     */
    List<Role> selectUserRoleByUserID(Integer id);
    /**
     * @description: 查找所有用户（附带角色信息）
     * @author: QuYuan
     * @date: 20:19 2019/2/27
     * @param: []
     * @return: java.util.List<com.nmefc.forcasthci.entity.management.User>
     */
//    List<User> selectAllUserWithRoleInfo();
}
