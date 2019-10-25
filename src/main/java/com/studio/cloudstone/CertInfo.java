package com.studio.cloudstone;

import org.joda.time.DateTime;

public class CertInfo {
    private DateTime applyDate;
    private DateTime beginDate;
    private DateTime endDate;
    private String carId;
    private String carOwnerName;
    private String certPicName;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    private String no;

    public String getCertPicName() {
        return certPicName;
    }

    public void setCertPicName(String certPicName) {
        this.certPicName = certPicName;
    }

    public DateTime getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(DateTime applyDate) {
        this.applyDate = applyDate;
    }

    public DateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(DateTime beginDate) {
        this.beginDate = beginDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }
}
