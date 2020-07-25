package org.emygdio.extator.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.emygdio.leitores.LeitorArquivos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private  static String diretorioSaida;
    private static String nomeBaseArquivo;
    private Logger logger = Logger.getLogger(Main.class.getCanonicalName());

    private static Function<String, String> nomeArquivoSaida = (conteudo)->{

        Pattern pattern = Pattern.compile("^(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$");
        Matcher matcher = pattern.matcher(conteudo);
        Random aleatorio = new Random();
        aleatorio.setSeed(10);

        if(matcher.find()){
            return nomeBaseArquivo + matcher.group().replace("/","-");
        }
        return nomeBaseArquivo + System.currentTimeMillis();
    };

    public static void main(String[] args) throws Exception {
        if(args.length <3){
            throw new Exception("Informe diretorio de leitura e saida e nome base do arquivo!");
        }
        diretorioSaida = args[1];
        nomeBaseArquivo = args[2];
        LeitorArquivos
                .buscaArquivos(Paths.get(args[0]), arquivo -> arquivo.isFile() && arquivo.getPath().endsWith("pdf"))
                .stream()
                .map(Main::extraiTexto)
                .forEach(Main::salvaArquivos);

    }

    private static void salvaArquivos(String conteudo) {
        URI diretorioNomeArquivo = Paths.get(getDiretorioSaida(), getNomeArquivoSaida().apply(conteudo)).toUri();
        try {
            File arquivo = new File(diretorioNomeArquivo.getPath());
            arquivo.createNewFile();
            FileWriter writer = new FileWriter(arquivo);
            writer.write(conteudo);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private static  String extraiTexto(File pdf) {

        try {
            PDDocument document = PDDocument.load(pdf);
            System.out.println("Quandide de leaf node:" + document.getPages().getCount());
            PDFTextStripper tStripper = new PDFTextStripper();

            tStripper.setStartPage(1);
            tStripper.setEndPage(document.getNumberOfPages());

            return tStripper.getText(document);

        } catch (IOException e) {
            return null;

        }

    }

    public static Function<String, String> getNomeArquivoSaida() {
        return nomeArquivoSaida;
    }

    public static String getDiretorioSaida() {
        return diretorioSaida;
    }
}
