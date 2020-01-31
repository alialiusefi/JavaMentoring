package com.epam.esm.generator;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class JSONGeneratorRunner {

    public static void main(String... args) throws IOException {

        ResourceBundle generatorConfig = ResourceBundle.getBundle("generatorConfig");
        GeneratorManager manager = new GeneratorManager(generatorConfig);
        manager.createFolders(manager.getGeneratorConfig().getPath(), manager.getGeneratorConfig().getSubfolderCount() / 3);
        manager.getAllFolderCreated().add(new File(manager.getGeneratorConfig().getPath()));
        manager.start();
    }


}
