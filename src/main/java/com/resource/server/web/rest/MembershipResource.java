package com.resource.server.web.rest;

import com.resource.server.domain.People;
import com.resource.server.service.MembershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.security.Principal;

/**
 * MembershipResource controller
 */
@RestController
@RequestMapping("/api/membership")
public class MembershipResource {

    private final Logger log = LoggerFactory.getLogger(MembershipResource.class);
    private final MembershipService membershipService;

    public MembershipResource(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ResponseEntity checkPeopleExist(@Valid @RequestBody People people, BindingResult bindingResult, Principal principal) throws URISyntaxException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        People result = membershipService.checkProfile(principal, people);

        return new ResponseEntity<People>(result, HttpStatus.OK);
    }

}
