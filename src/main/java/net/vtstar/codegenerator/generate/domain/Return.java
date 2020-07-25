package net.vtstar.codegenerator.generate.domain;

import java.util.Collections;

public final class Return {
    private int code = 600;
    private String message = "SUCCESS";
    private Object data;

    private Return() {
        this.data = Collections.EMPTY_LIST;
    }

    public static Return success() {
        return new Return();
    }
    public static Return success(Object data) {
        Return r = new Return();
        r.data = data;
        return r;
    }

    public static Return failed(String message) {
        return failed(601, message);
    }

    public static Return failed(int code, String message) {
        Return r = new Return();
        r.code = code;
        r.message = message;
        return r;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }
}
