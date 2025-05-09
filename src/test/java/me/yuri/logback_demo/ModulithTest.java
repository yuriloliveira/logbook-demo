package me.yuri.logback_demo;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModulithTest {
    @Test
    void createApplicationModuleModel() {
        ApplicationModules modules = ApplicationModules.of(LogbackDemoApplication.class);
        modules.forEach(System.out::println);
    }

    @Test
    void verifyModularStructure() {
        ApplicationModules modules = ApplicationModules.of(LogbackDemoApplication.class);
        modules.verify();
    }
}
