package com.carfax.test.carfaxTest.entities;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Records entity
 */
public class Records {
    @NotNull
    private List<Record> records;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
