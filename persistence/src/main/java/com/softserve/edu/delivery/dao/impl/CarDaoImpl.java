package com.softserve.edu.delivery.dao.impl;

import com.softserve.edu.delivery.dao.CarDao;
import com.softserve.edu.delivery.domain.Car;
import org.springframework.stereotype.Repository;

@Repository
public class CarDaoImpl extends BaseDaoImpl<Car, Long> implements CarDao {

    public CarDaoImpl() {
        super(Car.class);
    }

}
