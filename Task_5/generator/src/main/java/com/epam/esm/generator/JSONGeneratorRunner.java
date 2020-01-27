package com.epam.esm.generator;

import java.util.ResourceBundle;

public class JSONGeneratorRunner {

    public static void main(String... args) {
        ResourceBundle generatorConfig = ResourceBundle.getBundle("generatorConfig");
        GeneratorManager manager = new GeneratorManager(generatorConfig);
        manager.createFolders();
    }


}
