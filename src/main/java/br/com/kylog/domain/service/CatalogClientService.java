package br.com.kylog.domain.service;

import br.com.kylog.domain.exception.BusinessException;
import br.com.kylog.domain.model.Client;
import br.com.kylog.domain.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CatalogClientService {

    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Transactional
    public Client save(Client client) {
        boolean emailEmUso = clientRepository.findByEmail(client.getEmail()).stream().anyMatch(clienteExistente -> !clienteExistente.equals(client));

        if (emailEmUso) {
            throw new BusinessException("Já existe um cliente cadastrado com este e-mail.");
        }
        return clientRepository.save(client);
    }

    @Transactional
    public void delete(Long clientId) {
        clientRepository.deleteById(clientId);
    }
}
