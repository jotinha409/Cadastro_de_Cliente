package com.seuprojeto;

public class ClienteService {

    private final ClienteDAO dao;

    public ClienteService(ClienteDAO dao) {
        this.dao = dao;
    }

    public boolean cadastrarCliente(Cliente cliente) throws Exception {
        if (dao.cpfExiste(cliente.getCpf())) {
            throw new Exception("CPF já cadastrado no sistema.");
        }

        boolean sucesso = dao.cadastrarCliente(cliente);

        if (!sucesso) {
            throw new Exception("Erro ao cadastrar cliente no banco.");
        }

        return true;
    }
}
