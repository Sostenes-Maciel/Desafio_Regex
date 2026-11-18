package com.zg.acelera.service

class LetraService {

    String carregarDeArquivo(String caminhoResource) {
        InputStream stream = this.class.classLoader.getResourceAsStream(caminhoResource)
        if (!stream) {
            throw new IllegalArgumentException("Arquivo nao encontrado: ${caminhoResource}")
        }
        return stream.getText('UTF-8')
    }
}