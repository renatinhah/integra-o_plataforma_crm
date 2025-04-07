# ☁️ Integração com HubSpot via API REST

## ✅ Visão Geral

Este projeto implementa uma API REST em **Java com Spring Boot**, cujo objetivo é integrar com o **HubSpot** utilizando o fluxo **OAuth 2.0 (authorization code flow)**, permitindo a criação de contatos via API e o recebimento de notificações por **webhooks**.

As decisões abaixo foram guiadas por **clareza**, **produtividade** e foco no que de fato seria relevante avaliar dentro do tempo disponível.

---

## ⚙️ Funcionalidades

- 🔗 **Geração de URL de Autorização**  
  Cria a URL para iniciar o fluxo de autenticação com o HubSpot.

- 🔐 **Processamento de Callback OAuth**  
  Recebe o código de autorização e o troca por tokens de acesso.

- 🧑‍💼 **Criação de Contatos**  
  Adiciona novos contatos ao CRM do HubSpot.

- 📩 **Recebimento de Webhooks**  
  Escuta e processa eventos de criação de contatos enviados pelo HubSpot.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17** — Linguagem principal do projeto
- **Spring Boot** — Framework para simplificar o desenvolvimento
- **WebClient** — Cliente reativo para chamadas HTTP
- **Lombok** — Reduz a verbosidade do código
- **Swagger** — Documentação interativa da API

---

## 📋 Pré-requisitos

- Java 17 instalado
- Maven configurado
- Conta de desenvolvedor no HubSpot

---

## 🔧 Passos de Configuração Inicial no HubSpot

### 1. Criar uma Conta de Desenvolvedor
Acesse: [https://developers.hubspot.com](https://developers.hubspot.com) e crie sua conta de desenvolvedor.

### 2. Criar uma Conta de Teste
Dentro do painel da conta de desenvolvedor, crie uma "Test Account" clicando em **"Create test account"**.

### 3. Criar um App no HubSpot
Ainda na conta de desenvolvedor:
- Vá para **Apps > Create App**
- Defina o nome do app
- Configure os **scopes necessários** (`oauth`, `crm.objects.contacts.write`)
- Defina o **Redirect URI** como `http://localhost:8080/hubspot/callback` (ou o que preferir)

### 4. Instalar o App na Conta de Teste
- Gere a URL de instalação com os `client_id`, `redirect_uri` e `scope`
- Acesse essa URL, escolha a conta de teste, e autorize a aplicação

Após isso, você já pode começar a interagir com a API localmente.

---

## 🔧 Configuração do Projeto

### 1. Clonar o Repositório

```bash
git clone https://github.com/...
cd nome-do-projeto
```

### 2. Configurar Variáveis de Ambiente

Defina as seguintes variáveis com suas credenciais do HubSpot:

```bash
HUBSPOT_CLIENT_ID=...
HUBSPOT_CLIENT_SECRET=...
HUBSPOT_REDIRECT_URI=...
```

> Você também pode configurar via `application.properties`.

---

## ▶️ Rodando e Acessando a Documentação

### Compilar e Executar

```bash
mvn spring-boot:run
```

### Acessar a Documentação

Após iniciar a aplicação, acesse:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧪 Testando a API

### 1. Gerar URL de Autorização

```bash
curl --location 'http://localhost:8080/hubspot/auth-url'
```

### 2. Trocar Código por Token

```bash
curl --location 'http://localhost:8080/hubspot/callback?code=SEU_CODIGO'
```

### 3. Criar Contato

```bash
curl --location 'http://localhost:8080/hubspot/contact/create' \
--header 'Authorization: Bearer SEU_TOKEN' \
--header 'Content-Type: application/json' \
--data-raw '{
  "email": "exemplo@hubspot.com",
  "firstname": "Nome",
  "lastname": "Sobrenome",
  "phone": "(11) 99999-9999",
  "company": "Empresa",
  "website": "empresa.com",
  "lifecyclestage": "lead"
}'
```

---

## 💡 Observações

Este projeto foi desenvolvido com foco na **simplicidade** e **eficácia**, evitando complexidades desnecessárias.  
Decisões como manter a API aberta para facilitar testes e exigir e-mails nos contatos foram tomadas visando a funcionalidade e a utilidade dos dados.

Futuras melhorias podem incluir:

- Implementação de logs mais robustos
- Uso de Docker para ambientes de desenvolvimento consistentes

---

## 🚀 Melhorias Futuras

Caso o projeto evolua para uma versão mais robusta, estas seriam as próximas melhorias:

- ✅ Habilitar autenticação e autorização reais com Spring Security
- ✅ Implementar persistência dos eventos de webhook usando PostgreSQL ou MongoDB
- ✅ Utilizar logs estruturados e integrá-los a uma plataforma externa para monitoramento
- ✅ Implementar revalidação automática de token OAuth com refresh token
- ✅ Usar Docker Compose para facilitar o setup completo (ngrok, app, banco)
- ✅ Implementar os testes unitários e de integração
