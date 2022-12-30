package com.firstock.employee.controller;

import com.firstock.employee.employeeService.EmployeeService;
import com.firstock.employee.entity.AuthRequest;
import com.firstock.employee.entity.Employee;
import com.firstock.employee.jwt.utils.JwtUtil;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@Validated
@Valid
@RestController
@RequestMapping("/api/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @Autowired(required = true)
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Gson gson = new Gson();


    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("invalid username/password=> " + ex);
        }
        return gson.toJson(jwtUtil.generateToken(authRequest.getUsername()));
    }

    @PostMapping("/save")
    public ResponseEntity<?> savaEmp(@RequestBody @Valid Employee employee, @NotNull BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<String>(gson.toJson("Employee not save"), HttpStatus.BAD_REQUEST);
        } else if (employeeService.exisById(employee.getId())) {
            return new ResponseEntity<String>(gson.toJson("Duplicate employee Id"), HttpStatus.BAD_REQUEST);
        } else {
            employeeService.saveEmp(employee);
            return new ResponseEntity<String>(gson.toJson("employee save SuccesFully"), HttpStatus.OK);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmp() {
        List<Employee> employees = null;
        try {
            employees = (List<Employee>) employeeService.getAllEmp();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        if (employees != null) {
            return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(gson.toJson("no Employee found"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmpById(@PathVariable @Valid int id) {
        return new ResponseEntity<Employee>(employeeService.getEmpById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmp(@PathVariable int id) {

        employeeService.deleteEmp(id);
        return new ResponseEntity<String>("Delete Successfully", HttpStatus.OK);
    }

        @PostMapping(value = "/update/{id}",consumes = { "application/json" })
    public ResponseEntity<?> updateEmp(@PathVariable int id, @RequestBody @Valid Employee employee, @NotNull BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<Employee>(employee,HttpStatus.BAD_REQUEST);
        }else if (employeeService.exisById(employee.getId())){
            return new ResponseEntity<String>("Employee updated ",HttpStatus.OK);
        }else {
            return new ResponseEntity<String>(gson.toJson("No Employee found"), HttpStatus.BAD_REQUEST);
        }
    }
//    @PostMapping("/update/{id}")
//    public ResponseEntity<Employee> updateEmp(@PathVariable int id, @RequestBody Employee employee) {
//        return new ResponseEntity<Employee>(employeeService.updateEmp(id, employee), HttpStatus.OK);
//    }
}
