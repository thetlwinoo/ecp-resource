package com.resource.server.service;

import com.resource.server.domain.Addresses;
import com.resource.server.service.dto.AddressesDTO;

import java.security.Principal;
import java.util.List;

public interface AddressesExtendService {
    List<Addresses> fetchAddresses(Principal principal);

    void clearDefaultAddress(Principal principal);

    void setDefaultAddress(Principal principal, Long addressId);

    AddressesDTO crateAddresses(AddressesDTO addressesDTO, Principal principal);

    AddressesDTO updateAddresses(AddressesDTO addressesDTO, Principal principal);
}
