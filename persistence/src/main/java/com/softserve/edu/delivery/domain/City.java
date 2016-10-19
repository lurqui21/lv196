package com.softserve.edu.delivery.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "cities")
public class City {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    private String cityName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;
    private BigDecimal latitude;
    private BigDecimal longitude;


    public City() {
    }

    public City(String cityName, Region region) {
        this.cityName = cityName;
        this.region = region;
    }

    public City(Long cityId, String cityName, Region region) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.region = region;
    }

    public Long getCityId() {
        return cityId;
    }

    public City setCityId(Long cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public City setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public Region getRegion() {
        return region;
    }

    public City setRegion(Region region) {
        this.region = region;
        return this;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public City setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public City setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(cityId, city.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId);
    }
    @Override
    public String toString() {
        return "City [id = " + cityId + ", City = " + cityName + ", Region = " + region + "]";
    }
}
