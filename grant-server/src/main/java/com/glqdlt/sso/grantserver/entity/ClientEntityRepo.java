package com.glqdlt.sso.grantserver.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Jhun
 * 2020-02-04
 */
public interface ClientEntityRepo extends JpaRepository<ClientEntity, Integer> {
    Optional<ClientEntity> findByName(String clientId);
}
