package com.scaler.bookmyshow.service.factory;

import com.scaler.bookmyshow.advice.exception.ProgramException;
import com.scaler.bookmyshow.service.annotation.BmsService;
import com.scaler.bookmyshow.utils.BMSConstant;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.*;

public abstract class AbstractServiceFactory<T> {

    private Map<String, T> serviceMap;
    private T defaultService;

    protected AbstractServiceFactory(List<T> services) {
        Map<String, T> bmsServiceMap = new HashMap<>();
        for (T service : services) {
            BmsService bmsService = AnnotationUtils.getAnnotation(service.getClass(), BmsService.class);
            assert bmsService != null;
            if (bmsService.value().equals(BMSConstant.DEFAULT)) {
                if (Objects.isNull(defaultService)) {
                    defaultService = service;
                    continue;
                } else {
                    throw new ProgramException("There can be only one default service of the Interface" + service.getClass());
                }
            }
            bmsServiceMap.computeIfPresent(bmsService.value(), (carType, existedService) -> {
                throw new ProgramException("There cant be duplicate service implementation of one interface." + existedService.getClass());
            });
            bmsServiceMap.put(bmsService.value(), service);
            serviceMap = Collections.unmodifiableMap(bmsServiceMap);
        }
    }

    public T getService(String type) {
        return serviceMap.getOrDefault(type, defaultService);
    }
}
