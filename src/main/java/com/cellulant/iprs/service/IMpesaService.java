package com.cellulant.iprs.service;


import com.cellulant.iprs.dto.mpesa.ExpressCallback;
import com.cellulant.iprs.dto.mpesa.ExpressRequest;

/**
 * @author joeabala
 *
 * Mpesa Service
 */
public interface IMpesaService {
    void express(ExpressRequest express);
    void expressCallback(ExpressCallback expressCallback);
}
