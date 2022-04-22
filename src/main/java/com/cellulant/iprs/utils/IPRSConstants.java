package com.cellulant.iprs.utils;

import java.util.HashMap;
import java.util.Map;

public class IPRSConstants {
    public static int AUTH_SUCCESS=131;
    public static int GENERIC_SUCCESS=173;
    public static int GENERIC_FAILURE=174;
    public static int PHP_ASS_ITERATION=8;
    public static String initialisationVector = "71b2929e5f4bc2c0";
    public static String secretKey = "065fb12087e8e6d7";
    public static String  REQUEST_TYPES []={
            "NationalID","Passport","AlienID","Fingerprint"
    };
    public static Map<String, String> REQUEST_LOGS = new HashMap();
           // "REFRESHED_LOG", "SELECT new com.cellulant.iprs.dto.RefreshedLogDTO(r.requestType, r.requestNumber, r.requestSerialNumber, ca.customerArchiveID, ca.customerID, c.IDNumber, c.IDSerialNumber, c.firstName, c.otherName, c.surName, c.family, c.gender, c.ethnicGroup, c.occupation, c.pin, c.citizenship, c.placeOfBirth, c.placeOfLive, c.placeOfDeath, c.regOffice, c.dateOfBirth, c.dateOfDeath, c.dateOfIssue, c.passportExpiryDate, ca.dateCreated) FROM CustomerArchive ca INNER JOIN Customer c ON ca.customerID = c.customerID INNER JOIN RequestLog r ON r.requestLogID = ca.requestLogID where ca.dateCreated >= '%s' and ca.dateCreated < '%s'");
    public static String CUSTOMER_REQ_TYPE[]={"archive","live"};
}
