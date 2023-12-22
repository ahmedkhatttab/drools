package com.example.drools_app.service;

import com.example.drools_app.model.RequestDTO;
import com.example.drools_app.model.Score;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service public class RegistrationScoreService {

@Autowired
private KieContainer kieContainer;

public Long calculateFare(RequestDTO requestDTO) {
    Score score = new Score();
    KieSession kieSession = kieContainer.newKieSession();
//    kieSession.setGlobal("score", score);
    kieSession.insert(requestDTO);
    kieSession.fireAllRules();
    kieSession.dispose();
    return score.getFinalScore();
}
}