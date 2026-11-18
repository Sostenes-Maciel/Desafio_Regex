// Servico de extracao gramatical utilizando expressoes regulares
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
}