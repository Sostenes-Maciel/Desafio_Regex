package com.zg.acelera.service

import java.util.regex.Pattern

class ParserService {

    private static final Pattern PADRAO_CEDILHA_OU_TIL = Pattern.compile(/(?iu)\p{L}*[çãõ]\p{L}*/)

    static List<String> getPalavrasComCedilhaOuTil(String texto) {
        if (!texto) {
            return []
        }
        return texto.findAll(PADRAO_CEDILHA_OU_TIL)
    }

    private static final Pattern PADRAO_LINHA_NAO_VAZIA = Pattern.compile(/(?m)^\s*(\S.*?)\s*$/)

    static Map<String, Integer> getFrasesRepetidas(String texto) {
        if (!texto) {
            return [:]
        }

        List<String> frases = (texto =~ PADRAO_LINHA_NAO_VAZIA).collect { it[1] as String }

        return frases
                .countBy { it }
                .findAll { it.value > 1 }
    }

    private static final Pattern PADRAO_QUATRO_PALAVRAS = Pattern.compile(
            /(?m)^[^\p{L}\r\n]*(?:\p{L}++(?:-\p{L}++)*+[^\p{L}\r\n]+){3}\p{L}++(?:-\p{L}++)*+[^\p{L}\r\n]*$/
    )

    static List<String> getFrasesComQuatroPalavras(String texto) {
        if (!texto) {
            return []
        }
        return texto.findAll(PADRAO_QUATRO_PALAVRAS).collect { it.trim() }
    }
}