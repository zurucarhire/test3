package com.cellulant.iprs.service;

import com.cellulant.iprs.model.Client;

import java.util.List;

public interface IClientService {
    Client create(long createdBy, Client client);
    Client update(long clientId, long updatedBy, Client client);
    Long delete(long clientId, long updatedBy);
    List<Client> findAll();
}
