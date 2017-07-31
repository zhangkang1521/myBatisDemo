package org.zk.model;

import java.io.Serializable;

/**
 * Created by Administrator on 7/16/2017.
 */
public class User implements Serializable{


    private static final long serialVersionUID = 3641942761064124282L;

    private Integer id;
    private String username;
    private String nickName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
