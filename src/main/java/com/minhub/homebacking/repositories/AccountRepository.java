package com.minhub.homebacking.repositories;

import com.minhub.homebacking.models.Account;
import com.minhub.homebacking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByNumber (String number);
    List<Account>findAccountByClient(Client client);
}
