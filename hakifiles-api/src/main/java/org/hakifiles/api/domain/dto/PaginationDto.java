package org.hakifiles.api.domain.dto;

public class PaginationDto {
    private Integer limit;
    private Integer offSet;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffSet() {
        return offSet;
    }

    public void setOffSet(Integer offSet) {
        this.offSet = offSet;
    }
}
