package com.epam.esm.generator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class JSONGeneratorRunner {

    private static Logger LOG = LogManager.getLogger(JSONGeneratorRunner.class);

    public static void main(String... args) throws IOException {
        ResourceBundle generatorConfig = ResourceBundle.getBundle("generatorConfig");
        GeneratorManager manager = new GeneratorManager(generatorConfig);
        if (manager.getGeneratorConfig().getPeriodTime() == 0L) {
            LOG.error("Period time cannot be 0!");
            return;
        }
        double period = (double) manager.getGeneratorConfig().getPeriodTime();
        period = period / 1000;
        if (period > manager.getGeneratorConfig().getTestTime()) {
            LOG.error("Period time cannot be greater than test time!");
            return;
        }
        manager.createFolders(manager.getGeneratorConfig().getPath(), manager.getGeneratorConfig().getSubfolderCount() / 3);
        manager.getAllFolderCreated().add(new File(manager.getGeneratorConfig().getPath()));
        manager.start();
    }


}
