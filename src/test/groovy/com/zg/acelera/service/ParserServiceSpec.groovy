// Suite de testes unitarios para as extracoes com Regex do ParserService
package com.zg.acelera.service

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
}