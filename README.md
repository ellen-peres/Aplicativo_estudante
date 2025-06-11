# Aplicativo Estudante 📚

## 📖 Visão Geral
O **Aplicativo Estudante** é um aplicativo móvel desenvolvido para auxiliar estudantes na organização e acompanhamento de seu desempenho acadêmico. A aplicação permite o registro de notas, cálculo de médias ponderadas e gerenciamento de anotações de aula, proporcionando uma interface intuitiva e eficiente para a gestão de atividades escolares.

## 🎯 Objetivo Geral
O objetivo principal do projeto é desenvolver uma aplicação Android que simplifique a organização acadêmica dos estudantes, oferecendo ferramentas essenciais como cálculo automático de médias e gerenciamento de anotações, possibilitando maior controle e planejamento dos estudos.

## 📝 Justificativa
A rotina acadêmica envolve um grande volume de informações, desde notas de avaliações até anotações de aula e prazos de entrega. Manter todos esses dados organizados em um único ambiente digital facilita a vida do estudante e evita perdas de informações importantes. Além disso, o projeto proporciona à equipe uma experiência prática no desenvolvimento de software, abordando conceitos como:
- Programação Orientada a Objetos.
- Persistência de dados utilizando Room Database.
- Design de interface seguindo padrões modernos.

---

## 🏛 Arquitetura do Projeto

### 🔹 Padrão Model-View-Controller (MVC)
O projeto utiliza a arquitetura **Model-View-Controller (MVC)**, garantindo uma separação clara entre lógica de negócios, apresentação e controle.

#### 📌 Estrutura do Padrão MVC:
- **Model:** Responsável pela lógica de negócios e manipulação de dados. Inclui classes como `Materia` e interfaces `DAO` que permitem o acesso ao banco de dados.
- **View:** Camada visual do aplicativo. Engloba as telas apresentadas ao usuário, seus componentes gráficos e interações.
- **Controller:** Atua como intermediador entre as Views e Models, processando eventos e garantindo a atualização correta da interface.

#### ✅ Benefícios do Padrão MVC:
- Separação de responsabilidades entre lógica, apresentação e controle.
- Facilidade de manutenção e expansão do código.
- Reutilização de componentes em diferentes partes da aplicação.
- Possibilita a implementação de testes unitários de maneira eficiente.
- Adota boas práticas de mercado, facilitando o trabalho em equipe e integração com outras soluções.

---

## ⚙️ Funcionalidades Implementadas
O aplicativo oferece diversas funcionalidades para auxiliar estudantes na organização acadêmica:
- 📌 Cadastro de novas disciplinas (matérias).
- 🔢 Registro e exibição de notas de avaliações.
- 📊 Cálculo automático de média ponderada.
- 🖊️ Anotações vinculadas às matérias.
- 🔍 Pesquisa dinâmica por nome da matéria.
- 🔄 Navegação entre telas utilizando `Intents`.

---

## 🛠 Tecnologias Utilizadas
O projeto foi desenvolvido utilizando tecnologias modernas para garantir eficiência e bom desempenho:
- **📌 Linguagem:** Kotlin
- **📌 Plataforma:** Android (SDK 24+)
- **📌 Banco de Dados:** Room Persistence Library
- **📌 Interface Gráfica:** Android View Binding
- **📌 IDE:** Android Studio
- **📌 Padrões de UI:** Material Design, Jetpack Components

---

## 🚀 Como Executar o Projeto
### Pré-requisitos:
- Android Studio instalado.
- Emulador ou dispositivo físico configurado.
- SDK 24+ instalado.

### Passos para rodar:
1. Clone o repositório:  
   ```bash
   git clone https://github.com/ellen-peres/Aplicativo_estudante.git
