// Suite de testes unitarios para as extracoes com Regex do ParserService
package com.zg.acelera

import com.zg.acelera.service.ParserService
import spock.lang.Specification

class ParserServiceSpec extends Specification {

    ParserService parserService

    def setup() {
        parserService = new ParserService()
    }

    def "deve listar palavras que contenham cedilha, a-til ou o-til"() {
        given: "um texto com palavras variadas, acentuadas e maiusculas"
        String texto = "A construção do coração tem lições, maçã, pão e AÇÃO no chão."

        when: "extraimos palavras com c-cedilha, a-til ou o-til"
        List<String> resultado = parserService.getPalavrasComCedilhaOuTil(texto)

        then: "apenas as palavras que possuem esses caracteres sao retornadas"
        resultado == ["construção", "coração", "lições", "maçã", "pão", "AÇÃO", "chão"]
    }

    def "deve retornar frases que se repetem e a quantidade de repeticoes"() {
        given: "um texto com versos repetidos, unicos e linhas em branco"
        String texto = """
            O vento sopra forte na janela
            A chuva cai devagar

            O vento sopra forte na janela
            O relogio marca meia noite
            A chuva cai devagar
            O vento sopra forte na janela
        """

        when: "buscamos as frases repetidas"
        Map<String, Integer> resultado = parserService.getFrasesRepetidas(texto)

        then: "apenas as frases com mais de uma ocorrencia sao retornadas com sua contagem"
        resultado == [
                "O vento sopra forte na janela": 3,
                "A chuva cai devagar"          : 2
        ]
    }

    def "deve identificar frases com exatamente quatro palavras"() {
        given: "um texto com frases de diferentes tamanhos e pontuacoes"
        String texto = """
            Tres palavras aqui.
            O sol nasceu hoje.
            Ele olhou-se no espelho!
            Esta frase tem cinco palavras agora.
            Coração bate forte, sempre!
            So duas
        """

        when: "buscamos frases com exatamente quatro palavras"
        List<String> resultado = parserService.getFrasesComQuatroPalavras(texto)

        then: "apenas as frases com quatro palavras sao retornadas"
        resultado == [
                "O sol nasceu hoje.",
                "Ele olhou-se no espelho!",
                "Coração bate forte, sempre!"
        ]
    }

    def "deve identificar palavras que contem tritongos"() {
        given: "um texto com tritongos orais, nasais e digrafos como armadilha"
        String texto = "No Paraguai, os saguões são iguais; ele averiguou e enxaguei o queijo na fogueira."

        when: "extraimos as palavras com tritongos"
        List<String> resultado = parserService.getTritongos(texto)

        then: "apenas as palavras com tritongo real sao retornadas"
        resultado == ["Paraguai", "saguões", "iguais", "averiguou", "enxaguei"]
    }

    def "deve identificar palavras que contem hiatos"() {
        given: "um texto misturando diferentes tipos de hiatos e ditongos"
        String texto = "No dia da saída, o poeta foi ao teatro sob a chuva na rua e viu o voo com saúde e raiz."

        when: "extraimos as palavras com hiatos"
        List<String> resultado = parserService.getHiatos(texto)

        then: "apenas as palavras com hiato sao retornadas"
        resultado == ["dia", "saída", "poeta", "teatro", "rua", "voo", "saúde", "raiz"]
    }

    def "deve identificar palavras que contem ditongos"() {
        given: "um texto com ditongos orais, nasais, crescentes, hiatos e tritongo"
        String texto = "A construção do coração deixou o céu azul; a mãe levou pão, queijo, água e história, mas o poeta viu a saída na rua do Paraguai."

        when: "extraimos as palavras com ditongos"
        List<String> resultado = parserService.getDitongos(texto)

        then: "apenas as palavras com ditongo sao retornadas"
        resultado == ["construção", "coração", "deixou", "céu", "mãe", "levou", "pão", "queijo", "água", "história", "viu"]
    }

    def "deve extrair palavras proparoxitonas sem confundir com paroxitonas"() {
        given: "um texto com proparoxitonas variadas e paroxitonas ou oxitonas como armadilha"
        String texto = "Na última manhã, a máquina do náufrago derramou lágrimas no período público, mas a história da água e do café fácil não teve saída."

        when: "extraimos as palavras proparoxitonas"
        List<String> resultado = parserService.getProparoxitonas(texto)

        then: "apenas as proparoxitonas reais sao retornadas"
        resultado == ["última", "máquina", "náufrago", "lágrimas", "período", "público"]
    }

    def "deve limpar o texto removendo palavras no plural terminadas em s ou es"() {
        given: "um texto com plurais em s e es, alem de palavras no singular terminadas em s"
        String texto = "Os olhos tristes viram flores, mas o país não tem ônibus.\nAs paredes caíram depois."

        when: "limpamos o texto removendo os plurais"
        String resultado = parserService.removerPlurais(texto)

        then: "os plurais sao removidos e a pontuacao e quebras de linha permanecem limpas"
        resultado == "viram, mas o país não tem ônibus.\ncaíram depois."
    }

    def "deve listar as palavras no plural que seriam removidas do texto"() {
        given: "um texto com plurais em s e es, alem de palavras no singular terminadas em s"
        String texto = "Os olhos tristes viram flores, mas o país não tem ônibus.\nAs paredes caíram depois."

        when: "listamos os plurais encontrados"
        List<String> resultado = parserService.getPluraisRemovidos(texto)

        then: "apenas os plurais terminados em s ou es sao retornados"
        resultado == ["Os", "olhos", "tristes", "flores", "As", "paredes"]
    }
}