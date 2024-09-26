package com.application.rest.controller;

import com.application.rest.controller.dto.MakerDTO;
import com.application.rest.controller.dto.ProductDTO;
import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;
import com.application.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .nombre(product.getNombre())
                    .precio(product.getPrecio())
                    .maker(product.getMaker())
                    .build();

            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

        @GetMapping("/findall")
        public ResponseEntity<?> findAll(){
            List<ProductDTO> productList = productService.findAll()
                    .stream()
                    .map(product -> ProductDTO.builder()
                            .id(product.getId())
                            .nombre(product.getNombre())
                            .precio(product.getPrecio())
                            .maker(product.getMaker())
                            .build())
                    .toList();
            return ResponseEntity.ok(productList);
        }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ProductDTO productDTO) throws URISyntaxException {

        if(productDTO.getNombre().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        productService.save(Product.builder()
                .nombre(productDTO.getNombre())
                .build());
        return ResponseEntity.created(new URI("api/product/")).build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()){
            Product product = productOptional.get();
            product.setPrecio(productDTO.getPrecio());
            product.setNombre(productDTO.getNombre());
            productService.save(product);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteByud(@PathVariable long id){

        if(id != null){
            productService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

}
