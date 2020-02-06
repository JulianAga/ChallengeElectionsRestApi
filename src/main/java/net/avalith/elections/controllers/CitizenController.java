package net.avalith.elections.controllers;

import net.avalith.elections.models.Citizen;
import net.avalith.elections.services.CitizenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/User")
public class CitizenController {


        @Autowired
        CitizenService citizenService;

        @GetMapping
        public List<Citizen> getAll(){
            return citizenService.getAll();
        }

        @PostMapping
        public void insert(Citizen citizen){
            citizenService.insert(citizen);
        }

        @DeleteMapping
        public void delete(Long id){
            citizenService.delete(id);
        }

        @PutMapping
        public void modify(Citizen citizen){
            citizenService.insert(citizen);
        }
}
