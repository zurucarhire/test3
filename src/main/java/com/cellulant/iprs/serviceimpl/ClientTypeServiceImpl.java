package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.model.Client;
import com.cellulant.iprs.repository.ClientRepository;
import com.cellulant.iprs.service.IClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ClientTypeServiceImpl implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
