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

    private static final Pattern PADRAO_TRITONGO = Pattern.compile(
            /(?iU)\b\p{L}*(?:[qg]u(?:[aáâ]i|[aáâ]u|[oóô]u|[oóô]i|iu|ão|õe|am\b)|(?:enxagu|averigu|apazigu|desagu|obliqu|adequ)(?:ei|em))\p{L}*\b/
    )

    static List<String> getTritongos(String texto) {
        if (!texto) {
            return []
        }
        return texto.findAll(PADRAO_TRITONGO)
    }

    private static final Pattern PADRAO_HIATO = Pattern.compile(
            /(?iU)\b(?!aos?\b)(?:\p{L}*(?:([aáâeéêiíoóôuú])\1|(?:[aáâeéêiíoóô]|(?<![qg])u)[íú]|(?:[aeo]|(?<![qg])u)[iu](?=[rlzmn](?![aeiouáéíóúâêôãõ])|nh)|[aáâeéêoóô][aáâeéêoóô])\p{L}*|[\p{L}&&[^áéíóúâêô]]*(?<![qgaeiouáéíóúâêôãõ])[iu][aeo]s?)\b/
    )

    static List<String> getHiatos(String texto) {
        if (!texto) {
            return []
        }
        return texto.findAll(PADRAO_HIATO)
    }

    private static final Pattern PADRAO_DITONGO = Pattern.compile(
            /(?iU)\b(?:\p{L}*(?:(?<![qg]u)(?:[aáâoóô][iu](?![rlzmn](?![aeiouáéíóúâêôãõ])|nh)|ãe|ão|õe)|[eéê][iu](?![rlzmn](?![aeiouáéíóúâêôãõ])|nh)|(?<![qg])(?:iu|ui)(?![rlzmn](?![aeiouáéíóúâêôãõ])|nh)|[qg]u[ao](?![iu]))\p{L}*|\p{L}*[áéíóúâêô]\p{L}*[iu][aeo]s?|aos?)\b/
    )

    static List<String> getDitongos(String texto) {
        if (!texto) {
            return []
        }
        return texto.findAll(PADRAO_DITONGO)
    }

    private static final Pattern PADRAO_PROPAROXITONA = Pattern.compile(
            /(?iU)\b\p{L}*[áéíóúâêô](?:[iu](?=[bcdfghjklmnpqrstvwxyzç]))?(?:[bcdfghjklmnpqrstvwxyzç]+(?:(?<=[qg])u[aeiou]|[aeiou])|(?<=[íú])[aeo])[bcdfghjklmnpqrstvwxyzç]+[aeiouãõ]+[bcdfghjklmnpqrstvwxyzç]*\b/
    )

    static List<String> getProparoxitonas(String texto) {
        if (!texto) {
            return []
        }
        return texto.findAll(PADRAO_PROPAROXITONA)
    }

    private static final Pattern PADRAO_PLURAL_S_ES = Pattern.compile(
            /(?iU)\b(?!(?:mas|mais|menos|depois|antes|atrás|através|após|país|mês|gás|caos|cais|lápis|ônibus|tênis|vírus|pires|simples|deus|trás|aliás|jamais|talvez)\b)\p{L}+(?:es|s)\b/
    )

    static String removerPlurais(String texto) {
        if (!texto) {
            return ""
        }
        return texto
                .replaceAll(PADRAO_PLURAL_S_ES, "")
                .replaceAll(/[ \t]{2,}/, " ")
                .replaceAll(/[ \t]+([.,!?;:])/, '$1')
                .replaceAll(/(?m)^[ \t]+|[ \t]+$/, "")
    }

    static List<String> getPluraisRemovidos(String texto) {
        if (!texto) {
            return []
        }
        return texto.findAll(PADRAO_PLURAL_S_ES)
    }
}