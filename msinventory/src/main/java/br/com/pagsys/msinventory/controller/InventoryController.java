package br.com.pagsys.msinventory.controller;

import br.com.pagsys.msinventory.dto.ProductDto;
import br.com.pagsys.msinventory.model.Product;
import br.com.pagsys.msinventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = true) int page, @RequestParam(required = true) int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = inventoryService.getAll(pageable);

        return ResponseEntity.ok(productsPage);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable(required = true, value = "id") String id){

        Product product = inventoryService.getById(id);

        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @RolesAllowed({"product_management"})
    public ResponseEntity<?> create(@RequestBody @Valid ProductDto dto){
        Product product = inventoryService.create(dto);

        return ResponseEntity.ok(product);
    }

    @PutMapping("{id}")
    @RolesAllowed({"product_management"})
    public ResponseEntity<?> update(@RequestBody @Valid ProductDto dto,
                                    @PathVariable(required = true, value = "id") String id){
        Product product = inventoryService.update(id, dto);

        return product!= null? ResponseEntity.ok(product): ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @RolesAllowed({"product_management"})
    public ResponseEntity<?> delete(@PathVariable(required = true, value = "id") String id){


        Integer response = inventoryService.delete(id);

        return response == 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}
