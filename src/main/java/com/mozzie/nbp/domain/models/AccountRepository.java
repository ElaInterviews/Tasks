package com.mozzie.nbp.domain.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    // Dodatkowe metody związane z dostępem do danych konta
}

