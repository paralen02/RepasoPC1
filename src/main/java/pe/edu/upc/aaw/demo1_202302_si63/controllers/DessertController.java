package pe.edu.upc.aaw.demo1_202302_si63.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.aaw.demo1_202302_si63.dtos.AmountDessertDTO;
import pe.edu.upc.aaw.demo1_202302_si63.dtos.DessertDTO;
import pe.edu.upc.aaw.demo1_202302_si63.dtos.IngredientDessertDTO;
import pe.edu.upc.aaw.demo1_202302_si63.entities.Dessert;
import pe.edu.upc.aaw.demo1_202302_si63.serviceinterfaces.IDessertService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dulces")
public class DessertController {
    @Autowired
    private IDessertService dS;
    @PostMapping
    public void registrar(@RequestBody DessertDTO dto) {
        ModelMapper m = new ModelMapper();
        Dessert d = m.map(dto, Dessert.class);
        dS.insert(d);
    }

    @PutMapping
    public void modificar(@RequestBody DessertDTO dto) {
        ModelMapper m = new ModelMapper();
        Dessert d = m.map(dto, Dessert.class);
        dS.insert(d);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable("id") Integer id) {
        dS.delete(id);
    }

    @GetMapping("/{id}")
    public DessertDTO listarId(@PathVariable("id") Integer id) {
        ModelMapper m=new ModelMapper();
        DessertDTO dto=m.map(dS.listarId(id),DessertDTO.class);
        return dto;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<DessertDTO> listar() {
        return dS.list().stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,DessertDTO.class);
        }).collect(Collectors.toList());

    }
    @PostMapping("/buscar")
    public List<DessertDTO> buscar(@RequestBody LocalDate fecha) {
        return dS.findByDueDateDessert(fecha).stream().map(x->{
            ModelMapper m=new ModelMapper();
            return m.map(x,DessertDTO.class);
        }).collect(Collectors.toList());
    }
    @GetMapping("/cantidades")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<IngredientDessertDTO> cantidadIngredientesPorPostre(){
        List<String[]> lista= dS.quantityIngredientsByDessert();
        List<IngredientDessertDTO> listaDTO=new ArrayList<>();
        for (String[] data:lista){
            IngredientDessertDTO dto=new IngredientDessertDTO();
            dto.setNameDessert(data[0]);
            dto.setQuantityIngredients(Integer.parseInt(data[1]));
            listaDTO.add(dto);
        }
        return listaDTO;
    }

    @GetMapping("/precio")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<AmountDessertDTO> precioPorPostre(){
        List<String[]> lista= dS.amountDessert();
        List<AmountDessertDTO> listaDTO=new ArrayList<>();
        for (String[] data:lista){
            AmountDessertDTO dto=new AmountDessertDTO();
            dto.setNameDessert(data[0]);
            dto.setAmountDessert(Double.parseDouble(data[1]));
            listaDTO.add(dto);
        }
        return listaDTO;
    }
}
