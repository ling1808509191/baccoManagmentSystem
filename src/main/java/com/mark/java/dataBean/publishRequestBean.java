package com.mark.java.dataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vcc on 2017/4/3.
 */
public class publishRequestBean {
    private Integer notification_id;
    private ArrayList<Integer> department_ids;

    public Integer getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(Integer notification_id) {
        this.notification_id = notification_id;
    }

    public List<Integer> getDepartment_ids() {
        return department_ids;
    }

    public void setDepartment_ids(ArrayList<Integer> department_ids) {
        this.department_ids = department_ids;
    }
}
