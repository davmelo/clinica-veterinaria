# Sistema de Controle de Animais em Clínica Veterinária

## Equipe

* **David Fernando de Melo** - david.fmelo@ufrpe.br
* **Edilson José da Silva Junior** - edilsonjunior101@gmail.com
* **Giovanna Costa da Silva** - giovanna.costa@ufrpe.br
* **Lucas Pontes França Diniz Lima** - lucaspontesdiniz@gmail.com
* **Miguel Monteiro Alves Paes** - miguel.alves@ufrpe.br

## Descrição

Este sistema tem como objetivo gerenciar o cadastro, atendimento e histórico de animais em uma clínica veterinária. Ele permite o registro detalhado dos animais, seus donos, agendamentos de consultas, tratamentos, vacinação e exames.

O sistema possibilita o acompanhamento do estado de saúde dos animais, controle dos procedimentos realizados, agendamento e registro de consultas, bem como a emissão de relatórios para análise do histórico clínico e produtividade da clínica.

Além disso, oferece funcionalidades para gerenciar os profissionais veterinários e facilitar a comunicação com os clientes (donos dos animais), otimizando o fluxo de atendimento.

## Requisitos Funcionais

### 1. Gerenciamento de Animais

- **REQ01**: Permitir o cadastro de animais, incluindo nome, espécie, raça, data de nascimento, peso, e identificação (microchip ou outro).
- **REQ02**: Associar cada animal a um proprietário cadastrado no sistema.

### 2. Gerenciamento de Clientes (Donos)

- **REQ03**: Permitir o cadastro de clientes com nome, telefone, endereço e e-mail.
- **REQ04**: Manter histórico dos animais pertencentes a cada cliente.

### 3. Gerenciamento de Profissionais Veterinários

- **REQ05**: Permitir o cadastro de veterinários com nome, especialidades, telefone, e CRMV (registro profissional).
- **REQ06**: Controlar a agenda de cada veterinário para consultas e procedimentos.

### 4. Agendamento de Consultas e Procedimentos

- **REQ07**: Permitir o agendamento de consultas e procedimentos para animais, escolhendo veterinário, data e horário.
- **REQ08**: Impedir agendamento de horários conflitantes para o mesmo veterinário.
- **REQ09**: Permitir reagendamento e cancelamento com registro de motivo.

### 5. Registro de Atendimento e Procedimentos

- **REQ10**: Registrar informações do atendimento realizado, incluindo diagnóstico, tratamentos, medicamentos prescritos e observações.
- **REQ11**: Permitir o registro de procedimentos como vacinação, exames laboratoriais e cirurgias, com datas e resultados.

### 6. Controle de Vacinação e Exames

- **REQ12**: Manter o histórico de vacinas aplicadas para cada animal, incluindo datas e validade.
- **REQ13**: Alertar para vacinas e exames que estejam prestes a vencer ou necessitem ser renovados.

### 7. Consultas, Relatórios e Estatísticas

- **REQ14**: Permitir consultas por animal, cliente, veterinário, ou período, exibindo histórico completo.
- **REQ15**: Gerar relatórios de atendimentos realizados, frequência por veterinário, e tipos de procedimentos mais comuns.
- **REQ16**: Permitir exportação dos relatórios em **PDF** e **CSV**, com organização e filtros.

### 8. Regras e Restrições

- **REQ17**: Não permitir agendamento para datas e horários já ocupados por outro atendimento do mesmo veterinário.
- **REQ18**: Impedir que animais sejam cadastrados sem vínculo a um cliente.
- **REQ19**: Permitir somente profissionais com CRMV válido para realizar agendamentos e registrar atendimentos.
- **REQ20**: Garantir que os dados pessoais dos clientes estejam protegidos e acessíveis apenas a usuários autorizados.

## Possíveis APIs/Bibliotecas a Serem Usadas

- **JavaFX** – Interface gráfica para cadastro, agenda e visualização.
- **JDBC / Hibernate** – Persistência de dados e consultas.
- **Java Time API** – Manipulação de datas para agendamento e controle de validade de vacinas.
- **iText / JasperReports** – Geração de relatórios em PDF.
- **Apache POI** – Exportação para CSV/Excel.
- **JUnit / Mockito** – Testes de lógica para agendamentos e validações.
