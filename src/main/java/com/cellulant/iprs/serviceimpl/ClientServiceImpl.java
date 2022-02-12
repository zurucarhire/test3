package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.exception.ResourceFoundException;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.exception.UnprocessedResourceException;
import com.cellulant.iprs.model.ChangeLog;
import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.model.User;
import com.cellulant.iprs.repository.ChangeLogRepository;
import com.cellulant.iprs.repository.ClientRepository;
import com.cellulant.iprs.service.IChangeLogService;
import com.cellulant.iprs.service.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final ChangeLogRepository changeLogRepository;
    private final IChangeLogService changeLogService;

    @Override
    public Client create(long createdBy, Client client) {
        clientRepository.findByClientNameIgnoreCase(client.getClientName()).ifPresent(s -> {
            throw new UnprocessedResourceException("Client exists " + s.getClientName());
        });

        Client client1 = clientRepository.save(client);
        changeLogService.create(createdBy, "create client " + client.getClientName());
        return client1;
    }

    @Override
    public Client update(long clientId, long updatedBy, Client client) {

        Client client1 = clientRepository.findByClientID(clientId).
                orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        if (!client.getClientName().equals(client1.getClientName())){
            clientRepository.findByClientNameIgnoreCase(client.getClientName()).ifPresent(s -> {
                throw new UnprocessedResourceException("Client exists " + s.getClientName());
            });
        }

        client1.setClientName(client.getClientName());
        client1.setClientDescription(client.getClientDescription());
        client1.setUpdatedBy(updatedBy);
        client1.setActive(client.getActive());
        //client1.setDateModified(new Date());

        Client updatedClient = clientRepository.save(client1);
        changeLogService.create(updatedBy, "update client " + client.getClientName());
        return updatedClient;
    }

    @Override
    public Long delete(long clientId, long updatedBy) {
        Client client = clientRepository.findByClientID(clientId).
                orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        clientRepository.deleteByClientID(clientId);
        changeLogService.create(updatedBy, "delete client " + client.getClientName());
        return clientId;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> findAllActive() {
        return clientRepository.findAllActive();
    }
}
