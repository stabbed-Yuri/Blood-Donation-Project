package com.BDMS.demo;

import com.BDMS.demo.persistent.HBCEntity;
import com.BDMS.demo.repository.HBCRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToHBCEntityConverter implements Converter<String, HBCEntity> {

    private final HBCRepository hbcRepository;

    public StringToHBCEntityConverter(HBCRepository hbcRepository) {
        this.hbcRepository = hbcRepository;
    }

    @Override
    public HBCEntity convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return hbcRepository.findById(Integer.parseInt(source)).orElse(null);
    }
}