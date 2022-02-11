package com.minhub.homebacking.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.minhub.homebacking.models.Client;

import java.util.List;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {

     Client findByEmail(String email);

     //List<Client> findByFirstname(String firstName, Sort sort);



}
