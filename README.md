# Desafio Regex e TDD - Análise Gramatical de Textos

Projeto desenvolvido em Groovy com foco no aprendizado prático de Expressões Regulares (Regex) e Desenvolvimento Orientado a Testes (TDD), aplicado inicialmente sobre a letra da música *Construção*, de Chico Buarque.

A arquitetura separa a obtenção do texto da lógica de extração, permitindo reutilizar o analisador com qualquer arquivo ou fonte de texto.

## Tecnologias

- Groovy 4.0.22
- Gradle
- Spock Framework 2.3 (JUnit Platform)

## Estrutura do Projeto

```text
desafio-regex-tdd/
├── build.gradle
└── src/
    ├── main/
    │   ├── groovy/com/sandubinha/service/
    │   │   ├── LetraService.groovy
    │   │   └── ParserService.groovy
    │   └── resources/
    │       └── construcao.txt
    └── test/
        └── groovy/com/sandubinha/service/
            ├── ParserServiceSpec.groovy
            └── ConstrucaoIntegracaoSpec.groovy
```

## Funcionalidades Implementadas

### Requisitos Obrigatórios

- **Palavras com cedilha ou til (`getPalavrasComCedilhaOuTil`)**: extrai palavras que contenham `ç`, `ã` ou `õ` (maiúsculas ou minúsculas).
- **Frases repetidas (`getFrasesRepetidas`)**: identifica versos/frases duplicados e retorna um mapa com a quantidade de ocorrências.
- **Encontros vocálicos**:
    - **Ditongos (`getDitongos`)**: identifica ditongos orais, nasais e crescentes, evitando colisões com dígrafos (`qu`/`gu`) e hiatos.
    - **Tritongos (`getTritongos`)**: identifica tritongos orais e nasais.
    - **Hiatos (`getHiatos`)**: identifica vogais idênticas, `í`/`ú` tônicos, encontros de vogais fortes e hiatos seguidos de consoantes específicas.
- **Frases com 4 palavras (`getFrasesComQuatroPalavras`)**: identifica linhas contendo exatamente quatro palavras, preservando termos hifenizados como palavra única.

### Requisitos Extras

- **Proparoxítonas (`getProparoxitonas`)**: extrai palavras acentuadas na antepenúltima sílaba, diferenciando-as de paroxítonas terminadas em ditongo crescente.
- **Remoção de plurais (`removerPlurais`)**: remove palavras no plural terminadas em `s` ou `es`, preservando termos singulares/invariáveis comuns e mantendo a pontuação e quebras de linha formatadas.

## Como Executar os Testes

1. Certifique-se de que o arquivo `src/main/resources/construcao.txt` contém o texto a ser analisado.
2. No diretório raiz do projeto, execute:

```bash
./gradlew test
```

## Arquitetura e Boas Práticas

- **Desacoplamento**: `LetraService` isola a leitura de arquivos em `resources`, enquanto `ParserService` opera exclusivamente sobre strings recebidas por parâmetro.
- **TDD**: todas as regras foram implementadas seguindo o ciclo Red-Green-Refactor, com testes unitários controlados em `ParserServiceSpec` e teste de integração em `ConstrucaoIntegracaoSpec`.
- **Suporte a Unicode**: padrões compilados como constantes `static final Pattern` com a flag `(?iU)` para reconhecimento correto de caracteres acentuados e limites de palavra (`\b`) em português.