package com.resource.server.service;

import com.resource.server.domain.People;

import java.security.Principal;

public interface MembershipService {
    People checkProfile(Principal principal, People people);
}
