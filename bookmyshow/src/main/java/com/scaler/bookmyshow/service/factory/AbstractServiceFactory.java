package com.scaler.bookmyshow.service.factory;

import java.util.List;
import java.util.Map;

public abstract class AbstractServiceFactory<T> {
    public Map<String, T> serviceMap;
    public T defaultService;
    public AbstractServiceFactory(List<T> allService) {

    }
}
