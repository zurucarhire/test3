package com.cellulant.iprs.api;

import com.cellulant.iprs.dto.mpesa.C2BCallback;
import com.cellulant.iprs.dto.mpesa.ExpressCallback;
import com.cellulant.iprs.dto.mpesa.ExpressRequest;
import com.cellulant.iprs.service.IMpesaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/psm/mpesa")
public class MpesaResource {

    private final IMpesaService mpesaService;

    @PostMapping(value = "/express")
    public void express(@RequestParam("userId") Long userId,
                        @RequestParam("productId") Long productId,
                        @RequestParam("msisdn") String msisdn,
                        @RequestParam("amount") Double amount) {
        log.info("MpesaExpressRequestParams {} {} {} {} ", userId, productId, msisdn, amount);
        ExpressRequest expressRequest = new ExpressRequest();
        expressRequest.setUserID(userId);
        expressRequest.setProductID(productId);
        expressRequest.setMsisdn(msisdn);
        expressRequest.setAmount(amount);
        mpesaService.express(expressRequest);
    }

    @PostMapping(value = "/expresscallback")
    public void expressCallback(@RequestBody ExpressCallback expressCallback) {
        log.info("mpesaExpressCallbackRequestParams {}", expressCallback);
        mpesaService.expressCallback(expressCallback);
    }

    @PostMapping("/c2b/validation")
    public void c2bValidation(@RequestBody C2BCallback c2BCallback){
        log.info("c2bValidation");
        log.info("c2bValidation {}", c2BCallback);
    }

    @PostMapping("/c2b/confirmation")
    public void c2bConfirmation(@RequestBody C2BCallback c2BCallback
                                ){
        log.info("c2bConfirmation");
        log.info("c2bConfirmation {}", c2BCallback);
    }
}
