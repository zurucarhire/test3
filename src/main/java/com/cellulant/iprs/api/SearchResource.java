package com.cellulant.iprs.api;

import com.cellulant.iprs.entity.Customer;
import com.cellulant.iprs.dto.SearchDTO;
import com.cellulant.iprs.service.ISearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/search")
@Slf4j
public class SearchResource {
    private final ISearchService searchService;

    @PostMapping("/find/{userId}")
    public ResponseEntity<Customer> find(@PathVariable(value = "userId") long userId,
                                               @Valid @RequestBody SearchDTO searchDTO) {
        log.info("find {}", searchDTO);
        return ResponseEntity.ok(searchService.find(userId, searchDTO));
    }

    @GetMapping("/requests")
    public ResponseEntity<List<?>> findRequests(@RequestParam(value = "fromDate") String fromDate,
                                                @RequestParam("toDate") String toDate,
                                                @RequestParam("tag") String tag,
                                                @RequestParam(value = "requestNumber", required = false) Long requestNumber,
                                                @RequestParam(value = "requestSerialNumber", required = false) Long requestSerialNumber,
                                                @RequestParam(value = "requestType", required = false,defaultValue = "") String requestType,
                                                @RequestParam(value = "requestBy", required = false,defaultValue = "") String requestBy) {
        log.info("findRequests {} {} {} {} {} {} {}", fromDate, toDate, tag, requestType, requestNumber, requestSerialNumber, requestBy);
        return ResponseEntity.ok(searchService.findRequests(fromDate, toDate, tag, requestType, requestNumber, requestSerialNumber, requestBy));
    }
}
