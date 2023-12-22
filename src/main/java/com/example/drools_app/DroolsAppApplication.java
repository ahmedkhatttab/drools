package com.example.drools_app;

import com.example.drools_app.model.*;
import com.example.drools_app.service.OrderDiscountService;
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
    private final OrderDiscountService orderDiscountService;
    public static void main(String[] args) {
        SpringApplication.run(DroolsAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        RequestDTO requestDTO = new RequestDTO();
//        CompanyType comType = new CompanyType();
//        comType.setId(1L);
//        requestDTO.setCompanyType(comType);
//        requestDTO.setOwnedByParentCompany(true);
//        StakeholderDTO st1 = new StakeholderDTO();
//        st1.setNationality("UAE");
//        StakeholderDTO st2 = new StakeholderDTO();
//        st2.setNationality("USA");
//        StakeholderDTO st3 = new StakeholderDTO();
//        st3.setNationality("egypt");
//        requestDTO.setOwners(List.of(st1, st2, st3));
//        Long score = registrationScoreService.calculateFare(requestDTO);
//        log.info("----- FINAL SCORE = {}", score);
        OrderRequest orderRequest= new OrderRequest();
        orderRequest.setCustomerNumber("1111");
        orderRequest.setAge(8);
        orderRequest.setAmount(5000);
        orderRequest.setCustomerType(CustomerType.LOYAL);
        OrderDiscount discount = orderDiscountService.getDiscount(orderRequest);
        log.info(">>>>>>>>>>>>>>>>>>>>> START ");
        log.info("{}", discount);
    }
}
