# drools
how to use Drools business rule engine to calc score/weight of form
excel: https://www.baeldung.com/wp-content/uploads/2017/05/Screen-Shot-2017-04-26-at-12.26.59-PM-2.png
https://www.youtube.com/watch?v=fFvobeuFHvk

Application.class
=================
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
		log.info("-------------------------------- FINAL SCORE = {}", score);

	}


 registration-rule.drl
 =====================
 path: resources/rules/registration-rule.drl
 
import sa.site.clp.orm.dto.RequestDTO;
import sa.site.clp.orm.dto.StakeholderDTO;
global sa.site.business._role_engine.Score score;

dialect "mvel"

rule "CompanyType Rule"
    when
        RequestDTO(companyType.getId == 1)
    then
        System.out.println("------------- Add 10% for CompanyType ---------------");
        score.calcScore(10);
end


rule "OwnedByParentCompany Rule"
    when
        RequestDTO(ownedByParentCompany==true)
    then
        System.out.println("------------- Add 5% for OwnedByParentCompany ---------------");
        score.calcScore(5);
end


rule "Owners Nationality Rule"
    when
        RequestDTO($owner: owners)
        StakeholderDTO(nationality == "egypt" || nationality == "sa") from $owner
    then
        System.out.println("------------- Add 7% for Owners Nationality ---------------");
        score.calcScore(7);
end


