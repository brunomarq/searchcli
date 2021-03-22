package com.github.brunomarq.searchcli.repository;

import com.github.brunomarq.searchcli.domain.Organization;

import org.springframework.stereotype.Repository;

/*
* Provides searching capability for organizations and keeps the inverted index of all orgs.
*/
@Repository
public class OrganizationSearchRepository extends SearchRepository<Organization> {
}
