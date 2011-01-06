package br.com.notaroberto.util;

/**
 *
 * @author Lessandro
 */
public class Constantes {

    /* Opcoes Gerais */
    public static final String PAGINA_LOGIN = "/login.xhtml";
    public static final String PAGINA_INDEX = "/index.xhtml";
    public static final String PAGINA_EXIBIR_PERFIL = "exibirPerfis.xhtml";
    public static final String PAGINA_EXIBIR_USUARIO = "exibirUsuarios.xhtml";
    public static final Integer CLIENTE_TIPO_PESSOA_FISICA = 0;
    public static final Integer CLIENTE_TIPO_PESSOA_JURIDICA = 1;
    public static final Integer ID_PERFIL_ADMINISTRADOR = 1;

    public static final String MENU_CADASTRO = "Cadastro";
    public static final String MENU_HOME = "Home...";
    public static final String MENU_USUARIO = "Usuario...";
    public static final String MENU_PERFIL = "Perfil...";
    public static final String MENU_CLIENTE = "Cliente...";
    public static final String MENU_ESTOQUE = "Estoque...";
    public static final String MENU_FORMAPGTO = "Forma de Pagamento...";
    public static final String MENU_PEDIDO = "Pedido...";
    public static final String MENU_FORNECEDOR = "Fornecedor...";
    public static final String MENU_PRODUTO = "Produto...";
    public static final String MENU_ENCERRARSESSAO = "Encerrar Sessão...";
    public static final String MENU_SAIR = "Sair";

    public static final String URL_MENU_HOME = "/index?faces-redirect=true";
    public static final String URL_MENU_USUARIO = "/jsf/usuario/exibirUsuarios?faces-redirect=true";
    public static final String URL_MENU_PERFIL = "/jsf/perfil/exibirPerfis?faces-redirect=true";
    public static final String URL_MENU_CLIENTE = "/jsf/cliente/exibirClientes?faces-redirect=true";
    public static final String URL_MENU_ESTOQUE = "/jsf/estoque/exibirEstoques?faces-redirect=true";
    public static final String URL_MENU_FORMAPGTO = "/jsf/formaPgto/exibirFormasPgto?faces-redirect=true";
    public static final String URL_MENU_PEDIDO = "/jsf/pedido/exibirPedidos?faces-redirect=true";
    public static final String URL_MENU_FORNECEDOR = "/jsf/fornecedor/exibirFornecedores?faces-redirect=true";
    public static final String URL_MENU_PRODUTO = "/jsf/produto/exibirProdutos?faces-redirect=true";
    public static final String ACAO_MENU_ENCERRARSESSAO = "#{autenticacaoController.encerraSessao}";
}
