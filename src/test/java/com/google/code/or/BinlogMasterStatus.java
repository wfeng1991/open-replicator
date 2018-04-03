package com.google.code.or;

/**
 * @Author: wfeng
 * @Date: 2018/4/3
 */
public class BinlogMasterStatus {

    private String binlogName;
    private long position;

    public String getBinlogName() {
        return binlogName;
    }

    public void setBinlogName(String binlogName) {
        this.binlogName = binlogName;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }
}
