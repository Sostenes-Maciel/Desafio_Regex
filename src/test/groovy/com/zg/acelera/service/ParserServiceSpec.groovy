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
}