package com.nmefc.forcasthci.controller.management;

import com.nmefc.forcasthci.common.enumPackage.Regex;
import com.nmefc.forcasthci.common.enumPackage.ResponseMsg;
import com.nmefc.forcasthci.entity.management.User;
import com.nmefc.forcasthci.entity.management.UserExample;
import com.nmefc.forcasthci.exception.ControllerException;
import com.nmefc.forcasthci.exception.ServiceException;
import com.nmefc.forcasthci.service.management.UserService;
import com.nmefc.forcasthci.common.utils.DateTimeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: QuYuan
 * @Description: 用户管理Controller
 * @Date: Created in 10:43 2019/2/22
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * @description: 新增用户
     * @author: QuYuan
     * @date: 12:45 2019/2/22
     * @param: [user]
     * @return: int
     */
    @ResponseBody
    @PostMapping(value = "/insertUserInfo")
//    控制器完成数据校验等工作，其余业务工作交给业务层
    public String insertUserInfo(@RequestBody User user) throws ControllerException {
        if(user == null || user.getId() !=null){return ResponseMsg.REQUEST_ERROR.getValue();}
        String response = ResponseMsg.SUCCESS.getValue();
        //进行数据校验
        try {
            response = userService.check(user,response);
        } catch (ServiceException e) {
            throw new ControllerException("Insert Exception : " + e.getErrorMsg());
//            e.printStackTrace();
        }

        if(!response.equals(ResponseMsg.SUCCESS.getValue())){return response;}

//        设置软删除标记
        user.setIsDelete(false);
//        记录新建时间
        user.setGmtCreate(DateTimeUtils.date2timestamp(new Date()));
//        更新时间设置为新建时间
        user.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

//        密码加密,及关联角色在业务层中完成
//         传递给业务层
        try{
            userService.insertSelective(user);
        }catch (Exception e){
            response = ResponseMsg.EXCEPTION.getValue();
            throw new ControllerException("UserController Exception :" + e.getMessage());
        }finally {
            return response;
        }
    }
    /**
     * @description: 修改用户
     * @author: QuYuan
     * @date: 14:14 2019/2/24
     * @param: [user]
     * @return: java.lang.String
     */
    @ResponseBody
    @PostMapping(value = "/updateUserInfo")
    public String updateUserInfo(@RequestBody User user) throws ControllerException  {
        String response = "SUCCESS";
        //进行数据校验
        try {
            response = userService.check(user,response);
        } catch (Exception e) {
//            throw new ControllerException("update Exception :" + e.getMessage());
            e.printStackTrace();
        }

        if(!response.equals("SUCCESS")){return response;}
        //        更新时间设置为新建时间
        user.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

//        密码加密,及关联角色在业务层中完成
//         传递给业务层
        try{
            userService.updateByPrimaryKeySelective(user);
        }catch (Exception e){
            response = "Exception";
            throw new ControllerException("UserController Exception :" + e.getMessage());

        }finally {
            return response;
        }


    }

    /**
     * @description: 显示所有未软删除的用户信息
     * @author: QuYuan
     * @date: 21:03 2019/2/27
     * @param: []
     * @return: java.util.List<com.nmefc.forcasthci.entity.management.User>
     */
    @ResponseBody
    @GetMapping(value = "/findAllUser")
    public List<User> findAllUserInfo() throws ControllerException {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andIsDeleteNotEqualTo(true);
        List<User> userList = new ArrayList<>();
        try{
            userList = userService.selectByExample(userExample);
        }catch (Exception e){
            throw new ControllerException("UserController Exception :" + e.getMessage());
        }finally {
            return userList;
        }
    }



    /**
     * @description: 软删除用户
     * @author: QuYuan
     * @date: 15:27 2019/2/24
     * @param: [user]
     * @return: java.lang.String
     */
    @ResponseBody
    @PostMapping(value = "/deleteUserInfo")
    public String softDeleteUserInfo(User user){
        String response = "SUCCESS";
        //        更新时间设置为新建时间
        user.setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        //更改软删除标记，MySql不支持布尔类型(自动转为tinyint类型)
        user.setIsDelete(true);

//         传递给业务层
        try{
            userService.updateByPrimaryKeySelective(user);
        }catch (Exception e){
            response = "Exception";
            throw e;
        }finally {
            return response;
        }
    }

    /**
     * @description: 根据ID找到用户信息及其角色信息
     * @author: QuYuan
     * @date: 21:03 2019/2/27
     * @param: []
     * @return: java.util.List<com.nmefc.forcasthci.entity.management.User>
     */
    @ResponseBody
    @GetMapping("/findUserById")
    public User selectUserRoleByUserID(@RequestParam Integer userId) throws ControllerException {
        User user = new User();
        if(userId != null){
            try{
                user.setRoleList( userService.selectUserRoleByUserID(userId));
            }catch (Exception e){
                throw new ControllerException("UserController Exception :" + e.getMessage());
            }
        }
        return user;
    }


    /**
     * @description: 找到所有未软删除的用户信息及其角色信息
     * @author: QuYuan
     * @date: 21:03 2019/2/27
     * @param: []
     * @return: java.util.List<com.nmefc.forcasthci.entity.management.User>
     */
//    @ResponseBody
//    @GetMapping("/findAllUserWithRoleInfo")
//    public List<User> selectAllUserWithRoleInfo() throws ControllerException {
//       List<User> userList = new ArrayList<>();
//
//            try{
//                userList = userService.selectAllUserWithRoleInfo();
//            }catch (Exception e){
//                throw new ControllerException("UserController Exception :" + e.getMessage());
//            }finally {
//                return userList;
//            }
//
//    }
    /**
     * @description: 此方法为测试Shiro使用 请勿调用
     * @author: QuYuan
     * @date: 0:35 2019/3/10
     * @param: [model]
     * @return: java.lang.String
     */
    @RequiresPermissions("测试新增")
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    @ResponseBody
    public String add(Model model) {

        User user = new User();
        user.setName("王赛超");
        user.setAccount("wsc123123");
        user.setPassword("d2141safad");
        user.setIsDelete(false);
//        记录新建时间
        user.setGmtCreate(DateTimeUtils.date2timestamp(new Date()));
//        更新时间设置为新建时间
        user.setGmtModified(DateTimeUtils.date2timestamp(new Date()));

        try {
            userService.insertSelective(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        return "创建用户成功";

    }
    /**
     * @description: 此方法为测试Shiro使用 请勿调用
     * @author: QuYuan
     * @date: 0:42 2019/3/10
     * @param: [model]
     * @return: java.lang.String
     */
    @RequiresPermissions("测试删除")
    @RequestMapping(value = "/del",method = RequestMethod.GET)
    @ResponseBody
    public String del(Model model) {
        User user = new User();
        user.setAccount("wsc123123");
        List<User> userList = new ArrayList<>();
        try {
            userList = userService.accountDetected(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        String response = "删除用户名为wangsaichao用户成功";
        //        更新时间设置为新建时间
        userList.get(0).setGmtModified(DateTimeUtils.date2timestamp(new Date()));
        //更改软删除标记，MySql不支持布尔类型(自动转为tinyint类型)
        userList.get(0).setIsDelete(true);

//         传递给业务层
        try{
            userService.updateByPrimaryKeySelective(user);
        }catch (Exception e){
            response = "Exception";
            throw e;
        }finally {
            return response;
        }
    }

    /**
     * @description: 此方法为测试Shiro使用 请勿调用
     * @author: QuYuan
     * @date: 0:44 2019/3/10
     * @param: [model]
     * @return: java.lang.String
     */
    @RequiresPermissions("测试查看权限")
    @RequestMapping(value = "/view",method = RequestMethod.GET)
    @ResponseBody
    public String view(Model model) {

        return "这是用户列表页";

    }

}
