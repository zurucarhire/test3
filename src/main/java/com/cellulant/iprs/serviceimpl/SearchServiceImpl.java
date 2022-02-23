package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.dto.RequestLogDTO;
import com.cellulant.iprs.dto.SearchDTO;
import com.cellulant.iprs.entity.Customer;
import com.cellulant.iprs.entity.CustomerArchive;
import com.cellulant.iprs.entity.ExpiryPeriod;
import com.cellulant.iprs.entity.RequestLog;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.repository.CustomerArchiveRepository;
import com.cellulant.iprs.repository.CustomerRepository;
import com.cellulant.iprs.repository.ExpiryPeriodRepository;
import com.cellulant.iprs.repository.RequestLogRepository;
import com.cellulant.iprs.service.ISearchService;
import com.cellulant.iprs.utils.IPRSConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SearchServiceImpl implements ISearchService {

    @PersistenceContext
    private EntityManager em;

    private final CustomerRepository customerRepository;
    private final CustomerArchiveRepository customerArchiveRepository;
    private final ExpiryPeriodRepository expiryPeriodRepository;
    private final RequestLogRepository requestLogRepository;

    @Override
    public Customer find(long userId, SearchDTO searchDTO) {
        // customer dummy object
        Customer c = Customer.builder()
                //.customerID(1L)
                .requestLogID(1L)
                .requestType("Passport")
                .IDNumber("223394455")
                .IDSerialNumber("62312353431")
                .firstName("Paul")
                .otherName("Njoroge")
                .surName("Kamau")
                .family("Kamau")
                .gender("Male")
                .occupation("Engineer")
                .citizenship("Kenyan")
                .ethnicGroup("Luhya")
                .pin("52135342334")
                .photo("liquoricon.jpg")
                .dateCreated(new Date())
                .dateOfBirth(new Date())
                .dateOfDeath(new Date())
                .dateOfIssue(new Date())
                .passportDateOfBirth(new Date())
                .passportExpiryDate(new Date())
                .lastIPRSUpdate(new Date())
                .placeOfBirth("Machakos")
                .placeOfDeath("nil")
                .placeOfLive("Nairobi")
                .regOffice("Ngong Rd")
                .build();

        Customer c2 = Customer.builder()
                .customerID(10L)
                .requestLogID(1L)
                .requestType("Passport")
                .IDNumber("6666")
                .IDSerialNumber("7777")
                .firstName("Joe")
                .otherName("Abala")
                .surName("Osore")
                .family("Osore")
                .gender("Male")
                .occupation("Mechanic")
                .citizenship("Nigerian")
                .ethnicGroup("Kamba")
                .pin("44455")
                .photo("liquoricon.jpg")
                .dateCreated(new Date())
                .dateOfBirth(new Date())
                .dateOfDeath(new Date())
                .dateOfIssue(new Date())
                .passportDateOfBirth(new Date())
                .passportExpiryDate(new Date())
                .lastIPRSUpdate(new Date())
                .placeOfBirth("Kitengela")
                .placeOfDeath("nil")
                .placeOfLive("Kisumu")
                .regOffice("South B")
                .build();
        // create request log
        RequestLog requestLog = RequestLog.builder()
                .requestNumber(searchDTO.getRequestNumber())
                .requestSerialNumber(searchDTO.getRequestSerialNumber())
                .requestType(searchDTO.getRequestType())
                .insertedBy(userId).build();
        requestLogRepository.save(requestLog);

        Customer customer = customerRepository.findCustomerByIDNumber(String.valueOf(searchDTO.getRequestNumber()));
        log.info("customer {}", customer);
        if (customer != null){
            ExpiryPeriod expiryPeriod = expiryPeriodRepository.findExpiryPeriod().
                    orElseThrow(() -> new ResourceNotFoundException("Expiry Period Not Found"));
            log.info("expiryPeriod {}", expiryPeriod);
            long diffInMillies = new Date().getTime() - customer.getDateCreated().getTime();
            log.info("diffInMillies {}", diffInMillies);
            log.info("timeToMinutes {}", diffInMillies/(1000 * 60));
            long timeUnit =  TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
            log.info("timeUnit {}", timeUnit);

            if (timeUnit < expiryPeriod.getExpiryPeriod()){
                return customer;
            } else {
                archiveRecord(customer);
                updateRecord(customer.getCustomerID(), c2);
                return c2;
            }

        }
        customerRepository.save(c);
        return c;
    }


@Override
public List<?> findRequests(String fromDate, String toDate, String tag, String requestType, Long requestNumber, Long requestSerialNumber, String requestBy) {
    Map<String, Object> map = Map.of("r.requestNumber", requestNumber == null ? "" : requestNumber, "r.requestType", requestType,
            "r.requestSerialNumber", requestSerialNumber == null ? "" : requestSerialNumber, "u.userName",requestBy);
    //StringBuilder jpql = new StringBuilder("SELECT new com.cellulant.iprs.dto.RequestLogDTO(u.userName, r.requestType, r.requestNumber, r.requestSerialNumber, r.dateCreated) FROM RequestLog r INNER JOIN User u ON r.insertedBy = u.userID where r.dateCreated >= '20200101' and r.dateCreated <= '20220220'");
    StringBuilder jpql = new StringBuilder(String.format(IPRSConstants.REQUEST_LOGS.get(tag), fromDate, toDate));
    map.entrySet().stream().filter(x -> !x.getValue().equals("")).forEach(x -> {
        jpql.append(" and ").append(x.getKey()).append(" = ").append('\'').append(x.getValue()).append('\'');
    });
    log.info("eee-? {}", jpql);
    log.info("hhh-?22 {}", em.createQuery(jpql.toString()).getResultList());
    return em.createQuery(jpql.toString()).getResultList();
}

    private void archiveRecord(Customer customer){
        CustomerArchive customerArchive = CustomerArchive.builder()
                .customerID(customer.getCustomerID())
                .requestLogID(customer.getRequestLogID())
                .requestType(customer.getRequestType())
                .IDNumber(customer.getIDNumber())
                .IDSerialNumber(customer.getIDSerialNumber())
                .firstName(customer.getFirstName())
                .otherName(customer.getOtherName())
                .surName(customer.getSurName())
                .family(customer.getFamily())
                .gender(customer.getGender())
                .occupation(customer.getOccupation())
                .citizenship(customer.getCitizenship())
                .ethnicGroup(customer.getEthnicGroup())
                .pin(customer.getPin())
                .dateOfBirth(customer.getDateOfBirth())
                .dateOfDeath(customer.getDateOfDeath())
                .dateOfIssue(customer.getDateOfIssue())
                .passportDateOfBirth(customer.getPassportDateOfBirth())
                .passportExpiryDate(customer.getPassportExpiryDate())
                .placeOfBirth(customer.getPlaceOfBirth())
                .placeOfDeath(customer.getPlaceOfDeath())
                .placeOfLive(customer.getPlaceOfLive())
                .regOffice(customer.getRegOffice())
                .build();

        customerArchiveRepository.save(customerArchive);
    }
    private void updateRecord(long id, Customer customer){
        Customer customer1 = Customer.builder()
                .customerID(id)
                .requestLogID(customer.getRequestLogID())
                .requestType(customer.getRequestType())
                .IDNumber(customer.getIDNumber())
                .IDSerialNumber(customer.getIDSerialNumber())
                .firstName(customer.getFirstName())
                .otherName(customer.getOtherName())
                .surName(customer.getSurName())
                .family(customer.getFamily())
                .gender(customer.getGender())
                .occupation(customer.getOccupation())
                .citizenship(customer.getCitizenship())
                .ethnicGroup(customer.getEthnicGroup())
                .pin(customer.getPin())
                .photo(customer.getPhoto())
                .dateOfBirth(customer.getDateOfBirth())
                .dateOfDeath(customer.getDateOfDeath())
                .dateOfIssue(customer.getDateOfIssue())
                .passportDateOfBirth(customer.getPassportDateOfBirth())
                .passportExpiryDate(customer.getPassportExpiryDate())
                .placeOfBirth(customer.getPlaceOfBirth())
                .placeOfDeath(customer.getPlaceOfDeath())
                .placeOfLive(customer.getPlaceOfLive())
                .regOffice(customer.getRegOffice())
                .build();

        customerRepository.save(customer1);
    }
}
