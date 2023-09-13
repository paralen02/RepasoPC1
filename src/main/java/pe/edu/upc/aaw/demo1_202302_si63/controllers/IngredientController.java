package pe.edu.upc.aaw.demo1_202302_si63.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.aaw.demo1_202302_si63.dtos.IngredientDTO;
import pe.edu.upc.aaw.demo1_202302_si63.entities.Ingredient;
import pe.edu.upc.aaw.demo1_202302_si63.serviceinterfaces.IIngredientService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ingredientes")
public class IngredientController{
    @Autowired
    private IIngredientService iS;

    @PostMapping
    public void registrar(@RequestBody IngredientDTO dto) {
        ModelMapper m = new ModelMapper();
        Ingredient i = m.map(dto, Ingredient.class);
        iS.insert(i);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<IngredientDTO> listar() {
        return iS.list().stream().map(x -> {
            ModelMapper m = new ModelMapper();
            return m.map(x, IngredientDTO.class);
        }).collect(Collectors.toList());

    }

}
