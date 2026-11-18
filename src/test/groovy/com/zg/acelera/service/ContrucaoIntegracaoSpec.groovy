package com.zg.acelera.service

import spock.lang.Specification

class ConstrucaoIntegracaoSpec extends Specification {

    LetraService letraService
    ParserService parserService
    String letraCompleta

    def setup() {
        letraService = new LetraService()
        parserService = new ParserService()
        letraCompleta = letraService.carregarDeArquivo("Construcao.txt")
    }

    def "deve executar todas as extracoes obrigatorias e extras na letra de Construcao"() {
        when: "executamos todos os metodos do parser sobre a letra completa"
        List<String> cedilhaOuTil = parserService.getPalavrasComCedilhaOuTil(letraCompleta)
        Map<String, Integer> frasesRepetidas = parserService.getFrasesRepetidas(letraCompleta)
        List<String> frasesQuatroPalavras = parserService.getFrasesComQuatroPalavras(letraCompleta)
        List<String> ditongos = parserService.getDitongos(letraCompleta)
        List<String> tritongos = parserService.getTritongos(letraCompleta)
        List<String> hiatos = parserService.getHiatos(letraCompleta)
        List<String> proparoxitonas = parserService.getProparoxitonas(letraCompleta)
        String textoSemPlurais = parserService.removerPlurais(letraCompleta)

        then: "as extracoes retornam os padroes esperados da musica"
        !cedilhaOuTil.isEmpty()
        frasesRepetidas != null
        frasesQuatroPalavras != null
        !ditongos.isEmpty()
        !hiatos.isEmpty()
        proparoxitonas.size() > 20
        !textoSemPlurais.isEmpty()
        tritongos != null
    }
}