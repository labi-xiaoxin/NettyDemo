package edu.mju.pojo_server.pojo;

import java.util.Date;

public class UnixTime {
    private long value;

    public UnixTime(long value) {
        this.value = value;
    }

    public UnixTime() {
        this(System.currentTimeMillis() / 1000);
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "日期：" + new Date(value * 1000);
    }
}
