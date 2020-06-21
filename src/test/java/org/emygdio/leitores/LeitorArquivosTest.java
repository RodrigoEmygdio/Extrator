package org.emygdio.leitores;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class LeitorArquivosTest {

    @Test
    public void devera_ler_arquivo_em_diretorio_e_filtrar_de_acordo_com_predicado() throws IOException {
        Predicate<File> predicado = arquivo -> arquivo.isFile() && arquivo.getPath().endsWith("pdf");
        Path diretorioPath = new File("src/test/resources/arquivos").toPath();
        List<File> arquivos = LeitorArquivos.buscaArquivos(diretorioPath,predicado);
        assertThat(arquivos.size(), is(equalTo(2)));

    }

}
