package com.mozzie.nbp.domain.account;

import com.mozzie.nbp.domain.account.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, String> {
}

