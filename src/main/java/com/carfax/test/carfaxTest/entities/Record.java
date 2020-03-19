package com.carfax.test.carfaxTest.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


/**
 * Record entity
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Record {
    @NotEmpty(message = "vim cannot be null or empty")
    private String vin;
    @NotEmpty(message = "date cannot be null or empty")
    private String date;
    @PositiveOrZero(message = "data_provider_id cannot be null or negative")
    private Integer data_provider_id;
    @PositiveOrZero(message = "odometer_reading cannot be null or negative")
    private Integer odometer_reading;
    @NotNull(message = "service_details cannot be null")
    private List<@NotEmpty String> service_details;
    private Boolean odometer_rollback;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getData_provider_id() {
        return data_provider_id;
    }

    public void setData_provider_id(Integer data_provider_id) {
        this.data_provider_id = data_provider_id;
    }

    public Integer getOdometer_reading() {
        return odometer_reading;
    }

    public void setOdometer_reading(Integer odometer_reading) {
        this.odometer_reading = odometer_reading;
    }

    public List<String> getService_details() {
        return service_details;
    }

    public void setService_details(List<String> service_details) {
        this.service_details = service_details;
    }

    public Boolean getOdometer_rollback() {
        return odometer_rollback;
    }

    public void setOdometer_rollback(Boolean odometer_rollback) {
        this.odometer_rollback = odometer_rollback;
    }
}