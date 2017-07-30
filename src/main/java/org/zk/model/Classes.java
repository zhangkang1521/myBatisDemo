package org.zk.model;

import java.io.Serializable;

/**
 * 班级
 * Created by Administrator on 7/30/2017.
 */
public class Classes implements Serializable{
    private static final long serialVersionUID = -9038765208392864010L;
    private Integer id;
    private String className;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
