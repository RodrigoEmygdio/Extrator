package org.emygdio.leitores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LeitorArquivos {

    public static List<File> buscaArquivos(Path diretorioPath, Predicate<File> filtro) throws IOException {
        return Files.list(diretorioPath)
                .map(path -> new File(path.toUri()))
                .filter(filtro)
                .collect(Collectors.toList());
    }
}
