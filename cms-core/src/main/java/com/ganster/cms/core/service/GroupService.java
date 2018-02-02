package com.ganster.cms.core.service;

import com.ganster.cms.core.base.BaseService;
import com.ganster.cms.core.exception.GroupNotFountException;
import com.ganster.cms.core.exception.PermissionNotFoundException;
import com.ganster.cms.core.exception.UserNotFoundException;
import com.ganster.cms.core.pojo.Group;
import com.ganster.cms.core.pojo.GroupExample;
import com.ganster.cms.core.pojo.User;

import java.util.List;

public interface GroupService extends BaseService<Group,GroupExample> {
    List<Group> selectByUserId(Integer id);

    List<User> selectUserByGroupId(Integer id) throws GroupNotFountException;

    List<User> selectUserByGroupName(String name) throws GroupNotFountException;

    Group findUserOwnGroup(Integer userId) throws UserNotFoundException, GroupNotFountException;

    void addUserToGroup(Integer Uid,Integer Gid) throws UserNotFoundException, GroupNotFountException;

    void addUserToGroup(Integer Uid,String gName) throws UserNotFoundException, GroupNotFountException;

    void removeUserFromGroup(Integer Uid,String gName) throws UserNotFoundException, GroupNotFountException;

    void removeUserFromGroup(Integer Uid,Integer Gid) throws UserNotFoundException, GroupNotFountException;

    Boolean hasGroup(Integer userId,String gName);

    void deleteGroup(Integer groupId);
}
