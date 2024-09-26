package com.application.rest.controller;

import com.application.rest.controller.dto.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.service.IMakerService;
import com.application.rest.service.impl.MakerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maker")
public class MakerController {

    @Autowired
    private IMakerService makerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Maker> makerOptional = makerService.findById(id);

        if(makerOptional.isPresent()){
            Maker maker = makerOptional.get();

            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .nombre(maker.getNombre())
                    .productList(maker.getProductList())
                    .build();

            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<MakerDTO> makerList = makerService.findAll()
                .stream()
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .nombre(maker.getNombre())
                        .productList(maker.getProductList())
                        .build())
                .toList();
        return ResponseEntity.ok(makerList);
    }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {

        if(makerDTO.getNombre().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        makerService.save(Maker.builder()
                .nombre(makerDTO.getNombre())
                .build());
        return ResponseEntity.created(new URI("api/maker/")).build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO){
        Optional<Maker> makerOptional = makerService.findById(id);

        if (makerOptional.isPresent()){
            Maker maker = makerOptional.get();
            maker.setNombre(makerDTO.getNombre());
            makerService.save(maker);
            return ResponseEntity.ok("Registro actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteByud(@PathVariable long id){

        if(id != null){
            makerService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }

}
