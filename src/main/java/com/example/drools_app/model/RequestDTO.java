package com.example.drools_app.model;


import lombok.Data;

import java.util.List;

@Data
public class RequestDTO {
    private Long id;
    private CompanyType companyType;
    private Boolean ownedByParentCompany;
    private List<StakeholderDTO> owners;
}
