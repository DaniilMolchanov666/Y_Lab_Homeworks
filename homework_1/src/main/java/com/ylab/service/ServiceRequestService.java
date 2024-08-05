package com.ylab.service;

import com.ylab.entity.ServiceRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс управляет заявками на обслуживание автомобилей.
 */
public class ServiceRequestService {

    private List<ServiceRequest> serviceRequests = new ArrayList<>();

    /**
     * Добавляет новую заявку на обслуживание автомобиля.
     *
     * @param serviceRequest Заявка для добавления.
     */
    public void addServiceRequest(ServiceRequest serviceRequest) {
        serviceRequests.add(serviceRequest);
    }

    /**
     * Возвращает список всех заявок на обслуживание автомобилей.
     *
     * @return Список всех заявок.
     */
    public List<ServiceRequest> getAllServiceRequests() {
        return new ArrayList<>(serviceRequests);
    }

    /**
     * Удаляет заявку на обслуживание автомобиля.
     *
     * @param serviceRequest Заявка для удаления.
     */
    public void removeServiceRequest(ServiceRequest serviceRequest) {
        serviceRequests.remove(serviceRequest);
    }
}
