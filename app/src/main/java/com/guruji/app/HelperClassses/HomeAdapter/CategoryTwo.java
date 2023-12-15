package com.guruji.app.HelperClassses.HomeAdapter;

public class CategoryTwo {

    private String dataName;
    private String dataId;
    private String dataAge;
    private String dataDesc;
    private String dataImp;
    private String dataPrice;

    public CategoryTwo() {
    }

    public CategoryTwo(String dataName, String dataId, String dataAge, String dataDesc, String dataImp, String dataPrice) {
        this.dataName = dataName;
        this.dataId = dataId;
        this.dataAge = dataAge;
        this.dataDesc = dataDesc;
        this.dataImp = dataImp;
        this.dataPrice = dataPrice;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getDataImp() {
        return dataImp;
    }

    public void setDataImp(String dataImp) {
        this.dataImp = dataImp;
    }

    public String getDataPrice() {
        return dataPrice;
    }

    public void setDataPrice(String dataPrice) {
        this.dataPrice = dataPrice;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataAge() {
        return dataAge;
    }

    public void setDataAge(String dataAge) {
        this.dataAge = dataAge;
    }
}

