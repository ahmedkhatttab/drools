package com.example.drools_app;

import com.example.drools_app.model.CompanyType;
import com.example.drools_app.model.RequestDTO;
import com.example.drools_app.model.StakeholderDTO;
import com.example.drools_app.service.RegistrationScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DroolsAppApplication implements CommandLineRunner {

    private final RegistrationScoreService registrationScoreService;
    public static void main(String[] args) {
        SpringApplication.run(DroolsAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        RequestDTO requestDTO = new RequestDTO();
        CompanyType comType = new CompanyType();
        comType.setId(1L);
        requestDTO.setCompanyType(comType);
        requestDTO.setOwnedByParentCompany(true);
        StakeholderDTO st1 = new StakeholderDTO();
        st1.setNationality("UAE");
        StakeholderDTO st2 = new StakeholderDTO();
        st2.setNationality("USA");
        StakeholderDTO st3 = new StakeholderDTO();
        st3.setNationality("egypt");
        requestDTO.setOwners(List.of(st1, st2, st3));
        Long score = registrationScoreService.calculateFare(requestDTO);
        log.info("----- FINAL SCORE = {}", score);
    }
}
