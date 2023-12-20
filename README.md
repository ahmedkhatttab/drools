# drools
how to use Drools business rule engine to calc score/weight of form
excel: https://www.baeldung.com/wp-content/uploads/2017/05/Screen-Shot-2017-04-26-at-12.26.59-PM-2.png

build.gradle
============
	implementation 'org.drools:drools-core:9.44.0.Final'
	implementation 'org.drools:drools-compiler:9.44.0.Final'
	implementation 'org.drools:drools-decisiontables:9.44.0.Final'
	implementation 'org.drools:drools-mvel:9.44.0.Final'


Drools Configs
===============
 import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

    private static final String drlFile = "rules/registration-rule.drl";
    private static final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieContainer kieContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}




import lombok.Data;

public class Score {
    private Long finalScore = 0L;

    public Long calcScore(Long score){
        return this.finalScore = finalScore+score;
    }

    public Long getFinalScore(){
        return this.finalScore;
    }
}




import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sa.site.clp.orm.dto.RequestDTO;

@Service
public class RegistrationScoreService {

    @Autowired
    private KieContainer kieContainer;

    public Long calculateFare(RequestDTO requestDTO) {
        Score score = new Score();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("score", score);
        kieSession.insert(requestDTO);
        kieSession.fireAllRules();
        kieSession.dispose();
        return score.getFinalScore();
    }
}






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


