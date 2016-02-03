package com.sm.user.persistence.dao.impl;

import com.sm.common.criterion.Restrictions;
import com.sm.common.persistence.dao.impl.GenericDaoImpl;
import com.sm.user.persistence.dao.UserDao;
import com.sm.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Newbody on 10/10/2015.
 */
@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, String> implements UserDao {

//    @Override
//    public Boolean createUser(User user) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", user.getId());
//        params.put("username", user.getUsername());
//        params.put("password", user.getPassword());
//        params.put("createdTime", user.getCreatedTime());
//        params.put("lastUpdatedTime", user.getLastUpdatedTime());
//        params.put("status", user.getStatus());
//
//        String sql = "INSERT INTO tb_user(c_id, c_username, c_password, c_createdTime, c_lastUpdatedTime, c_status) " +
//                " VALUES(:id, :username, :password, :createdTime, :lastUpdatedTime, :status)";
//
//        Integer jdbcResult = jdbcTemplate.update(sql,params);
//        if(jdbcResult > 0){
//            return true;
//        } else {
//            return false;
//        }
//    }

//    @Override
//    public User retrieveUser(String userId) {
//        String sql = "SELECT user.c_id id, user.c_username username, user.c_password password, user.c_createdTime createdTime, user.c_lastUpdatedTime lastUpdatedTime, user.c_status status" +
//                " FROM tb_user as user WHERE user.c_id=:id";
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", userId);
//        Map<String, Object> result;
//        result = jdbcTemplate.queryForMap(sql, params);
//
//        User user = new User();
//        user.setId((String) result.get("id"));
//        user.setUsername((String) result.get("username"));
//        user.setPassword((String) result.get("password"));
//        user.setCreatedTime((Long) result.get("createdTime"));
//        user.setLastUpdatedTime((Long) result.get("lastUpdatedTime"));
//        user.setStatus((String) result.get("status"));
//
//        return user;
//    }

    @Override
    public User retrieveUserByUsername(String username) {
        return retrieve(Restrictions.eq("username", username));
    }

//    @Override
//    public Boolean modifyUser(User user) {
//        user.setLastUpdatedTime(new Date().getTime());
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("id", user.getId());
//        params.put("username", user.getUsername());
//        params.put("password", user.getPassword());
//        params.put("lastUpdatedTime", user.getLastUpdatedTime());
//        params.put("status", user.getStatus());
//
//        String sql = "UPDATE tb_user SET c_username=:username, c_password=:password, c_lastUpdatedTime=:lastUpdatedTime, c_status=:status" +
//                " WHERE c_id=:id";
//
//        Integer jdbcResult = jdbcTemplate.update(sql,params);
//        if(jdbcResult > 0){
//            return true;
//        } else {
//            return false;
//        }
//    }

//    @Override
//    public Boolean existsUserId(String userId) {
//        return super.exists("User", "id", userId);
//    }

    @Override
    public Boolean existsUsername(String username) {
        return super.exists(Restrictions.eq("username", username));
    }
}
