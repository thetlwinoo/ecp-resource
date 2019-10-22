package com.resource.server.repository;

import com.resource.server.domain.People;

import java.util.Optional;

public interface PeopleExtendRepository extends PeopleRepository {
    Optional<People> findPeopleByEmailAddress(String email);
    Optional<People> findPeopleByLogonName(String name);
    Optional<People> findPeopleByEmailAddressOrLogonName(String email, String name);
}
