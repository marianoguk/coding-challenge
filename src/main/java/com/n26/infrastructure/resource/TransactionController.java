package com.n26.infrastructure.resource;

import com.n26.domain.dto.TransactionDto;
import com.n26.domain.service.TransactionService;
import com.n26.infrastructure.resource.documentation.TransactionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController implements TransactionResource {
    @Autowired
    private TransactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody TransactionDto toCreate){
        service.create(toCreate);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        service.deleteAll();
    }
}