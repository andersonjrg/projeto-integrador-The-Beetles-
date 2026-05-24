package com.Beetles.systempayout.backend.admin;

import com.Beetles.systempayout.backend.admin.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AdminRepositoryTest {


    @Autowired
    AdminRepository adminRepository;



    @Test
    void findByEmail() {
    }
}