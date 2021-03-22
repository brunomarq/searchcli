package com.github.brunomarq.searchcli.repository;

import com.github.brunomarq.searchcli.domain.Ticket;

import org.springframework.stereotype.Repository;

/*
* Provides searching capability and keeps the inverted index of all tickets.
*/
@Repository
public class TicketSearchRepository extends SearchRepository<Ticket> {
}
