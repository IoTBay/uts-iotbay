/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;

import java.util.List;
import uts.isd.model.*;


/**
 *
 * @author Ashley
 */
public interface IAuditLogs {
    
    /* Select queries */

    
    public List<AuditLog> getAccessLogsByCustomerID(int customerId);

    public List<AuditLog> searchAccessLogsByDateForCustomerId(String start, String end, int customerId);
    
}
