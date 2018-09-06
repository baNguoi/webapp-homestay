package com.banguoi.service.province;

import com.banguoi.model.Province;

public interface ProvinceService {

    Iterable<Province> findAll();

    Province findById(Long id);

    void save(Province province);

    void remove(Long id);
}
