package org.zk.model;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 7/16/2017.
 */
@Table(name = "tb_user")
public class User implements Serializable{


    private static final long serialVersionUID = 3641942761064124282L;

    @Id
    private Integer id;
    private String username;

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
}
