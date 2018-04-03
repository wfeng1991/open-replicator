package com.google.code.or;

/**
 * @Author: wfeng
 * @Date: 2018/4/3
 */
public class BinlogInfo {


    private String binlogName;
    private Long fileSize;

    public String getBinlogName() {
        return binlogName;
    }

    public void setBinlogName(String binlogName) {
        this.binlogName = binlogName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

}
