package com.cellulant.iprs.service;

import com.cellulant.iprs.model.Client;

import java.util.List;

public interface IClientService {
    List<Client> findAll();
}
