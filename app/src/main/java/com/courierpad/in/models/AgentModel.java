package com.courierpad.in.models;

public class AgentModel {
    public AgentModel(int agent_id, String agent_name, String agent_dob, String agent_doj, String agent_phone, String agent_username, String agent_password) {
        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.agent_dob = agent_dob;
        this.agent_doj = agent_doj;
        this.agent_phone = agent_phone;
        this.agent_username = agent_username;
        this.agent_password = agent_password;
    }
    public AgentModel(int agent_id, String agent_name, String agent_dob, String agent_doj, String agent_phone) {
        this.agent_id = agent_id;
        this.agent_name = agent_name;
        this.agent_dob = agent_dob;
        this.agent_doj = agent_doj;
        this.agent_phone = agent_phone;
    }

    public AgentModel(int agent_id, String agent_name) {
        this.agent_id = agent_id;
        this.agent_name = agent_name;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getAgent_name() {
        return agent_name;
    }

    public void setAgent_name(String agent_name) {
        this.agent_name = agent_name;
    }

    public String getAgent_dob() {
        return agent_dob;
    }

    public void setAgent_dob(String agent_dob) {
        this.agent_dob = agent_dob;
    }

    public String getAgent_doj() {
        return agent_doj;
    }

    public void setAgent_doj(String agent_doj) {
        this.agent_doj = agent_doj;
    }

    public String getAgent_phone() {
        return agent_phone;
    }

    public void setAgent_phone(String agent_phone) {
        this.agent_phone = agent_phone;
    }

    public String getAgent_username() {
        return agent_username;
    }

    public void setAgent_username(String agent_username) {
        this.agent_username = agent_username;
    }

    public String getAgent_password() {
        return agent_password;
    }

    public void setAgent_password(String agent_password) {
        this.agent_password = agent_password;
    }


    private int agent_id;
     private String agent_name;
     private String agent_dob;
     private String agent_doj;
     private String agent_phone;
     private String agent_username;
     private String agent_password;

}
