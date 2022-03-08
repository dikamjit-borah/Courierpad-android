package com.courierpad.in.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class OrdersModel implements Serializable {

    public OrdersModel(int order_id, String order_date, String order_client, String order_status, String order_receiver,String order_location , String order_phone) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_client = order_client;
        this.order_receiver = order_receiver;
        this.order_location = order_location;
        this.order_phone = order_phone;
        this.order_status = order_status;
//        this.order_assigned_to = order_assigned_to;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
    }

    public OrdersModel(int order_id, String order_date, String order_client, String order_status, String order_receiver,String order_location , String order_phone,String order_email, int order_assigned_to,String order_estimated_date) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_client = order_client;
        this.order_receiver = order_receiver;
        this.order_location = order_location;
        this.order_phone = order_phone;
        this.order_status = order_status;
        this.order_assigned_to = order_assigned_to;
        this.order_email = order_email;
        this.order_estimated_date = order_estimated_date;
    }

    /**
     * order_id : 101
     * order_date : 2001-04-17T00:00:00.000Z
     * order_client : Kaustab
     * order_receiver : Biswa
     * order_location : Tezpur
     * order_phone : 8881112223
     * order_status : completed
     * order_assigned_to : 123
     * createdAt : 2021-04-29T10:45:27.000Z
     * updatedAt : 2021-04-29T10:45:27.000Z
     */

    private int order_id;
    private String order_date;
    private String order_client;
    private String order_receiver;
    private String order_location;
    private String order_phone;
    private String order_status;
    String order_estimated_date;

    public String getOrder_email() {
        return order_email;
    }

    public void setOrder_email(String order_email) {
        this.order_email = order_email;
    }

    private  String order_email;
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_client() {
        return order_client;
    }

    public void setOrder_client(String order_client) {
        this.order_client = order_client;
    }

    public String getOrder_receiver() {
        return order_receiver;
    }

    public void setOrder_receiver(String order_receiver) {
        this.order_receiver = order_receiver;
    }

    public String getOrder_location() {
        return order_location;
    }

    public void setOrder_location(String order_location) {
        this.order_location = order_location;
    }

    public String getOrder_phone() {
        return order_phone;
    }

    public void setOrder_phone(String order_phone) {
        this.order_phone = order_phone;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getOrder_assigned_to() {
        return order_assigned_to;
    }

    public void setOrder_assigned_to(int order_assigned_to) {
        this.order_assigned_to = order_assigned_to;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private int order_assigned_to;
    private String createdAt;
    private String updatedAt;
}
