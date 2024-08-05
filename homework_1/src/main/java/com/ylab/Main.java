package com.ylab;

import com.ylab.in.ConsoleInterface;
import com.ylab.service.*;
import com.ylab.utils.AuditLogger;

public class Main {

    private static final UserService userManager = new UserService();
    private static final AuthenticationService authenticationService = new AuthenticationService(userManager);
    private static final AuthorizationService authorizationService = new AuthorizationService();
    private static final CarService carManager = new CarService();
    private static final OrderService orderManager = new OrderService();
    private static final ServiceRequestService serviceRequestManager = new ServiceRequestService();
    private static final AuditLogger auditLogger = new AuditLogger();

    public static void main(String[] args) {
        new ConsoleInterface(authenticationService, authorizationService, userManager, carManager, orderManager,
               serviceRequestManager, auditLogger).start();
    }
}
