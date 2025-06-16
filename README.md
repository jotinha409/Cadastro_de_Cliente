```# 📋 Sistema de Cadastro Digital – Space Wellness (v0.3)```

Sistema acadêmico desenvolvido para informatizar o processo de **cadastro de clientes** da clínica fictícia *SPA Space Wellness*, substituindo fichas de papel por uma aplicação Java integrada a banco de dados.

---

```## 👥 Integrantes```

- **João Pedro Ferreira Queiroz**  
  RA: 972515409  
  Curso: Análise e Desenvolvimento de Sistemas

- **David Lucas Gonçalves Santos**  
  RA: 972515322  
  Curso: Ciência da Computação

---

```## 🚀 Tecnologias Utilizadas```

- **Linguagem:** Java (versão 17+ recomendada)
- **GUI:** Swing (interface gráfica com `JFrame`)
- **Banco de Dados:** MySQL
- **IDE:** IntelliJ IDEA
- **Build Tool:** Maven
- **JDBC:** Para conexão com banco
- **JUnit:** Para testes unitários
- **Arquitetura:** Monolítica com camadas (`Entity`, `DAO`, `Service`, `GUI`, `Util`)

---

```## 📂 Estrutura de Arquivos```

```
src/
└── com/
└── seuprojeto/
├── Cliente.java # Entidade Cliente (modelo dos dados)
├── ClienteDAO.java # Operações de banco (CRUD)
├── ClienteService.java # Lógica de negócio
├── ConexaoBD.java # Classe de conexão com banco MySQL
├── Main.java # Classe principal com interface Swing
├── TesteConexao.java # Teste simples da conexão JDBC
└── ValidadorInput.java # Validação de entradas do usuário
```

---

```## ✅ Funcionalidades```

- [x] Cadastrar clientes
- [x] Editar dados do cliente (por **ID**)
- [x] Remover cliente (por **ID**)
- [x] Consultar cliente (por **ID**)
- [x] Interface gráfica via Swing
- [x] Validação de entradas
- [x] Teste de conexão com MySQL

---

```## ⚙️ Pré-requisitos```

- Java JDK 17+
- MySQL Server
- IntelliJ IDEA (ou outra IDE)
- Maven

---

```## 🧪 Como Executar```

1. Clone o projeto:
```bash
git clone https://github.com/jotinha409/Cadastro_de_Cliente.git

-- Cria o banco de dados, se não existir
CREATE DATABASE IF NOT EXISTS sistema_clientes;
-- Seleciona o banco de dados para uso
USE sistema_clientes;

-- Cria a tabela de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(150) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

