package com.example.studentauth.enums;

import java.util.Objects;

public enum WorkBookVersion {
    WorkBook2003("2003", "2003版本workBook"),
    WorkBook2007("2007", "2007版本workBook"),
    WorkBook2003xls("xls", "xls后缀名结尾-2003版本workBook"),
    WorkBook2007xlsx("xlsx", "xlsx后缀名结尾-2007版本workBook");
    private String code;
    private String name;

    private WorkBookVersion(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static WorkBookVersion valueofversion(String version) {
        if (Objects.equals(WorkBook2003.getCode(), version)) {
            return WorkBook2003;
        } else if (Objects.equals(WorkBook2007.getCode(), version)) {
            return WorkBook2007;
        } else {
            return null;
        }
    }

    public static WorkBookVersion valueofsuffix(String suffix) {
        if (Objects.equals(WorkBook2003xls.getCode(), suffix)) {
            return WorkBook2003xls;
        } else if (Objects.equals(WorkBook2007xlsx.getCode(), suffix)) {
            return WorkBook2007xlsx;
        } else {
            return null;
        }
    }
}
