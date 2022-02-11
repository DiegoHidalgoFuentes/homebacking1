package com.minhub.homebacking.repositories;

import com.minhub.homebacking.models.Client;
import com.minhub.homebacking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan , Long> {


    List<Loan> findByName (Client client);
}
