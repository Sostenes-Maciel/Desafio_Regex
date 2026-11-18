package com.zg.acelera

import com.zg.acelera.service.LetraService
import com.zg.acelera.service.ParserService

class Main {

    static void main(String[] args) {
        String arquivo = args ? args[0] : "Construcao.txt"

        LetraService letraService = new LetraService()
        ParserService parserService = new ParserService()
        String texto = letraService.carregarDeArquivo(arquivo)

        println "=================================================="
        println "RELATORIO DE ANALISE GRAMATICAL: ${arquivo}"
        println "=================================================="

        imprimirLista("1. Palavras com ç, ã ou õ", parserService.getPalavrasComCedilhaOuTil(texto))
        imprimirMapa("2. Frases repetidas", parserService.getFrasesRepetidas(texto))
        imprimirLista("3. Frases com exatamente 4 palavras", parserService.getFrasesComQuatroPalavras(texto))
        imprimirLista("4. Palavras com Ditongos", parserService.getDitongos(texto))
        imprimirLista("5. Palavras com Tritongos", parserService.getTritongos(texto))
        imprimirLista("6. Palavras com Hiatos", parserService.getHiatos(texto))
        imprimirLista("7. [EXTRA] Palavras Proparoxitonas", parserService.getProparoxitonas(texto))

        List<String> pluraisRemovidos = parserService.getPluraisRemovidos(texto)
        imprimirLista("8. [EXTRA] Palavras no plural removidas (s / es)", pluraisRemovidos)

        String textoLimpo = parserService.removerPlurais(texto)
        println "\n---  Texto sem plurais (Caracteres: ${texto.length()} -> ${textoLimpo.length()}) ---"
        println textoLimpo
    }

    private static void imprimirLista(String titulo, List<String> itens) {
        println "\n--- ${titulo} (Total: ${itens.size()}) ---"
        if (itens.isEmpty()) {
            println "Nenhuma ocorrencia encontrada."
            return
        }
        println itens.join(", ")
    }

    private static void imprimirMapa(String titulo, Map<String, Integer> mapa) {
        println "\n--- ${titulo} (Total: ${mapa.size()}) ---"
        if (mapa.isEmpty()) {
            println "Nenhuma ocorrencia encontrada."
            return
        }
        mapa.each { frase, quantidade ->
            println "[${quantidade}x] ${frase}"
        }
    }
}