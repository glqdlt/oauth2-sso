package com.glqdlt.sso.grantserver.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jhun
 * 2020-02-06
 */
public interface LegacyAuthorityRepo extends JpaRepository<LegacyAuthorityEntity, Integer> {
}
