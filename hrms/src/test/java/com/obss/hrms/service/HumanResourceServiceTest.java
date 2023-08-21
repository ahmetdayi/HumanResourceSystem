package com.obss.hrms.service;

import com.obss.hrms.entity.HumanResource;
import com.obss.hrms.exception.HumanResourceNotFoundException;
import com.obss.hrms.repository.HumanResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HumanResourceServiceTest {

    private  HumanResourceRepository humanResourceRepository;
    private HumanResourceService humanResourceService;

    @BeforeEach
    void setUp() {
        humanResourceRepository = mock(HumanResourceRepository.class);
        humanResourceService = new HumanResourceService(humanResourceRepository);
    }

    @Test
    public void testFindNameByDn_whenDnExist_shouldReturnHumanResource() throws InvalidNameException {
        Name name = new LdapName("cn=ahmet");

        HumanResource expect = new HumanResource(
                name,
                "ahmet",
                "dayi",
                "ahmet dayi",
                "12345"
        );

        when(humanResourceRepository.findById(name)).thenReturn(Optional.of(expect));
        HumanResource actual = humanResourceService.findNameByDn(name);

        assertEquals(expect,actual);

        verify(humanResourceRepository).findById(name);


    }
    @Test
    public void testFindNameByDn_whenDnDoesntExist_shouldReturnHumanResource() throws InvalidNameException {
        Name name = new LdapName("cn=ahmet");

        when(humanResourceRepository.findById(name)).thenReturn(Optional.empty());

        assertThrows(HumanResourceNotFoundException.class,()-> humanResourceService.findNameByDn(name));

        verify(humanResourceRepository).findById(name);


    }
}