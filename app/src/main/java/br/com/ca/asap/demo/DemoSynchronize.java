package br.com.ca.asap.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.ca.asap.database.DatabaseOpenHelper;
import br.com.ca.asap.database.AdminDAO;
import br.com.ca.asap.database.DeliverableDAO;
import br.com.ca.asap.vo.AdminVo;
import br.com.ca.asap.vo.DeliverableVo;
import br.com.ca.asap.database.InitiativeDAO;
import br.com.ca.asap.vo.InitiativeVo;

/**
 * DemoSynchronize
 *
 * TODO: remove demo version
 *
 * @author Rodrigo Carvalho
 */
public class DemoSynchronize {

    private List<InitiativeVo> initiativeVoList = null;
    private List<DeliverableVo> deliverableVoList = null;
    private List<AdminVo> adminVoList = null;

    //Empty constructor
    public DemoSynchronize(){

    }

    //Rewrite SQLite database with current initiatives
    public void demoSynchronize(Context context){

        SQLiteDatabase db;

        //instantiate DatabaseOpenHelper
        DatabaseOpenHelper databaseOpenHelper = DatabaseOpenHelper.getInstance(context);

        //delete all from initiative table before repopulating
        db = databaseOpenHelper.getWritableDatabase();
        databaseOpenHelper.deleteTables();

        //Synchronize from server database (in demo version create with local values)

        //create Initiative DAO
        InitiativeDAO initiativeDAO = new InitiativeDAO(context);
        //create Work Item DAO
        DeliverableDAO deliverableDAO = new DeliverableDAO(context);
        AdminDAO adminDAO = new AdminDAO(context);

        //create array lists
        initiativeVoList = new ArrayList<>();
        deliverableVoList = new ArrayList<>();
        adminVoList = new ArrayList<>();

        //DEMO LOCAL CREATION
        //local creation of list of InitiativeVo. In the non demo version, list must be create from CA server http response
        //hard coded for demo version. In non demo version CA server must return array of initiatives for synchronization


        //
        //CREATE INITIATIVES LIST
        //
        initiativeVoList.add(new InitiativeVo("COMITE TECNICO","COMITE TECNICO","Problemas técnicos com impacto em receita."));
        /*
        initiativeVoList.add(new InitiativeVo("COMITÊ EXECUTIVO","COMITÊ EXECUTIVO","Executivo"));
        initiativeVoList.add(new InitiativeVo("COMITÊ COMERCIAL","COMITÊ COMERCIAL","Condições comerciais"));
        initiativeVoList.add(new InitiativeVo("COMITÊ DE APARELHOS","COMITÊ DE APARELHOS","Abastecimento"));
        initiativeVoList.add(new InitiativeVo("COMITÊ DE APROVAÇÃO DE INVESTIMENTOS","COMITÊ DE APROVAÇÃO DE INVESTIMENTOS","Investimento Projetos em DTI"));
        initiativeVoList.add(new InitiativeVo("COMITÊ DE APROVAÇÃO DE INVESTIMENTOS NÃO TI","COMITÊ DE APROVAÇÃO DE INVESTIMENTOS NÃO TI","Capex"));
        initiativeVoList.add(new InitiativeVo("COMITÊ DE CONTIGENCIAS TRABALHISTAS","COMITÊ DE CONTIGENCIAS TRABALHISTAS","Contingências"));
        initiativeVoList.add(new InitiativeVo("COMITÊ DE RECEITA","COMITÊ DE RECEITA","Orçamento de Receita"));
        initiativeVoList.add(new InitiativeVo("COMITÊ DE REGIONAIS","COMITÊ DE REGIONAIS","Vendas"));
        initiativeVoList.add(new InitiativeVo("COMITÊ DE RENTABILIDADE","COMITÊ DE RENTABILIDADE","Custos"));
        initiativeVoList.add(new InitiativeVo("COMITÊ CLIENTES","COMITÊ CLIENTES","Experiência do Cliente"));
        */
        //insert list of initiatives
        //access initiative list via Iterator
        Iterator iterator = initiativeVoList.iterator();
        while(iterator.hasNext()){
            InitiativeVo initiativeVo = (InitiativeVo) iterator.next();
            //...insert into initiative table
            initiativeDAO.insertInitiative(initiativeVo);
        }

        //DEMO LOCAL CREATION
        //local creation of list of DeliverableVo. In the non demo version, list must be create from CA server http response
        //hard coded for demo version. In non demo version CA server must return array of work items for synchronization


        //
        //CREATE DELIVERABLES LIST
        //
        /*
        deliverableVoList.add(new DeliverableVo("1657", "COMITÊ EXECUTIVO", "Resolucao 226 e 604 - Anatel", "Revisao e alteracao das regras para aplicacao de estornos - resolucao 226 e 604", "Novo prazo proposto: 06-05-2016. A OS 103188 de TI-Mediação está com data prevista para implantação até 29-04-2016. - O novo prazo solicitado é para possibilitar a validação da demanda pela equipe GAR-Pré Pago, conforme e-mail anexo.", "Open", "06-05-2016", "FLORIANO PAULINO DA COSTA NETO", "2"));

        deliverableVoList.add(new DeliverableVo("1832", "COMITÊ DE RENTABILIDADE", "RECUPERAR % DE RECEBIMENTO DE VALORES CONFORME META DEFINIDA POR AMX - PF", "RECUPERAR % DE RECEBIMENTO DE VALORES CONFORME META DEFINIDA POR AMX - PF (DEFINIÇÃO DE PLANO DE AÇÃO - AÇÕES", " RESPONSÁVEIS", "Open", "25-02-2016", "RENILDO JOSE DA SILVA JUNIOR", "2"));

        deliverableVoList.add(new DeliverableVo("1833", "COMITÊ DE RENTABILIDADE", "RECUPERAR % DE RECEBIMENTO DE VALORES CONFORME META DEFINIDA POR AMX - PME", "RECUPERAR % DE RECEBIMENTO DE VALORES CONFORME META DEFINIDA POR AMX - PME (DEFINIÇÃO DE PLANO DE AÇÃO - AÇÕES", " RESPONSÁVEIS E PRAZOS) - FONTE: REUNIÃO COM EXECUTIVOS DE FIN E SCO DE 05-09-13.", "Open", "25-02-2016", "RENILDO JOSE DA SILVA JUNIOR", "2"));

        deliverableVoList.add(new DeliverableVo("1890", "COMITÊ COMERCIAL", "VENDA DE PRODUTOS CLOUD NOS CANAIS COPORATIVOS", "IMPLEMENTAR PRODUTOS E COMEÇAR VENDER CLOUD NOS CANAIS PME DA CLARO", "", "Open", "25-03-2016", "RODRIGO AUGUSTO VIDIGAL DE LIMA", "2"));

        deliverableVoList.add(new DeliverableVo("1904", "COMITÊ EXECUTIVO", "IMPLEMENTAÇÃO DE MODELO OPERAÇÃO  + CROSS DOCKING +  PARA ABASTECIMENTO AO CANAL VAREJO", "IMPLEMENTAÇÃO DE MODELO OPERAÇÃO  + CROSS DOCKING +  PARA ABASTECIMENTO AO CANAL VAREJO", "Campo Data Acompanhamento alterado de 02-09-2015 para 08-10-2015", "Open", "29-01-2016", "MARTIN ENRIQUE BUTELER", "2"));

        deliverableVoList.add(new DeliverableVo("1936", "COMITÊ CLIENTES", "IMPLEMENTAÇÃO DE NOVO MODELO CONTRATUAL EPS", "IMPLEMENTAR NOVO MODELO CONTRATUAL EM TODAS AS EPS COM O OBJETIVO DE REDUZIR MIGRAÇÕESINDEVIDAS PRÉ PARA CONTROLE. (Origem: tarefa 1640)", "Proposta de alteração de prazo aceita . Prazo Aceito", "Open", "18-02-2016", "RICARDO CESAR DE OLIVEIRA", "2"));

        deliverableVoList.add(new DeliverableVo("2095", "COMITÊ COMERCIAL", "BANDA LARGA PRÉ-PAGO: REDISENHAR O PRODUTO INCLUINDO MODEM E ROTEADOR. DESDE MKT RETOMAR AS MIGRAÇÕE", "BANDA LARGA PRÉ-PAGO: REDISENHAR O PRODUTO INCLUINDO MODEM E ROTEADOR. DESDE MKT RETOMAR AS MIGRAÇÕES DE PRÉ-PAGO PARA PÓS-PAGO.", "Campo Data Acordada alterado de 30-09-2015 para 29-01-2016Campo Data Acompanhamento alterado de 17-11-2014 para 29-01-2016", "Open", "29-01-2016", "ALEXANDRE OLIVARI", "2"));

        deliverableVoList.add(new DeliverableVo("2207", "COMITÊ EXECUTIVO", "RETIRAR ACESSO MOBILE PARA OPERAÇÃO DE ATENDIMENTO E CALL-CENTER", "RETIRAR ACESSO MOBILE PARA OPERAÇÃO DE ATENDIMENTO E CALL-CENTER", "", "Open", "31-01-2016", "SERGIO ROBERTO RICUPERO", "2"));

        deliverableVoList.add(new DeliverableVo("2338", "COMITÊ CLIENTES", "PADRONIZAR AS INFORMAÇÕES NAS FERRAMENTAS DE CONSULTA AOS AGREGADORES", "PADRONIZAR AS INFORMAÇÕES (ATIVAÇÃO", " USO E CANCELAMENTO) NAS FERRAMENTAS DE CONSULTA DOS AGREGADORES. HOJE TEMOS APRESENTAÇÃO DISTINTAS NO FORMATO APRESENTADO POR CADA UM DOS PARCEIROS O QUE DIFICULTA O ATENDIMENTO.", "Open", "29-02-2016", "MARCO DYODI TAKAHASHI", "2"));

        deliverableVoList.add(new DeliverableVo("2348", "COMITÊ COMERCIAL", "VISITA DH OUT14 - MODELO DE NEGÓCIOS - PRODUTOS MASSIVO", "MODELO DE NEGOCIOS: REAVALIAR O MODELO DE NEGOCIO DOS PRODUTOS MASSIVOS PARA GANHAR RENTABILIDADE: -  - a) Troca de aparelhos nos AA com delivery para reduzir margem. - b) Receber aportes para focalizar certos modelos na retenção (Projeto Pesqueiro) - c) Implantar as taxas de adesão em TV (até agora é uma proposta)", "Proposta de alteração de prazo aceita .", "Open", "19-02-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("2387", "COMITÊ DE REGIONAIS", "MKT SHARE POS PAGO 2015", "ALCANÇAR MKT SHARE POS TOTAL DE 18% EM DEZEMBRO DE 2015. MKT SHARE SET´14 = 14", "79%", "Open", "31-03-2016", "ERIK CORDEIRO CALDAS FERNANDES", "2"));

        deliverableVoList.add(new DeliverableVo("2388", "COMITÊ DE REGIONAIS", "MKT SHARE POS TOTAL 2015", "ALCANÇAR MKT SHARE POS TOTAL DE 19% EM DEZEMBRO DE 2015. MKT SHARE SET´14 = 15", "27%", "Open", "21-03-2016", "MARCO AURELIO NEVES ALVES", "2"));

        deliverableVoList.add(new DeliverableVo("2389", "COMITÊ DE REGIONAIS", "MKT SHARE POS TOTAL 2015", "ALCANÇAR MKT SHARE POS TOTAL DE 15% EM DEZEMBRO DE 2015. MKT SHARE SET´14 = 9", "78%", "Open", "31-03-2016", "DYLCIO JOSE LEAL PORTO", "2"));

        deliverableVoList.add(new DeliverableVo("2390", "COMITÊ DE REGIONAIS", "MKT SHARE POS TOTAL 2015", "ALCANÇAR MKT SHARE POS TOTAL DE 25% EM DEZEMBRO DE 2015. MKT SHARE SET´14 = 21", "09%", "Open", "31-03-2016", "RICARDO CESAR DE OLIVEIRA", "2"));

        deliverableVoList.add(new DeliverableVo("2395", "COMITÊ DE REGIONAIS", "MKT SHARE PRÉ CLARO -", "COMBATE A OI NOI PRÉ - FAZER O  + X +  EM JULHO-17", "Proposta de alteração de prazo aceita .", "Open", "31-08-2017", "CARLOS ALEXANDRE CIPRIANO", "2"));

        deliverableVoList.add(new DeliverableVo("2397", "COMITÊ DE REGIONAIS", "Desafio de GROSS PME", "PME RSC DEVERÁ ENTREGAR 20K até junho 2016 - 14 K voz - 03 k BL - 03 K M2M - Total 20K -  - (report será feito mês a mês)", "Campo Data Acordada alterado de 08-01-2016 para 11-07-2016Novo prazo de acordo com solicitação DOR.", "Open", "11-07-2016", "ALEXANDRE DOS SANTOS BERBEL", "2"));

        deliverableVoList.add(new DeliverableVo("2398", "COMITÊ DE REGIONAIS", "GROSS BL PME", "FAZER PLANO PARA ENTREGAR O DESAFIO DE  1,5K BL POR MES ATÉ JULHO DE 2016 - ", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "29-02-2016", "ALEXANDRE DOS SANTOS BERBEL", "2"));

        deliverableVoList.add(new DeliverableVo("2403", "COMITÊ DE REGIONAIS", "CAPILARIDADE AA", "IMPLEMENTAR PDVs nas cidades:  SANTA ISABEL", " EMBU GUAÇU", "Open", "29-02-2016", "RICARDO CESAR DE OLIVEIRA", "2"));

        deliverableVoList.add(new DeliverableVo("2406", "COMITÊ DE REGIONAIS", "GROSS CONTA LP", "DESAFIO DE ENTREGAR 13K DE CONTA ATÉ 31-12-2015", "Solicito finalização por conclusão da meta.", "Open", "26-01-2016", "CARLOS ALEXANDRE CIPRIANO", "2"));

        deliverableVoList.add(new DeliverableVo("2413", "COMITÊ DE REGIONAIS", "ALAVANCAR AS VENDAS DE GROSS BRUTO PRÉ NO DC", "DESAFIO DE 130K DE GROSS PRÉ NO DC A PARTIR DE jun-16", "Proposta de alteração de prazo aceita .", "Open", "05-07-2016", "MARCELO MOREIRA PEREIRA LEITE", "2"));

        deliverableVoList.add(new DeliverableVo("2523", "COMITÊ CLIENTES", "DEFINIR E APROVAR MODELO DE QUALIDADE PARA TELEVENDAS", "DEFINIR E APROVAR UM PROGRAMA DE QUALIDADE-MONITORIA PARA TELEVENDAS", "Conforme orientação Abussanrra.", "Open", "01-03-2016", "FABIANA CRISTINA FALCETI SCHNEEBERGER", "2"));

        deliverableVoList.add(new DeliverableVo("2619", "COMITÊ DE APARELHOS", "POLITICA DE COMPRA DE APARELHO COM DEMONSTRAÇÃO.", "NEGOCIAR COM OS FABRICANTES PARA COMEÇAR APLICAR A POLITICA DE COMPRA DE APARELHO COM DEMONSTRAÇÃO NO 2Q. (Origem: tarefa 1923)", "Anderson", "Open", "29-01-2016", "ANDERSON APARECIDO CARNEIRO DA SILVA", "2"));

        deliverableVoList.add(new DeliverableVo("2674", "COMITÊ DE RECEITA", "FICHA 92: COBRANÇA DE VALORES EM DISPUTA", "COBRANÇA DE VALORES EM DISPUTA COM PRAZO SUPERIOR AO LIMITE REGULATÓRIO DE COBRANÇA SEM ANUÊNCIA.  -  - AVALIAR O MAILING DE CLIENTES EM DISPUTA COM BAIXA PROPENSÃO A ATRITO PARA CONTATO DE ANUÊNCIA DE COBRANÇA. QUANTIFICAR E ALINHAR AÇÃO COM REGULATÓRIO.", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "31-03-2016", "JOSE APARECIDO PRADO", "2"));

        deliverableVoList.add(new DeliverableVo("2705", "COMITÊ EXECUTIVO", "REVISÃO PROCESSO FATURAMENTO DE MULTA", "REVISÃO DO PROCESSO DE FATURAMENTO DE MULTA: - A) INCLUIR COMO ARGUMENTAÇÃO DE VENDA, MAS NÃO FATURAR DE IMEDIATO). - B) REVISAR TAMBÉM A DEVOLUÇÃO DO APARELHO PARA CASOS DE NÃO PAGAMENTO DE MULTA - BLOQUEIO DE IMEI.", "Proposta de alteração de prazo aceita .", "Open", "29-02-2016", "MARCELO MARTIGNAGO BAILAO", "2"));

        deliverableVoList.add(new DeliverableVo("2706", "COMITÊ DE RECEITA", "FICHA 56: VENDAS CANCELADAS EM CANAIS NÃO PRESENCIAIS", "Notificar aos parceiros as penalidade definidas e aplicação das sanções para o backlog de parceiros que não retornaram gravações solicitadas.", "Fabiana, -  - Já temos definição do procedimento de aplicação de multa para o parceiro? -  - Att, - Rodrigo Carvalho", "Open", "29-01-2016", "FABIANA CRISTINA FALCETI SCHNEEBERGER", "2"));

        deliverableVoList.add(new DeliverableVo("2733", "COMITÊ EXECUTIVO", "ACESSO SISTEMAS CHAVE", "O PROJETO CONSISTE EM: -  - CONTRATAR E IMPLEMENTAR A SOLUÇÃO DE MONITORAMENTO NO GRUPO AMX BRASIL.  -  - FASE 1 - CONTRATAÇÃO DA SOLUÇÃO DO SIEM - DEZEMBRO 2015, - RECEBIMENTO DE LICENÇAS - JANEIRO 2016, - LEVANTAMENTO E ADEQUAÇÃO DO AMBIENTE - FEV. 2016, -  - FASE 2  - INSTALAÇÃO - INICIO DA OPERAÇÃO - MARÇO 2016, - OPERAÇÃO ASSISTIDA - ABRIL 2016, - OPERAÇÃO AUTÔNOMA - MAIO 2016, -  - ", "Proposta de alteração de prazo aceita .", "Open", "31-05-2016", "SANDRO APARECIDO DE SANTANA", "2"));

        deliverableVoList.add(new DeliverableVo("2957", "COMITÊ DE RENTABILIDADE", "CENTRALIZAÇÃO DE TELEVENDAS (ILHA DE AGENDAMENTO)", "CRIAR ILHA DE AGENDAMENTO NO CD", " GERANDO ECONOMIA DE R$ 290K POR MÊS (R$872K EM 2015) NA LINHA DE TRANSPORTE E ARMAZENAGEM.", "Open", "26-02-2016", "ALEXANDRE MAGNO DOS SANTOS SILVA", "2"));

        deliverableVoList.add(new DeliverableVo("2963", "COMITÊ DE CONTIGENCIAS TRABALHISTAS", "HORAS EXTRAS - AVALIAR SE OS CARGOS EXCLUÍDOS DO PONTO ESTÃO BEM ENQUADRADOS NAS EXCEÇÕES DO ART. 62", "HORAS EXTRAS - AVALIAR SE OS CARGOS EXCLUÍDOS DO PONTO ESTÃO BEM ENQUADRADOS NAS EXCEÇÕES DO ART. 62 DA CLT", "Paulo", "Open", "29-01-2016", "PAULO PIMENTEL DE VIVEIROS", "2"));

        deliverableVoList.add(new DeliverableVo("2997", "COMITÊ DE REGIONAIS", "PN2015 - PLANO PARA VENDER CONTA NO AA", "PLANO DE AÇÃO PARA O AA VENDER CONTA. DESAFIO 200K LINHAS MÊS EM DEEMBRO-15", "segue anexo. Meta já superada e plano de ação para fazermos muito mais que o solicitado", "Open", "29-01-2016", "RICARDO CESAR DE OLIVEIRA", "2"));

        deliverableVoList.add(new DeliverableVo("3000", "COMITÊ DE REGIONAIS", "PN2015 - PLANO 10", "CANAL DISTRIBUIDOR: PLANO 10 ? DOMINAR OS 10 MELHORES PDRS", " NAS 10 MELHORES CIDADES DE CADA FILIAL.", "Open", "31-03-2016", "DYLCIO JOSE LEAL PORTO", "2"));

        deliverableVoList.add(new DeliverableVo("3013", "COMITÊ DE REGIONAIS", "PN2015 - REDUÇÃO DA INADIMPLÊNCIA DE 1ª FATURA DO DIC", "CANAL DIC: REDUÇÃO DA INADIMPLÊNCIA DE 1ª FATURA EM 30%", "Proposta de alteração de prazo aceita . Novo prazo alinhado com diretoria GAR.", "Open", "29-02-2016", "RENATA FANTONI TATINI", "2"));

        deliverableVoList.add(new DeliverableVo("3029", "COMITÊ DE REGIONAIS", "PN2015 - NOVOS VAREJOS REGIONAIS", "CANAL GV: CADASTRAR 5 NOVOS VAREJOS REGIONAL EM 2015 E ATUAR COM AUMENTO DE PRODUTIVIDADE PARA GIRO LAZER", " DU JUCA", "Open", "30-06-2016", "RODRIGO MARTINS ABUSSAMRA", "2"));

        deliverableVoList.add(new DeliverableVo("3036", "COMITÊ DE RECEITA", "FICHA 224: CONTESTAÇÃO LD OUTRAS OPERADORES (DIFERENTE DE 21) - CLIENTES PME", "REALIZAR REVERSÃO-CONTESTAÇÃO (DESCONTO NO REPASSE PARA OUTRAS OPERADORAS DE LONGA DISTÂNCIA) DOS VALORES DE CONSUMO LD DIFERENTE DE 21 PARA O SEGUINTE CENÁRIO: -  - CLIENTES PME CANCELADOS COM FATURAS ARRECADADAS NO PERÍODO DE JAN-14 A MAI-15 PARA OS EVENTOS DE LONGA DISTÂNCIA DIFERENTE DE 21 CONSUMIDOS DENTRO DA FRANQUIA.", "Proposta de alteração de prazo aceita . novo prazo", "Open", "19-02-2016", "MILENE VOGEL GLOGUER", "2"));

        deliverableVoList.add(new DeliverableVo("3046", "COMITÊ DE RECEITA", "FICHA 064: NOVITECH - CHARGE BACK - VOLUME ELEVADO DE CONTESTAÇÃO", "REDUZIR O CHARGE BACK NO CONTROLE FÁCIL", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "04-04-2016", "JOSE APARECIDO PRADO", "2"));

        deliverableVoList.add(new DeliverableVo("3059", "COMITÊ CLIENTES", "CARTILHA INTERNET", "DESENVOLVER CARTILHA PARA OPERAÇÃO EXPLICANDO INTERNET, QUANTIDADE DE DADOS CONSUMIDOS ETC. - DIVULGAR NA INTRANET.", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "18-02-2016", "JULIANA GODOY DE OLIVEIRA LIMA MACHADO", "2"));

        deliverableVoList.add(new DeliverableVo("3060", "COMITÊ CLIENTES", "CONTROLE DE MENSAGEM PUBLICITÁRIA (fluxo optout)", "Disponibilizar em uma tabela única no DW da Claro", " todas as marcações de optin-optout provenientes do WebSite Minha Claro", "Open", "04-03-2016", "EUNICE MARCAL DA SILVEIRA", "2"));

        deliverableVoList.add(new DeliverableVo("3062", "COMITÊ CLIENTES", "BLOQUEAR VENDA PARA LOCALIDADES SEM COBERTURA", "FUNCIONALIDADE DEVE PERMITIR QUE SISTEMAS DE VENDA WA, WM (MIGRAÇÃO COM DESTINO BANDA LARGA) E ORDERING (CPC) TENHAM UMA TRAVA PARA BLOQUEIO DE NOVA ATIVAÇÃO OU VENDA DE PLANO, PROMOÇÃO POR CEP, DDD, ESTADO, MUNICÍPIO EXCLUSIVO PARA BANDA LARGA, NOS CANAIS PRESENCIAIS E NÃO PRESENCIAIS. -  -  CASO HAJA UMA TENTATIVA DE VENDA EM UM CEP, DDD, ESTADO, MUNICÍPIO NÃO LIBERADOS, SISTEMA DEVE BLOQUEAR E MOSTRAR A INFORMAÇÃO DE QUE NÃO É POSSÍVEL PROSSEGUIR COM A VENDA.", "Thiago, -  - Conforme mudança de responsabilidade sobre o tema segue direcionamento desta atividade. Por favor alinhar o status da ação e sinalizar no IW caso tenha revisão do planejamento. -  - Rodrigo Carvalho", "Open", "31-03-2016", "THIAGO DE MARCO MARQUES", "2"));

        deliverableVoList.add(new DeliverableVo("3065", "COMITÊ CLIENTES", "BLOQUEIO HUB", "POSSIBILITAR O BLOQUEIO DE AGREGADORES QUE NÃO ESTÃO NA FERRAMENTA MSE PARA EVITAR NOVA COBRANÇA DO SERVIÇO RECLAMADO.", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "29-02-2016", "EUNICE MARCAL DA SILVEIRA", "2"));

        deliverableVoList.add(new DeliverableVo("3073", "COMITÊ CLIENTES", "CONTROLE DOS AJUSTES CONCEDIDOS VIA WPP PARA VIABILIZAR REPASSE DE AJUSTES PARA AGREGADORES", "MELHORIA NO CAMPO DE AJUSTES VIA WPP, COM REMOÇÃO DO CONCEDER CRÉDITO E CONSOLIDAR OS AJUSTES VIA SELEÇÃO DO MOTIVO RECLAMADO. -  - BC101181", "", "Open", "05-04-2016", "CRISTIANE DE SOUZA LIMA", "2"));

        deliverableVoList.add(new DeliverableVo("3090", "COMITÊ CLIENTES", "GERENCIAMENTO DE FRAUDES DO AACE", "REVISAR PROCESSO DE DETECÇÃO E PENALIDADES DE FRAUDES ASSOCIADA A AÇÃO DE AACE", "", "Open", "15-03-2016", "CRISTIANE DE SOUZA LIMA", "2"));

        deliverableVoList.add(new DeliverableVo("3095", "COMITÊ DE RECEITA", "FICHA 230: CLIENTES PF  SEM PLANO DE PREÇO - SISTEMAS", "CLIENTES PF  SEM PLANO DE PREÇO - SISTEMAS. -  - AVALIAR OS CASOS REPORTADOS NO PERÍODO DE 12-08-2015 A 14-08-2015 E ELABORAR PLANO DE CORREÇÃO DA CAUSA RAIZ. -  - FICHA DO CASO EM ANEXO.", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "29-02-2016", "MILENE VOGEL GLOGUER", "2"));

        deliverableVoList.add(new DeliverableVo("3097", "COMITÊ DE RECEITA", "FICHA 232: ASSINANTES PME SEM COMPROMISSO", "ASSINANTES PME SEM COMPROMISSO -  - ELABORAR PLANO DE CORREÇÃO DO BACKLOG E CAUSA RAIZ -  - FICHA DO CASO EM ANEXO.", "Pauta do comitê técnico deve ser alinhada previamente com Antonio Amorim.", "Open", "19-02-2016", "ALEXANDRE COUTO MORAES", "2"));

        deliverableVoList.add(new DeliverableVo("3163", "COMITÊ DE RECEITA", "FICHA 149: FALHA NO PROCESSO DE ZERO FRAUDE", "O PROCESSO ?ZERO FRAUDE? ESTÁ ZERANDO VALORES ELEGÍVEIS A FATURAMENTO. -  - CORRIGIR O PROCESSO DE RETIRADA DE EVENTOS NO PRÉ-BILLING (FLAG ZERO FRAUDE), GARANTINDO QUE SOMENTE AS CHAMADAS FRAUDULENTAS E DENTRO DO PERÍODO SEJAM RETIRADAS. - IDENTIFICAR E CORRIGIR CAUSA RAIZ. - AVALIAR SOLUÇÕES PALIATIVAS.", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "31-03-2016", "JOSE APARECIDO PRADO", "2"));

        deliverableVoList.add(new DeliverableVo("3281", "COMITÊ DE CONTIGENCIAS TRABALHISTAS", "PERICULOSIDADE - APRESENTAR PLANO DE ADEQUAÇÃO DOS PRÉDIOS DA UNIDADE PESSOAL", "PERICULOSIDADE - APRESENTAR PLANO DE ADEQUAÇÃO DOS PRÉDIOS DA UNIDADE PESSOAL: ADMINISTRATIVOS", " CCCS E SWITCHS QUE NÃO ATENDAM ÀS RECOMENDAÇÕES DA NR-20 - LÍQUIDOS COMBUSTÍVEIS E INFLAMÁVEIS", "Open", "15-03-2016", "PAULO PIMENTEL DE VIVEIROS", "2"));

        deliverableVoList.add(new DeliverableVo("3283", "COMITÊ DE CONTIGENCIAS TRABALHISTAS", "PERICULOSIDADE - MANUTENÇÃO DOS EQUIPAMENTOS POR EMPRESAS TERCEIRAS ESPECIALIZADAS", "PERICULOSIDADE - REALIZAR AÇÃO PARA GARANTIR QUE A MANUTENÇÃO DOS EQUIPAMENTOS TAIS COMO RELÓGIOS DE MEDIÇÃO", " RETIFICADORES", "Open", "31-01-2016", "PAULO PIMENTEL DE VIVEIROS", "2"));

        deliverableVoList.add(new DeliverableVo("3403", "COMITÊ CLIENTES", "PORTAL ÚNICO (CLARO MÓVEL", "0", "?	UNIFICAR OS 3 PORTAIS DE INFORMAÇÕES EXISTENTES (MÓVEL, TV E NET) - ?	FOMENTAR O AUTOATENDIMENTO - ?	OTIMIZAR ATENDIMENTO COMBOS - ?	OTIMIZAR A GOVERNANÇA E GESTÃO ANALÍTICA ? VISÃO ÚNICA - ?	APOIAR 100% DA LINHA DE FRENTE, INCLUINDO FORÇA DE VENDAS E TÉCNICOS - ?	ADEQUAR-SE À DINÂMICA DO NEGÓCIO", "Open", "15-10-2015", "RODRIGO CARVALHO DOS SANTOS", "2"));

        deliverableVoList.add(new DeliverableVo("3497", "COMITÊ COMERCIAL", "IMPLEMENTAÇÃO DO PLANO SOHO NO SISTEMA WA", "IMPLEMENTAÇÃO DA VENDA DOS PLANOS CLARO ONLINE (SINGLE)", " VIA SISTEMA WA", "Open", "31-03-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("3535", "COMITÊ COMERCIAL", "PARCELAMENTO DE APARELHO", "IMPLANTAR PILOTO PARA PARCELAMENTO DE APARELHO NA FATURA  (NÃO CLARO UP)", "Proposta de alteração de prazo aceita . Favor informar status atualizado.", "Open", "15-02-2016", "RODRIGO AUGUSTO VIDIGAL DE LIMA", "2"));

        deliverableVoList.add(new DeliverableVo("3536", "COMITÊ COMERCIAL", "AÇÕES DOWNGRADE DE PLANOS", "IMPLANTAR AS AÇÕES DE DOWNGRADE DE PLANOS CONFORME: - 1.IMPLANTAÇÃO DE RELATÓRIO DE UPGRADE-DOWNGRADE COM COMPARAÇÃO DE CENÁRIOS (FINANCEIRO E POR SIMILARIDADE) ? DCP-GAR, 23-09 - 2.IMPLANTAR BLOQUEIOS NECESSÁRIOS PARA EVITAR NOVOS DOWNGRADES - A.RETIRAR O MOBILE DA RETENÇÃO PARA AÇÕES DE DOWNGRADE SEM A FUNÇÃO DE APROVAÇÃO ? GAR, 01-10 - B.RETIRAR O MOBILE DA RETENÇÃO PARA AÇÕES DE DOWNGRADE COM A FUNÇÃO DE APROVAÇÃO ? GAR, 20-10 - 3.REVISAR ESCOPO DA OS DE BLOQUEIO MOBILE ? GAR, 30-09", "Proposta de alteração de prazo aceita .", "Open", "08-02-2016", "ALESSANDRO LUIZ VARME DIAS", "2"));

        deliverableVoList.add(new DeliverableVo("3620", "COMITÊ CLIENTES", "IMPLANTAÇÃO DE PA QUALIFICADORA NO ATENDIMENTO", "REALIZAR LEVANTAMENTO DOS MOTIVOS DE LIGAÇÃO COM UMA  + PA QUALIFICADORA +  E IMPLEMENTÁ-LA (METODOLOGIA, UTILIZADA HÁ 6 ANOS NO UMR, MAIS PRECISA E TRAZ RESULTADOS MAIS RELEVANTES DO QUE A MARCAÇÃO REALIZADA PELOS OPERADORES). - ", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "21-03-2016", "JOYCE BEZERRA CAVALCANTI", "2"));

        deliverableVoList.add(new DeliverableVo("3729", "COMITÊ DE RECEITA", "FICHA 227 : RECUPERAÇÃO: CLIENTES PME SEM CONTROLE DE VELOCIDADE DE DADOS", "AÇÃO DE RECUPERAÇÃO: - REALIZAR CORREÇÃO DO BACKLOG DOS ASSINANTES PME QUE POSSUEM PACOTE DE INTERNET E ESTÃO SEM REDUÇÃO DE VELOCIDADE", "Proposta de alteração de prazo aceita . Novo prazo acordado com GAR.", "Open", "29-02-2016", "ALEXANDRE COUTO MORAES", "2"));

        deliverableVoList.add(new DeliverableVo("3828", "COMITÊ COMERCIAL", "REVISAR PROCESSO PARA PERMITIR NAVEGAÇÃO EXCEDENTE DE DADOS AUTOMATICAMENTE (POS E PREPAGO)", "REVISAR PROCESSO PARA PERMITIR NAVEGAÇÃO EXCEDENTE DE DADOS AUTOMATICAMENTE (POS E PREPAGO)", "Proposta de alteração de prazo aceita .", "Open", "20-02-2016", "MARCIO FERREIRA DA SILVA", "2"));

        deliverableVoList.add(new DeliverableVo("3829", "COMITÊ COMERCIAL", "BBT: ESCREVER ESPECIFICAÇÃO CONSIDERANDO PREMISSAS ACORDADAS. REVISAR COM AMX SE NECESSÁRIO", "BBT: ESCREVER ESPECIFICAÇÃO CONSIDERANDO PREMISSAS ACORDADAS. REVISAR COM AMX SE NECESSÁRIO", "Campo Data Acordada alterado de 04-12-2015 para 29-01-2016Demanda de responsabilidade de FIN conforme alinhamento com Presidência.", "Open", "29-01-2016", "ROBERTO CATALAO CARDOSO", "2"));

        deliverableVoList.add(new DeliverableVo("3835", "COMITÊ COMERCIAL", "CONTRATAÇÃO DO SERVIÇO CLEAR-SALES PELA NOVITECH", "20", "CONTRATAÇÃO DO SERVIÇO CLEAR-SALES PELA NOVITECH", "Open", "04-12-2015", "RENATO AMATTO", "2"));

        deliverableVoList.add(new DeliverableVo("3841", "COMITÊ COMERCIAL", "IMPLANTAR AÇÕES PARA GOV NIVEL 2", "0", "IMPLANTAR AÇÕES PARA GOV NIVEL 2", "Open", "04-12-2015", "RENATO AMATTO", "2"));

        deliverableVoList.add(new DeliverableVo("3844", "COMITÊ COMERCIAL", "PROJETO VENDEDORES RESIDENCIAIS NAS LOJAS PRÓPRIAS - TREINAMENTO-LOGINS", "ABERTURA DE DEMANDA AS ÁREAS: TREINAMENTO", " SISTÊMICO LOGINS E RELATÓRIOS", "Open", "22-01-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("3847", "COMITÊ COMERCIAL", "AMPLIAR OPÇÃO DE RECARGA EM CONTA-CORRENTE ", "AMPLIAR OPÇÃO DE RECARGA EM CONTA-CORRENTE ", "Proposta de alteração de prazo aceita .", "Open", "15-03-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("3848", "COMITÊ COMERCIAL", "NOVA RVV - FOCO EM RECEITA ", "NOVA RVV - FOCO EM RECEITA ", "Proposta de alteração de prazo aceita .", "Open", "28-02-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("3849", "COMITÊ COMERCIAL", "GARANTIR QUE TEMOS 100% DOS CASOS ANALISADOS PARA VALIDAÇÃO DE FRAUDE", "GARANTIR QUE TEMOS 100% DOS CASOS ANALISADOS PARA VALIDAÇÃO DE FRAUDE", "Proposta de alteração de prazo aceita .", "Open", "12-02-2016", "FLORIANO PAULINO DA COSTA NETO", "2"));

        deliverableVoList.add(new DeliverableVo("3850", "COMITÊ COMERCIAL", "ESTORNO  - IMPLANTAR ESTORNO PARA CANCELAMENTO-DONWGRADE DE MÓDULOS E ASSINATURAS. REVISÃO DO REGULA", "ESTORNO  - IMPLANTAR ESTORNO PARA CANCELAMENTO-DONWGRADE DE MÓDULOS E ASSINATURAS. REVISÃO DO REGULAMENTO DE REMUNERAÇÃO DOS AGENTES  EM PROCESSO DE VALIDAÇÃO PELAS ÁREAS DE GAR", " COMISSIONAMENTO E JURÍDICO.", "Open", "19-02-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("3853", "COMITÊ COMERCIAL", "MÉDIO PRAZO: IMPLANTAR M4U PARA CONTROLE FÁCIL ", "MÉDIO PRAZO: IMPLANTAR M4U PARA CONTROLE FÁCIL ", "Essa ativitidade depende do projeto controle fácil", "Open", "16-02-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("3858", "COMITÊ COMERCIAL", "AVALIAR IMPACTO NO ATENDIMENTO QUANDO HOUVER BLOQUEIO DE IMEI DIRETAMENTE PELA POLICIA", "AVALIAR IMPACTO NO ATENDIMENTO QUANDO HOUVER BLOQUEIO DE IMEI DIRETAMENTE PELA POLICIA", "Proposta de alteração de prazo aceita .", "Open", "29-02-2016", "TATIANE PIACENTE TOMAZ", "2"));

        deliverableVoList.add(new DeliverableVo("3859", "COMITÊ COMERCIAL", "CRIAR WORKFLOW PARA SUPERVISOR APROVAR O DOWNGRADE", "CRIAR WORKFLOW PARA SUPERVISOR APROVAR O DOWNGRADE", "Clesia, boa tarde. - Segue para atuação. -  - Grata, - at. - Sirlande Jesus dos Santos", "Open", "28-02-2016", "CLESIA PEREIRA CARNEIRO BARBISAN", "2"));

        deliverableVoList.add(new DeliverableVo("3860", "COMITÊ COMERCIAL", "DEFINIR UM % DA BONIFICAÇÃO DO TERCEIRO QUE DEVE SER DESTINADO A BONIFICAÇÃO DO OPERADOR (ALINHAR OB", "DEFINIR UM % DA BONIFICAÇÃO DO TERCEIRO QUE DEVE SER DESTINADO A BONIFICAÇÃO DO OPERADOR (ALINHAR OBJETIVOS DO TERCEIRO E DO OPERADOR)", "Proposta de alteração de prazo aceita .", "Open", "29-02-2016", "CLESIA PEREIRA CARNEIRO BARBISAN", "2"));

        deliverableVoList.add(new DeliverableVo("3861", "COMITÊ COMERCIAL", "MULTA POR CANCELAMENTO - GARANTIR QUE O ATENDIMENTO APLIQUE MULTA  ", "MULTA POR CANCELAMENTO - GARANTIR QUE O ATENDIMENTO APLIQUE MULTA PARA CANCELAMENTO  OU DOWNGRADE DE MÓDULOS-ASSINATURAS", " CONFORME REGRA VIGENTE DE MKT.  ", "Open", "26-02-2016", "RENATO AMATTO", "2"));

        deliverableVoList.add(new DeliverableVo("3862", "COMITÊ COMERCIAL", "INCLUIR % DE DOWNGRADE NA CESTA DE INDICADORES DE RV DO OPERADOR (DEVE SER IMPLEMENTADO EM CONJUNTO", "INCLUIR % DE DOWNGRADE NA CESTA DE INDICADORES DE RV DO OPERADOR (DEVE SER IMPLEMENTADO EM CONJUNTO COM O ITEM ACIMA)", "Proposta de alteração de prazo aceita .", "Open", "29-02-2016", "CLESIA PEREIRA CARNEIRO BARBISAN", "2"));

        deliverableVoList.add(new DeliverableVo("3865", "COMITÊ COMERCIAL", "RETIRAR O MOBILE DA RETENÇÃO PARA AÇÕES DE DOWNGRADE COM A FUNÇÃO DE APROVAÇÃO", "RETIRAR O MOBILE DA RETENÇÃO PARA AÇÕES DE DOWNGRADE COM A FUNÇÃO DE APROVAÇÃO", "", "Open", "31-01-2016", "SERGIO ROBERTO RICUPERO", "2"));

        deliverableVoList.add(new DeliverableVo("3870", "COMITÊ COMERCIAL", "DEFINIR PROCESSO DE VALIDAÇÃO DE PROMOÇÃO", "", "34", "Open", "01-12-2015", " DESCONTOS", "2"));

        deliverableVoList.add(new DeliverableVo("3871", "COMITÊ COMERCIAL", "REVISÃO DE PLATAFORMAS - ELEMENTOS PARA IDENTIFICAR DESVIOS", "REVISÃO DE PLATAFORMAS - ELEMENTOS PARA IDENTIFICAR DESVIOS", "Proposta de alteração de prazo aceita .", "Open", "31-03-2016", "ANTONIO MARCOS LYCERO DE AMORIM", "2"));

        deliverableVoList.add(new DeliverableVo("3874", "COMITÊ COMERCIAL", " REVISAR REGRAS DE NEGÓCIO QUE GERAM TRÁFEGO GRATUITO", "REVISAR REGRAS DE NEGÓCIO QUE GERAM TRÁFEGO GRATUITO", "Proposta de alteração de prazo aceita .", "Open", "08-01-2016", "ALESSANDRO LUIZ VARME DIAS", "2"));

        deliverableVoList.add(new DeliverableVo("4037", "COMITÊ EXECUTIVO", "FATURA ÚNICA POR CLIENTE", "GERAR INDICADOR DA EFETIVIDADE DA AÇÃO DE FATURA ÚNICA POR CLIENTE (origem: tarefa 2054). (Origem: tarefa 2424)", "Proposta de alteração de prazo aceita .", "Open", "19-02-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("4038", "COMITÊ COMERCIAL", "CADASTRO UNIVERSAL DE TODOS OS PARCEIROS PARA VENDER TODOS OS PRODUTOS DO GRUPO", "CADASTRO UNIVERSAL DE TODOS OS PARCEIROS PARA VENDER TODOS OS PRODUTOS DO GRUPO (Origem: tarefa 3496)", "Proposta de alteração de prazo aceita .", "Open", "15-03-2016", "LEANDRO DE MATTOS BUENO", "2"));

        deliverableVoList.add(new DeliverableVo("4040", "COMITÊ COMERCIAL", "PLANO DE AÇÃO CLEARSALE", "PLANO DE AÇÃO CLEARSALE (Origem: tarefa 3852)", "Desconsiderar (excluir) esse último arquivo", "Open", "07-03-2016", "JOSE APARECIDO PRADO", "2"));

        deliverableVoList.add(new DeliverableVo("4041", "COMITÊ EXECUTIVO", "COBRANÇA DE MULTA", "APLICAR PREMISSA DE NÃO ISENTAR APLICAÇÃO DE MULTA DE APARELHO. REVISAR PROCEDIMENTO", " BLOQUEIOS E CASOS DE EXCEÇÃO (Origem: tarefa 2732)", "Open", "29-01-2016", "ALESSANDRO LUIZ VARME DIAS", "2"));

        deliverableVoList.add(new DeliverableVo("4068", "COMITÊ COMERCIAL", "REVISAR POLITICA PAGAMENTO DE MARGEM DE APARELHOS", "REVISAR POLÍTICA QUE TRATA DO PAGAMENTO DE MARGEM E APARELHOS DEFINIDO NOVA REGRA PARA PAGAMENTO DO CANAL AA. CONSIDERAR NA REVISÃO: -  - PAGAR MARGEM PARA AA PARA VENDA DE APARELHO COM PREÇO DE PRÉ-PAGO. - NÃO SERÁ FEITO PAGAMENTO RETROATIVO `PA PUBLICAÇÃO DA POLÍTICA.", "Atividade inserida.", "Open", "15-02-2016", "GUSTAVO NICOLAS BRUNO", "2"));

        deliverableVoList.add(new DeliverableVo("4071", "COMITÊ DE RECEITA", "FICHA 123: CONTAS FORA DE COBRANÇA. MOTIVOS.", "AVALIAR MOTIVOS DE CONTAS SEM FATURAMENTO HÁ MAIS DE 15 DIAS", " IDENTIFICADAS POR GAR. (Origem: tarefa 2735)", "Open", "15-04-2016", "JOAO MARCELO FERNANDES LUCAS", "2"));

        deliverableVoList.add(new DeliverableVo("4072", "COMITÊ DE RECEITA", "FICHA 145: CLIENTES COM DIVERGÊNCIA DE STATUS DE COBRANÇA", "SINCRONIZAR STATUS DE LISTA DE CLIENTES EM SUSPENSÃO FINAL NA RÉGUA DE COBRANÇA E QUE CONTINUAM COM GERAÇÃO DE FATURA.  -  - CONSIDERAR A LISTA DE CLIENTES IDENTIFICADA POR GAR NO DIAGNÓSTICO DA FICHA. (Origem: tarefa 3031)", "Proposta de alteração de prazo aceita . Novo prazo", "Open", "15-04-2016", "JOAO MARCELO FERNANDES LUCAS", "2"));

        deliverableVoList.add(new DeliverableVo("4075", "COMITÊ COMERCIAL", "IMPLEMENTAR VENDA DE CLARO VIDEO COM CARTÃO DE CRÉDITO PARA CLIENTES QUE NÃO DA BASE CLARO. AVALIAR", "IMPLEMENTAR VENDA DE CLARO VIDEO COM CARTÃO DE CRÉDITO PARA CLIENTES QUE NÃO DA BASE CLARO. AVALIAR LANÇAMENTO DO CLARO VIDEO BOX (Origem: tarefa 2091)", "", "Open", "30-06-2016", "ALEXANDRE OLIVARI", "2"));

        deliverableVoList.add(new DeliverableVo("4076", "COMITÊ EXECUTIVO", "IMPLEMENTAÇÃO DO NOVO MODELO DE REMUNERAÇÃO", "IMPLEMENTAÇÃO DO PROJETO DE SOLUÇÃO ANTECIPADA. (Origem: tarefa 2962)", "Proposta de alteração de prazo aceita .", "Open", "22-02-2016", "CELSO LUIZ TONET JUNIOR", "2"));

        deliverableVoList.add(new DeliverableVo("4077", "COMITÊ DE RECEITA", "FICHA 51: AJUSTE POR FALHA CPC", "FICHA 51: AJUSTE POR FALHA CPC - R$15,00MM (Origem: tarefa 2686)", "Varme, -  - A entrega foi validada para conclusão desta atividade? -  - Caso tenhamos outros erros CPC deverá ser encerrada esta demanda e aberto novo diagnóstico? -  - Att, - Rodrigo Carvalho", "Open", "29-01-2016", "ALESSANDRO LUIZ VARME DIAS", "2"));

        deliverableVoList.add(new DeliverableVo("4078", "COMITÊ DE RENTABILIDADE", "PROJETO PLANO LOCADOR", "CRIAR UMA OFERTA PARA OS LOCADORES DE SITES DE FORMA A CONSEGUIR REDUZIR OS CUSTOS DE ALUGUEL E INCREMENTAR A BASE DE CLIENTES. (Origem: tarefa 1947) (Origem: tarefa 2620)", "Polizell", "Open", "29-01-2016", "MARCELO TADEU POLIZELL", "2"));

        deliverableVoList.add(new DeliverableVo("4079", "COMITÊ DE APROVAÇÃO DE INVESTIMENTOS NÃO TI", "PLANO REGIONAL RNO - OFFLOAD", " + IMPLANTAR PLANO DE AÇÃO PARA OFFLOAD DOS MUNICÍPIOS: -  - BACURI: CREDENCIAR UMA LOJA SATÉLITE DO ARMAZEM PARAÍBA NA CIDADE, - NEGOCIAR COM O COMPRADOR DA REDE E ABASTECER COM APARELHOS 3G, - OFERTAR EM CARRO DE SOM, -  - CURUPURU:CREDENCIAR UM PARCEIRO NÃO ESPECIALISTA NA CIDADE, - NEGOCIAR COM O COMPRADOR DO GRUPO A. PARAÍBA E ABASTECER COM APARELHOS 3G, - COLOCAR UMA PROMOTORA NA CIDADE PARA ATENDER A. PARAÍBA, - OFERTAR EM CARRO DE SOM, -  - TIMBIRAS: VISITA DA EQUIPE DE VENDAS  NO DIA 19-08 PARA ESSA LOCALIDADE.  ASSIM QUE ENGENHARIA POSICIONAR SITUAÇÃO DE REDE. (VAI TER AÇÃO PARA ESSE MUNICÍPIO?)  -  +  (Origem: tarefa 3451)", "Campo Data Acordada alterado de 08-10-2015 para 29-01-2016Demanda do exercício 2015 com acompanhamento 2016. Novo prazo para revisar planejamento e ações IW.", "Open", "29-01-2016", "JAIME BATISTA MONTEIRO JUNIOR", "2"));

        deliverableVoList.add(new DeliverableVo("4080", "COMITÊ DE CONTIGENCIAS TRABALHISTAS", "DIFERENÇA SALARIAL SUBSTITUTO - VIABILIDADE DE SALÁRIO DE SUBSTITUIÇÃO", "DIFERENÇA SALARIAL SUBSTITUTO - REALIZAR ESTUDO DE ALTERNATIVAS POR VIABILIDADE (CUSTO E PROCESSO DE IMPLEMENTAÇÃO) - SALÁRIO SUBSTITUÇÃO DE GERENTES E VENDENDORES SENIORES (Origem: tarefa 3286)", "Campo Data Acordada alterado de 30-10-2015 para 29-01-2016Demanda do exercício 2015 com acompanhamento 2016. Novo prazo para revisar planejamento e ações IW.", "Open", "29-01-2016", "REGINA MITSUKO FUCHIGAMI IZAWA", "2"));

        deliverableVoList.add(new DeliverableVo("4081", "COMITÊ DE CONTIGENCIAS TRABALHISTAS", "HORAS EXTRAS - SOBREAVISO - ALTERAÇÕES SISTÊMICAS SOLICITADAS NO RH PONTO", "ALTERAÇÕES SISTÊMICAS SOLICITADAS NO RH PONTO: - . AQUISIÇÃO DO MÓDULO DE ENVIO DE EMAILS PARA OS GESTORES QUANDO OCORRER IRREGULARIDADES DE MARCAÇÃO DE PONTO, - . ANALISAR A POSSIBILIDADE DA DIGITALIZAÇÃO DO CARTÃO DE PONTO ASSINADO PELO COLABORADOR NO RH ONLINE, - . MÓDULO DE ADVERTENCIA NO SISTEMA RH ONLINE - VERIFICAR A POSSIBILIDADE DE UTILIZAR PARA ESTE CASO. (Origem: tarefa 2965)", "Campo Data Acordada alterado de 31-12-2015 para 29-01-2016Demanda do exercício 2015 com acompanhamento 2016. Novo prazo para revisar planejamento e ações IW.", "Open", "29-01-2016", "REGINA MITSUKO FUCHIGAMI IZAWA", "2"));

        deliverableVoList.add(new DeliverableVo("4082", "COMITÊ DE APARELHOS", "NOVO PROCESSO DE ENTREGA DE ROTEADOR E CHIP JUNTOS E PELA CLARO", "DEFINIR E COLOCAR EM OPERAÇÃO NOVO PROCESSO DE ENTREGA DE ROTEADOR E CHIP CORRESPONDENTE JUNTOS E PELA CLARO. HOJE O CHIP É ENTREGUE PELA EMBRATEL E AS ENTREGAS EM SEPARADO GERAM PROBLEMAS DE ABASTECIMENTO. (Origem: tarefa 2490)", "Demerval", "Open", "29-01-2016", "DEMERVAL NARDI MARTINS", "2"));

        deliverableVoList.add(new DeliverableVo("4083", "COMITÊ DE APARELHOS", "CONTROLE SEGREGADO DOS ESTOQUES PF+PME - GE (OS95235 EM DTI)", "CONFIGURAR CONTROLE DE ESTOQUE PARA QUE TENHAMOS VISÃO SEGREGADA DO ESTOQUE MASSIVO (PF+PME) E CORPORATIVO (GE). (Origem: tarefa 2491)", "Demanda finalizada e implementada desde o dia 03-02-16.", "Open", "29-01-2016", "MARTIN ENRIQUE BUTELER", "2"));

        deliverableVoList.add(new DeliverableVo("4086", "COMITÊ COMERCIAL", "COMUNICAÇÃO PROJETO 'RAJADA'", "REALIZAR COMUNICAÇÃO PARA MAIOR CONHECIMENTO INTERNO - DOS CLIENTES SOBRE O PROJETO ?RAJADA' (COM REVISÃO PRÉVIA DA COMUNICAÇÃO COM MKT). (Origem: tarefa 3954)", "", "Open", "29-01-2016", "JOAO MARCELO FERNANDES LUCAS", "2"));

        deliverableVoList.add(new DeliverableVo("4087", "COMITÊ COMERCIAL", "AVALIAR PROPOSTA DE UNIFICAÇÃO DAS MAQUINETAS", "AVALIAR PROPOSTA DE UNIFICAÇÃO DAS MAQUINETAS (Origem: tarefa 3867)", "Campo Data Acordada alterado de 04-12-2015 para 29-01-2016Demanda do exercício 2015 com acompanhamento 2016. Novo prazo para revisar planejamento e ações IW", "Open", "29-01-2016", "JOAO MARCELO FERNANDES LUCAS", "2"));

        deliverableVoList.add(new DeliverableVo("4089", "COMITÊ EXECUTIVO", "NEGOCIAÇÃO COM OS ESCRITÓRIOS", "CONTINGÊNCIAS CONSUMERISTAS - CONCLUIR NEGOCIAÇÃO COM OS ESCRITÓRIOS COM O NOVO MODELO DE REMUNERAÇÃO. (Origem: tarefa 2961)", "Proposta de alteração de prazo aceita . Demanda do exercício 2015 com acompanhamento 2016. Novo prazo para revisar planejamento e ações IW.", "Open", "29-01-2016", "MARTIN ENRIQUE BUTELER", "2"));

        deliverableVoList.add(new DeliverableVo("4092", "COMITÊ CLIENTES", "BLOQUEIO DAS REDES SOCIAIS NA PROMOÇÃO ILIMITADA (VISITA CALL CENTER 17_12)", "APÓS O TÉRMINO DA FRANQUIA DE INTERNET O ACESSO A REDES SOCIAIS É BLOQUEADO OU O CLIENTE É COBRADO DE PACOTE ADICIONAL.  - A REGRA PARA A PROMOÇÃO ILIMITADA É PERMITIR O ACESSO A REDES SOCIAIS APÓS O TERMINO DA FRANQUIA.   - ", "Dyodi, - Esta atividade, além de ser do Comitê de Clientes está no contexto do ICG DEX 2016 - Contact Rate em Plano de Ação para mar-16.", "Open", "01-03-2016", "MARCO DYODI TAKAHASHI", "2"));

        deliverableVoList.add(new DeliverableVo("4110", "COMITÊ DE RECEITA", "FICHA 0286: ROLLOVER INDEVIDO DE BONUS EM REAIS", "CORREÇÃO DO SISTEMA", " ATRAVÉS DE ABERTURA DE OS SOLICITANDO CORRETA APLICAÇÃO DA REGRA.", "Open", "05-02-2016", "ALEXANDRE COUTO MORAES", "2"));

        deliverableVoList.add(new DeliverableVo("4113", "COMITÊ DE REGIONAIS", "DESAFIO CONTA TOTAL PARA REGIONAL NO", "DESAFIO CONFORME ACORDADO NO COMITÊ DE REGIONAIS DE 25-01-2016. CHEGAR a 500 vendas diárias dentro de três meses.  -  - AJUSTES NESTA META DESAFIO DEVEM SER ALINHADOS COM A DIRETORIA DOR.", "Campo Projeto alterado de COMITÊ DE RECEITA para COMITÊ DE REGIONAIS", "Open", "04-04-2016", "DYLCIO JOSE LEAL PORTO", "2"));

        deliverableVoList.add(new DeliverableVo("4114", "COMITÊ DE RECEITA", "FICHA 290: RECUPERAÇÃO: ASSINANTES SEM PLANO E SEM TRÁFEGO (PF", "RECUPERAÇÃO: CANCELAMENTO DAS LINHAS (INCLUIR CRONOGRAMA COM DATA ESTIMADA PARA FINALIZAÇÃO DO TRATAMENTO DOS CASOS).", "Parecer e cronograma compartilhado", "Open", "05-02-2016", "RODRIGO AUGUSTO VIDIGAL DE LIMA", "2"));
        */

        /*
        deliverableVoList.add(new DeliverableVo("T01", "COMITE TECNICO", "FICHA 290: RECUPERAÇÃO: ASSINANTES SEM PLANO E SEM TRÁFEGO.", "Clientes em situação irregular. Cancelar e parar de pagar fistel.", "", "Open", "30/06/2016", "Maria Julia", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T02", "COMITE TECNICO", "Ficha 311 -  Consumo incorreto Bônus. Em recuperação. Clientes PME e GE.", "Correção sistêmica por DTI para uso indevido da franquia de bônus, que está sendo usada para casos como ligações para outras operadoras, fixo e conexão de dados.", "PENDENTE COM ALEXANDRE COUTO NO IW, MAS NA PLANILHA DE CASOS DE USO ESTÁ FINALIZADA. VALIDAR PARA CONCLUSÃO NO IW", "Open", "14/01/2016", "ALEXANDRE COUTO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T03", "COMITE TECNICO", "FICHA 0286: ROLLOVER INDEVIDO DE BONUS EM REAIS. - ", "Solução sistêmcia para evitar o rollover de binus.", "", "Open", "06/05/2016", "Alexandre Couto", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T04", "COMITE TECNICO", "Ficha 0336-2016- Divergência promoção Redes Sociais x Plano Turbo - ", "", "", "Open", "15/04/2016", "Julie Paulino", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T05", "COMITE TECNICO", "FICHA 227 : RECUPERAÇÃO: CLIENTES PME SEM CONTROLE DE VELOCIDADE DE DADOS - ", "AÇÃO DE RECUPERAÇÃO: - REALIZAR CORREÇÃO DO BACKLOG DOS ASSINANTES PME QUE POSSUEM PACOTE DE INTERNET E ESTÃO SEM REDUÇÃO DE VELOCIDADE", "", "Open", "06/05/2016", "Alexandre Couto", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T06", "COMITE TECNICO", "FICHA 92: COBRANÇA DE VALORES EM DISPUTA", "COBRANÇA DE VALORES EM DISPUTA COM PRAZO SUPERIOR AO LIMITE REGULATÓRIO DE COBRANÇA SEM ANUÊNCIA.  -  - AVALIAR O MAILING DE CLIENTES EM DISPUTA COM BAIXA PROPENSÃO A ATRITO PARA CONTATO DE ANUÊNCIA DE COBRANÇA. QUANTIFICAR E ALINHAR AÇÃO COM REGULATÓRIO.", "[08/04] - Solciiatda finalização por Marco Campos.", "Open", "06/05/2016", "JOSE APARECIDO PRADO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T07", "COMITE TECNICO", "FICHA 224: CONTESTAÇÃO LD OUTRAS OPERADORES (DIFERENTE DE 21) - CLIENTES PME", "REALIZAR REVERSÃO/CONTESTAÇÃO (DESCONTO NO REPASSE PARA OUTRAS OPERADORAS DE LONGA DISTÂNCIA) DOS VALORES DE CONSUMO LD DIFERENTE DE 21 PARA O SEGUINTE CENÁRIO: -  - CLIENTES PME CANCELADOS COM FATURAS ARRECADADAS NO PERÍODO DE JAN/14 A MAI/15 PARA OS EVENTOS", "", "Open", "29/04/2016", "ALEXANDRE COUTO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T08", "COMITE TECNICO", "FICHA 064: NOVITECH - CHARGE BACK - VOLUME ELEVADO DE CONTESTAÇÃO", "REDUZIR O CHARGE BACK NO CONTROLE FÁCIL", "ver data", "Open", "04/04/2016", "JOSE APARECIDO PRADO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T09", "COMITE TECNICO", "FICHA 230: CLIENTES PF  SEM PLANO DE PREÇO - SISTEMAS", "CLIENTES PF  SEM PLANO DE PREÇO - SISTEMAS. -  - AVALIAR OS CASOS REPORTADOS NO PERÍODO DE 12/08/2015 A 14/08/2015 E ELABORAR PLANO DE CORREÇÃO DA CAUSA RAIZ. -  - FICHA DO CASO EM ANEXO.", "", "Open", "15/04/2016", "MARIA JULIA", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T10", "COMITE TECNICO", "FICHA 232: ASSINANTES PME SEM COMPROMISSO", "ASSINANTES PME SEM COMPROMISSO -  - ELABORAR PLANO DE CORREÇÃO DO BACKLOG E CAUSA RAIZ -  - FICHA DO CASO EM ANEXO.", "", "Open", "15/04/2016", "ALEXANDRE COUTO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T11", "COMITE TECNICO", "FICHA 149: FALHA NO PROCESSO DE ZERO FRAUDE", "O PROCESSO ?ZERO FRAUDE? ESTÁ ZERANDO VALORES ELEGÍVEIS A FATURAMENTO. -  - CORRIGIR O PROCESSO DE RETIRADA DE EVENTOS NO PRÉ-BILLING (FLAG ZERO FRAUDE), GARANTINDO QUE SOMENTE AS CHAMADAS FRAUDULENTAS E DENTRO DO PERÍODO SEJAM RETIRADAS. - IDENTIFICAR E CORRIG", "[06/04] - Novo prazo proposto: 05/05/2016. Prazo para avaliar ha necessidade de abertura da OS de sincronismo entre CLASSE F x FLAG pois ha pontos que ainda precisam ser esclarecidos.", "Open", "05/05/2016", "JOSE APARECIDO PRADO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T12", "COMITE TECNICO", "FICHA 123: CONTAS FORA DE COBRANÇA. MOTIVOS.", "AVALIAR MOTIVOS DE CONTAS SEM FATURAMENTO HÁ MAIS DE 15 DIAS, IDENTIFICADAS POR GAR. (Origem: tarefa 2735)", "PENDENTE COM JOÃO MARCELO NO IW, MAS NA PLANILHA DE CASOS DE USO ESTÁ FINALIZADA. VALIDAR PARA CONCLUSÃO NO IW -  - [07/04] - Chamado para tratamento de 4 BANs pendentes em atraso", "Open", "08/04/2016", "CLESSIUS", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T13", "COMITE TECNICO", "FICHA 145: CLIENTES COM DIVERGÊNCIA DE STATUS DE COBRANÇA", "SINCRONIZAR STATUS DE LISTA DE CLIENTES EM SUSPENSÃO FINAL NA RÉGUA DE COBRANÇA E QUE CONTINUAM COM GERAÇÃO DE FATURA.  -  - CONSIDERAR A LISTA DE CLIENTES IDENTIFICADA POR GAR NO DIAGNÓSTICO DA FICHA. (Origem: tarefa 3031)", "[07/04] - Aberto por Adriana Pepato chamado 2580513, do último lote PME (500 contas) até fim de Abril. - Chamados de Março GE e PF/PME ainda não finalizado. - Fevereiro reportado como executado todas as suspensões. Em apuração GAR. - Conta TDMA pendente.  -  - [31/03", "Open", "30/04/2016", "CLESSIUS", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T14", "COMITE TECNICO", "FICHA 51: AJUSTE POR FALHA CPC", "FICHA 51: AJUSTE POR FALHA CPC - R$15,00MM (Origem: tarefa 2686)", "PENDENTE COM ALESSANDRO VARME NO IW, MAS NA PLANILHA DE CASOS DE USO ESTÁ FINALIZADA. VALIDAR PARA CONCLUSÃO NO IW -  - fichas 50 e 51 na mesma linha do arquivo de GAR. Confirmar valor da ação ", "Open", "29/01/2016", "ALESSANDRO LUIZ VARME DIAS", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T15", "COMITE TECNICO", "FICHA 0322: DIAGNÓSTICO: SUSPENSÃO VOLUNTÁRIA GRATUITA PF", "PARECER DE MARKETING COM RELAÇÃO A FICHA 322. -  - AVALIAÇÃO DA BASE E PARECER SOBRE O QUE DEVE SER FEITO COM AS LINHAS ( Retomar , cancelar, ETC ... )", "[07/04] - Fernanda Santos Rodrigues (MKT) entregou as fichas assinadas. Avaliar finalização do diagnóstico.", "Open", "31/12/2016", "MARIA JULIA FERREIRA CAMILO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T16", "COMITE TECNICO", "FICHA 324: DIAGNÓSTICO: LINHAS EM SUSPENSÃO PARCIAL PARA CANCELAMENTO, APÓS PERÍODO DE RETENÇÃO (PF)", "NECESSITAMOS DO PARECER DE MARKETING COM RELAÇÃO A FICHA 324 , ONDE OS ASSINANTES ESTÃO SUSPENSÃO PARCIAL CANCELAMENTO HÁ MAIS DE 7 DIAS. - AVALIAÇÃO DA BASE E PARECER SOBRE O QUE DEVE SER FEITO COM AS LINHAS ( RETOMAR, CANCELAR, OUTROS ... ) - ", "[07/04] - Fernanda Santos Rodrigues (MKT) entregou as fichas assinadas. Avaliar finalização do diagnóstico.", "Open", "31/12/2016", "MARIA JULIA FERREIRA CAMILO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T17", "COMITE TECNICO", "REDUÇÃO DE AJUSTES POR FRAUDE", "ENCAMINHAR CRONOGRAMA E PLANO DE AÇÃO, BEM COMO O ACOMPANHAMENTO DAS AÇÕES REALIZADAS PARA REDUZIR OS AJUSTES COM O MOTIVO DE FRAUDE A FIM DE QUE O VALOR TOTAL DE AJUSTES ATINJA A META DE NO MÁXIMO 1% DA RECEITA", "AVALIAR VALOR DA AÇÃO", "Open", "06/05/2016", "JOSE APARECIDO PRADO", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T18", "COMITE TECNICO", "REDUÇÃO DE AJUSTES COM MOTIVO DE MULTA", "ENCAMINHAR CRONOGRAMA E PLANO DE AÇÃO PARA REDUZIR OS AJUSTES COM O MOTIVO DE MULTA", "AVALIAR VALOR DA AÇÃO", "Open", "31/03/2016", "SYBELLE", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T19", "COMITE TECNICO", "Utilização de licenças do HSS para assinantes que não trafegam no 4G.", "Avaliar usuários GE e PME que não trafegam 4G porém utilizam licenças HSS e alternativa para atender a alocação de licenças de acordo coma necessidade.", "[07/04] - Na ausência de Edilson Briotto que está em férias, agendada reunião com Wanderson Alexandre Oliveira (11 992158418).", "Open", "14/04/2016", "Edilson Briotto", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T20", "COMITE TECNICO", "Queda no tráfego de dados no último dia do mês.  - Quedas: - 30/11/2015 - 31/12/2015 - 31/01/2016 - 29/02/2016 - ", "Avaliar se temos algum problema técnico que leva a queda de tráfego de dados de clientes pós-pago no último dia do mês.", "AVALIAR VALOR DA AÇÃO -  - [07/04] - Análise preliminar de ENG (mês de Março) não iodentifcou indícios de falha.  - Amorim solicitou acompnhamento do faturamento do mês de Março com os ciclos que fecham ao lonog de Abril. - Alexandre Martins seguirá com avaliação d", "Open", "14/04/2016", "Alexandre Martins", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T21", "COMITE TECNICO", "Ficha 338 - Assinantes Street Seller sem faturamento", "", "Fernanda Santos Rodrigues (MKT): Trazer fichas assinadas", "Open", "31/12/2016", "Fernanda Santos", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T22", "COMITE TECNICO", "REDUÇÃO DE OFENSORES DE AJUSTE", "ENCAMINHAR CRONOGRAMA E PLANO DE AÇÃO, BEM COMO O ACOMPANHAMENTO DAS AÇÕES REALIZADAS PARA REDUZIR OS OFENSORES DE AJUSTES PARA OS SEGMENTOS PF E PME A FIM DE QUE O VALOR TOTAL DE AJUSTES ATINJA A META DE NO MÁXIMO 1% DA RECEITA - (LÊ-SE COMO OFENSORES DE A", "AVALIAR VALOR DA AÇÃO -  - [05/04] - Mover para comitê técnico. -  - [04/03] - Solicitado por GAR por frente de redução de ajustes.", "Open", "11/03/2016", "SYBELLE", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T23", "COMITE TECNICO", "REDUÇÃO DE AJUSTES DE AGREGADORES", "ENCAMINHAR CRONOGRAMA E PLANO DE AÇÃO, BEM COMO O ACOMPANHAMENTO DAS AÇÕES REALIZADAS PARA REDUZIR OS AJUSTES COM O MOTIVO DE AGREGADORES A FIM DE QUE O VALOR TOTAL DE AJUSTES ATINJA A META DE NO MÁXIMO 1% DA RECEITA", "[05/04] - Mover para comitê técnico. -  - [04/03] - Solicitado por GAR por frente de redução de ajustes.", "Open", "11/03/2016", "SYBELLE", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T24", "COMITE TECNICO", "REDUÇÃO DE AJUSTES RECORRENTES", "ENCAMINHAR CRONOGRAMA E PLANO DE AÇÃO, BEM COMO O ACOMPANHAMENTO DAS AÇÕES REALIZADAS PARA REDUZIR OS AJUSTES RECORRENTES (PF/PME/PRÉ) A FIM DE QUE O VALOR TOTAL DE AJUSTES ATINJA A META DE NO MÁXIMO 1% DA RECEITA", "AVALIAR VALOR DA AÇÃO -  - [05/04] - Mover para comitê técnico. -  - [10/03] - Luis Amaral direcionou para Sybele em ACL. Alinhar. -  - [04/03] - Solicitado por GAR por frente de redução de ajustes.", "Open", "11/03/2016", "LUIS HENRIQUE DA SILVA AMARAL", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T25", "COMITE TECNICO", "Composição de oferta Claro Turbo", "", "Data prevista para o término da Recuperação, conforme controle de GAR.", "Open", "05/04/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T26", "COMITE TECNICO", "Clientes PABX sem cadastro no mobile e ativo na central", "", "Data prevista para o término da Recuperação, conforme controle de GAR.", "Open", "08/04/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T27", "COMITE TECNICO", "Ressarcimento - Falha do fornecedor Promon", "", "Data prevista para o término da Recuperação, conforme controle de GAR.", "Open", "14/04/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T28", "COMITE TECNICO", "Venda de Aparelhos com desconto para clientes inadimplentes [venda irregular]", "", "Data prevista para o término da Recuperação, conforme controle de GAR.", "Open", "30/06/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T29", "COMITE TECNICO", "Descontos dependentes Claro Online turbo", "", "Data prevista para o término da Recuperação, conforme controle de GAR.", "Open", "11/04/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T30", "COMITE TECNICO", "Oportunidade de receita com o pacote “Magazine Luiza”", "", "Data prevista para o término do Diagnóstico, conforme controle de GAR.", "Open", "15/04/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T31", "COMITE TECNICO", "Cliente Pré-Pago com pacote de bônus do Controle ", "", "Data prevista para o término do Diagnóstico, conforme controle de GAR.", "Open", "11/04/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T32", "COMITE TECNICO", "Ressarcimento - Interrupção do serviço de ativação de novas linhas via plataforma ODA", "", "Data prevista para o término do Diagnóstico, conforme controle de GAR.", "Open", "30/05/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T33", "COMITE TECNICO", "Queda na recarga do Pré-Pago no dia 27/02", "", "Data prevista para o término do Diagnóstico, conforme controle de GAR.", "Open", "30/04/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T34", "COMITE TECNICO", "Clientes com pacote Magazine sem limite de navegação no RG7", "", "Data prevista para o término do Diagnóstico, conforme controle de GAR.", "Open", "30/04/2016", "", "2","NO","","",""));

        deliverableVoList.add(new DeliverableVo("T35", "COMITE TECNICO", "Descontos retenção inseridos fora da vigência promocional", "", "Data prevista para o término do Diagnóstico, conforme controle de GAR.", "Open", "30/04/2016", "", "2","NO","","",""));

        */



        //remove
        //insert list of work items
        //access initiative list via Iterator
        Iterator iterator2 = deliverableVoList.iterator();
        while(iterator2.hasNext()){
            DeliverableVo deliverableVo = (DeliverableVo) iterator2.next();
            Log.d("DemoSynchronize","Inserting into database : " + deliverableVo.getTitle());
            //...insert into initiative table
            deliverableDAO.insertDeliverableVo(deliverableVo);
        }

        //
        //CREATE ADMIN LIST
        //
        adminVoList.add(new AdminVo("1","RODRIGO CARVALHO DOS SANTOS"));


        //insert list of initiatives
        //access initiative list via Iterator
        Iterator iterator3 = adminVoList.iterator();
        while(iterator3.hasNext()){
            AdminVo adminVo = (AdminVo) iterator3.next();
            //...insert into Pmo table
            adminDAO.insertPmo(adminVo);
        }
        //END OF DEMO LOCAL CREATION
    }
}
