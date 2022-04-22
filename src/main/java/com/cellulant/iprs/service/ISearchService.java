package com.cellulant.iprs.service;

import com.cellulant.iprs.entity.Customer;
import com.cellulant.iprs.dto.SearchDTO;

import java.util.List;

public interface ISearchService {
    Customer find(long userId, SearchDTO searchDTO);
    List<?> findRequests(String fromDate, String toDate, String tag, String requestType, Long requestNumber, Long requestSerialNumber, String requestBy);
}
