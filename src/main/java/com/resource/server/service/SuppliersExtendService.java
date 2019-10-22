package com.resource.server.service;

import com.resource.server.service.dto.SuppliersDTO;

import java.security.Principal;
import java.util.Optional;

public interface SuppliersExtendService {
    Optional<SuppliersDTO> getSupplierByPrincipal(Principal principal);
}
