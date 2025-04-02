package com.adarsh.LeaveManagementSystem.controller;

import com.adarsh.LeaveManagementSystem.ExceptionHandlers.LeaveRequestNotFoundException;
import com.adarsh.LeaveManagementSystem.Feign.EmployeeFeignController;
import com.adarsh.LeaveManagementSystem.dto.LeaveRequest;
import com.adarsh.LeaveManagementSystem.dto.LeaveType;
import com.adarsh.LeaveManagementSystem.refDto.Employee;
import com.adarsh.LeaveManagementSystem.service.LeaveService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/leave")
@CrossOrigin(origins = "http://localhost:4200")
public class LeaveController {

    @Autowired
    private EmployeeFeignController employeeFeignController;

    @Autowired
    private LeaveService service;

    @Autowired
    private LeaveTypeController leaveTypeController;

    @PostMapping("/save")
    public LeaveRequest requestleaveOrsaveleave(@RequestBody LeaveRequest leaverequest) {
        // Validate employee exists
        Optional<Employee> optional = employeeFeignController.getEmployeeByIdpost(leaverequest.getEmployeeid());
        if (!optional.isPresent()) {
            throw new RuntimeException("Employee not found");
        }

        // Validate leave type is not null
        if (leaverequest.getLeaveType() == null) {
            throw new RuntimeException("Leave type must be specified");
        }

        // Get current leave balances
        LeaveType lt = leaveTypeController.getleavetypebyempid(leaverequest.getEmployeeid())
                .orElseThrow(() -> new RuntimeException("Leave balances not found for employee"));

        int days = leaverequest.getNumberOfDays();
        String leaveType = leaverequest.getLeaveType();

        // Check and deduct leave balance
        switch (leaveType.toLowerCase()) {
            case "vacationcount":
                if (lt.getVacationCount() < days) {
                    throw new RuntimeException("Not enough vacation days remaining");
                }
                lt.setVacationCount(lt.getVacationCount() - days);
                break;
            case "sickleavecount":
                if (lt.getSickLeaveCount() < days) {
                    throw new RuntimeException("Not enough sick leave days remaining");
                }
                lt.setSickLeaveCount(lt.getSickLeaveCount() - days);
                break;
            case "remoteworkcount":
                if (lt.getRemoteWorkCount() < days) {
                    throw new RuntimeException("Not enough remote work days remaining");
                }
                lt.setRemoteWorkCount(lt.getRemoteWorkCount() - days);
                break;
            case "personalcount":
                if (lt.getPersonalCount() < days) {
                    throw new RuntimeException("Not enough personal days remaining");
                }
                lt.setPersonalCount(lt.getPersonalCount() - days);
                break;
            case "paidcount":
                if (lt.getPaidCount() < days) {
                    throw new RuntimeException("Not enough paid days remaining");
                }
                lt.setPaidCount(lt.getPaidCount() - days);
                break;
            default:
                throw new RuntimeException("Invalid leave type specified");
        }

        // Update the leave type record
        leaveTypeController.updateLeaveType(leaverequest.getEmployeeid(), lt);

        // Process the leave request
        return service.requestleaveOrsaveleave(leaverequest);
    }


    @PutMapping("/update")
    public LeaveRequest updateleave(@RequestBody LeaveRequest leaverequest) {
        try {
            return service.updateleave(leaverequest);
        } catch (LeaveRequestNotFoundException ex) {
            throw new LeaveRequestNotFoundException("Leave request not found for update: " + ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while updating the leave request: " + ex.getMessage());
        }
    }

    @GetMapping("/all")
    public List<LeaveRequest> getallemployees() {
    return   service.getallleave();
    }


    @GetMapping("/leaveid/{employeeId}")
    public  Optional<LeaveRequest> findByEmployee(@PathVariable Long employeeId){
        return  service.findByEmployee(employeeId);
    }

    @GetMapping("/test")
    public  Optional<Employee> findByEmployee1(){
        Optional<Employee>   o= employeeFeignController.getEmployeeByIdpost(7L);
        System.out.println(o.get());
        return Optional.of(o.get());
    }

    @PatchMapping("/update-status/{id}")
    public LeaveRequest updatestatus(@PathVariable Long id, @RequestBody LeaveRequest leaveRequest) {
   return service.updatestatus(id,leaveRequest);
    }

}
