package com.lysenkova.dbserver.entity;

public enum SQLDataType {
    INTEGER("INTEGER"), BIGINT("BIGINT"), VARCHAR("VARCHAR"), DATE("DATE"), TIMESTAMP("TIMESTAMP"), CLOB("CLOB");

    private String dataType;

    SQLDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }

    public static SQLDataType getByName(String dataType) {
        SQLDataType[] values = values();
        for (SQLDataType sqlDataType : values) {
            if(sqlDataType.getDataType().equalsIgnoreCase(dataType)) {
                return sqlDataType;
            }
        }
        throw new IllegalArgumentException("Incorrect SQL data type: " + dataType);
    }
}
