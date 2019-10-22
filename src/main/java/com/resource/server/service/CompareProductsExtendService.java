package com.resource.server.service;

import com.resource.server.domain.Compares;

import java.security.Principal;

public interface CompareProductsExtendService {
    Compares addToCompare(Principal principal, Long id);

    Compares fetchCompare(Principal principal);

    Compares removeFromCompare(Principal principal, Long id);

    void emptyCompare(Principal principal);

    Boolean isInCompare(Principal principal, Long id);
}
