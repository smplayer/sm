package com.sm.common;

import java.util.Collection;
import java.util.List;

/**
 * Created by Newbody on 11/4/2015.
 */
public class Slice<T> {
    private Long sliceIndex = 1L;
    private Integer sliceSize = 10;
//    private Collection<String> requiredPropertyNames;
    private Long sliceQuantity = 0L;
    private Long dataQuantity = 0L;
    private List<T> dataList;

    public Long getSliceIndex() {
        return sliceIndex;
    }

    public void setSliceIndex(Long sliceIndex) {
        this.sliceIndex = sliceIndex;
    }

    public Integer getSliceSize() {
        return sliceSize;
    }

    public void setSliceSize(Integer sliceSize) {
        this.sliceSize = sliceSize;
    }

//    public Collection<String> getRequiredPropertyNames() {
//        return requiredPropertyNames;
//    }
//
//    public void setRequiredPropertyNames(Collection<String> requiredPropertyNames) {
//        this.requiredPropertyNames = requiredPropertyNames;
//    }

    public Long getDataQuantity() {
        return dataQuantity;
    }

    public void setDataQuantity(Long dataQuantity) {
        this.dataQuantity = dataQuantity;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Long getSliceQuantity(){
        if(this.sliceQuantity == null){
            if(sliceSize == 0){
                this.sliceQuantity = 1L;
            } else{
                this.sliceQuantity = dataQuantity / sliceSize;
                if(dataQuantity % sliceSize > 0){
                    this.sliceQuantity += 1;
                }
            }
        }
        return this.sliceQuantity;
    }

    public Long getDataIndex() {
        return (this.sliceIndex - 1) * this.sliceSize;
    }
}
