package com.minhub.homebacking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.minhub.homebacking.models.Transaction;
@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
