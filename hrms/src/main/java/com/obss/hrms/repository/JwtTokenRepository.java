package com.obss.hrms.repository;

import com.obss.hrms.entity.JwtToken;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends MongoRepository<JwtToken,String> {

     List<JwtToken> findAllByExpiredAndRevokedAndHumanResource_Dn(boolean expired,boolean revoked,String humanResourceId);

    Optional<JwtToken> findByToken(String token);
}
