package com.banguoi.formatter;

import com.banguoi.model.Province;
import com.banguoi.service.province.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class ProvinceFormatter implements Formatter<Province> {

    private ProvinceService provinceService;

    @Autowired
    public ProvinceFormatter(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }
    @Override
    public Province parse(String text, Locale locale) throws ParseException {
        Long id = Long.parseLong(text);
        Province province = provinceService.findById(id);
        return province;
    }

    @Override
    public String print(Province object, Locale locale) {
        return object.toString();
    }
}
