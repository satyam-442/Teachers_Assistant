package com.example.teachersassistant.modal;

public class Laboratory {
    String compCount, forBachelor, forBoth, forMaster, isEngage, labNo;

    public Laboratory() {
    }

    public Laboratory(String compCount, String forBachelor, String forBoth, String forMaster, String isEngage, String labNo) {
        this.compCount = compCount;
        this.forBachelor = forBachelor;
        this.forBoth = forBoth;
        this.forMaster = forMaster;
        this.isEngage = isEngage;
        this.labNo = labNo;
    }

    public String getCompCount() {
        return compCount;
    }

    public void setCompCount(String compCount) {
        this.compCount = compCount;
    }

    public String getForBachelor() {
        return forBachelor;
    }

    public void setForBachelor(String forBachelor) {
        this.forBachelor = forBachelor;
    }

    public String getForBoth() {
        return forBoth;
    }

    public void setForBoth(String forBoth) {
        this.forBoth = forBoth;
    }

    public String getForMaster() {
        return forMaster;
    }

    public void setForMaster(String forMaster) {
        this.forMaster = forMaster;
    }

    public String getIsEngage() {
        return isEngage;
    }

    public void setIsEngage(String isEngage) {
        this.isEngage = isEngage;
    }

    public String getLabNo() {
        return labNo;
    }

    public void setLabNo(String labNo) {
        this.labNo = labNo;
    }
}
