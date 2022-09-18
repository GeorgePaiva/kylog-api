package br.com.kylog.api.controller;

import br.com.kylog.domain.model.Client;
import br.com.kylog.domain.service.CatalogClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

    private CatalogClientService catalogClientService;

    @GetMapping
    public List<Client> findAll() {
        return catalogClientService.findAll();
    }

    @GetMapping(value = "/{clientId}")
    public ResponseEntity<Client> findById(@PathVariable Long clientId) {
        return catalogClientService.findById(clientId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@Valid @RequestBody Client client) {
        return catalogClientService.save(client);
    }

    @PutMapping(value = "/{clientId}")
    public ResponseEntity<Client> updateClient(@Valid @PathVariable Long clientId, @RequestBody Client client) {
        if (!catalogClientService.findById(clientId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        client.setId(clientId);
        Client save = catalogClientService.save(client);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping(value = "/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        if (!catalogClientService.findById(clientId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        catalogClientService.delete(clientId);
        return ResponseEntity.noContent().build();

    }

}
