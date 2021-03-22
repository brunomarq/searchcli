package com.github.brunomarq.searchcli.repository;

import com.github.brunomarq.searchcli.domain.User;

import org.springframework.stereotype.Repository;

/*
* Provides searching capability and keeps the inverted index of all users.
*/
@Repository
public class UserSearchRepository extends SearchRepository<User> {
}
