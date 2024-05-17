/*package fr.isencaen.Gameplatform.service;

import jakarta.annotation.PostConstruct;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan("fr.isencaen.Gameplatform.service")
public class DroolsConfig {
    // Utilisation of the code from the website https://www.baeldung.com/drools
    @Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        //List<String> rules = Arrays.asList("fr/isencaen/Gameplateform/ressources/droolsRules/statusTictactoe.drl", "fr/isencaen/Gameplateform/ressources/droolsRules/winnerTictactoe.drl");
        //for (String rule : rules) {
        //    kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
        //}
        kieFileSystem.write(ResourceFactory.newClassPathResource("fr/isencaen/Gameplateform/ressources/droolsRules/statusTictactoe.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

}
*/