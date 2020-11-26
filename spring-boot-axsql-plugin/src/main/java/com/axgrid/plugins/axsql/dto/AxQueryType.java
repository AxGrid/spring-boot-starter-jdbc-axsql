package com.axgrid.plugins.axsql.dto;

public enum  AxQueryType {
    INSERT,
    SELECT,
    UPDATE;

    public static AxQueryType forName(String name) {
        switch (name){
            default:
                return SELECT;
            case "!":
                return UPDATE;
            case "!<":
                return INSERT;
        }
    }
}
