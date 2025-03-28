package com.adarsh.LeaveManagementSystem.controller;

import com.adarsh.LeaveManagementSystem.ExceptionHandlers.InvalidLeaveRequestException;
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

//    @PostMapping("/save")
//    public LeaveRequest requestleaveOrsaveleave(@RequestBody LeaveRequest leaverequest) {
//           Optional<Employee>  optional = employeeFeignController.getEmployeeByIdpost(leaverequest.getEmployeeid());
//        System.out.println(optional.get());
//        if (!optional.isPresent()) {
//            throw new RuntimeException("Employee not found");
//        }
//       String leavetypee= leaverequest.getLeaveType();
//
//        if (leavetypee.equals("vacationCount")) {
//          int days=  leaverequest.getNumberOfDays();
//          Optional<LeaveType> optionalLeaveType  =leaveTypeController.getleavetypebyid(leaverequest.getEmployeeid());
//          int tablevacationcount=optionalLeaveType.get().getVacationCount();
//          int remainingvacationleaves= tablevacationcount-days;
//
//        } else if (leavetypee.equals("sickLeaveCount"))
//        {
//
//        } else if (leavetypee.equals("remoteWorkCount")) {
//        }else if (leavetypee.equals("personalCount")) {
//        }else if (leavetypee.equals("unpaidCount")) {
//        }else{
//            throw new RuntimeException("please enter the valid leave type");
//        }
//        return service.requestleaveOrsaveleave(leaverequest);
//
//    }

//    @PostMapping("/save")
//    public LeaveRequest requestleaveOrsaveleave(@RequestBody LeaveRequest leaverequest) {
//        // Validate employee exists
//        Optional<Employee> optional = employeeFeignController.getEmployeeByIdpost(leaverequest.getEmployeeid());
//        if (!optional.isPresent()) {
//            throw new RuntimeException("Employee not found");
//        }
//
//        String leaveType = leaverequest.getLeaveType();
//        System.out.println(leaveType);
//        int days = leaverequest.getNumberOfDays();
//        System.out.println(days);
//        // Get current leave balances
//        Optional<LeaveType> optionalLeaveType = leaveTypeController.getleavetypebyid(leaverequest.getEmployeeid());
//        if (!optionalLeaveType.isPresent()) {
//            throw new RuntimeException("Leave balance record not found for employee");
//        }
//
//        LeaveType leaveTypeEntity = optionalLeaveType.get();
//        LeaveType updatedLeaveType = new LeaveType();
////        updatedLeaveType.setId(leaveTypeEntity.getId());
//        updatedLeaveType.setEmpid(leaveTypeEntity.getEmpid());
//
//        // Copy all existing values first
//        updatedLeaveType.setVacationCount(leaveTypeEntity.getVacationCount());
//        updatedLeaveType.setSickLeaveCount(leaveTypeEntity.getSickLeaveCount());
//        updatedLeaveType.setRemoteWorkCount(leaveTypeEntity.getRemoteWorkCount());
//        updatedLeaveType.setPersonalCount(leaveTypeEntity.getPersonalCount());
//        updatedLeaveType.setUnpaidCount(leaveTypeEntity.getUnpaidCount());
//
//        // Update only the relevant leave type
//        switch (leaveType) {
//            case "vacationCount":
//                if (leaveTypeEntity.getVacationCount() < days) {
//                    throw new RuntimeException("Not enough vacation days remaining");
//                }
//
//                updatedLeaveType.setVacationCount(leaveTypeEntity.getVacationCount() - days);
//                break;
//
//            case "sickLeaveCount":
//                if (leaveTypeEntity.getSickLeaveCount() < days) {
//                    throw new RuntimeException("Not enough sick leave days remaining");
//                }
//                updatedLeaveType.setSickLeaveCount(leaveTypeEntity.getSickLeaveCount() - days);
//                break;
//
//            case "remoteWorkCount":
//                if (leaveTypeEntity.getRemoteWorkCount() < days) {
//                    throw new RuntimeException("Not enough remote work days remaining");
//                }
//                updatedLeaveType.setRemoteWorkCount(leaveTypeEntity.getRemoteWorkCount() - days);
//                break;
//
//            case "personalCount":
//                if (leaveTypeEntity.getPersonalCount() < days) {
//                    throw new RuntimeException("Not enough personal days remaining");
//                }
//                updatedLeaveType.setPersonalCount(leaveTypeEntity.getPersonalCount() - days);
//                break;
//
//            case "unpaidCount":
//                // Typically unpaid leave doesn't have a limit, but we'll still track it
//                updatedLeaveType.setUnpaidCount(leaveTypeEntity.getUnpaidCount() + days);
//                break;
//
//            default:
//                throw new RuntimeException("Please enter a valid leave type");
//        }
//
//        // Update the leave type record
//        leaveTypeController.updateLeaveType(updatedLeaveType);
//
//        // Process the leave request
//        return service.requestleaveOrsaveleave(leaverequest);
//    }


    @PostMapping("/save")
    public LeaveRequest requestleaveOrsaveleave(@RequestBody LeaveRequest leaverequest) {
        LeaveType lt=new LeaveType();
        // Validate employee exists
        Optional<Employee> optional = employeeFeignController.getEmployeeByIdpost(leaverequest.getEmployeeid());
        if (optional.isPresent()) {
           int days= leaverequest.getNumberOfDays();

            if ((leaverequest.getLeaveType()).equals("vacationCount")) {
                if (lt.getVacationCount() < days)
                {
                    int remainingcount = days - lt.getVacationCount();
                    System.out.println(remainingcount+" i am vacationcount");
                    lt.setVacationCount(remainingcount);
                } else if ((leaverequest.getLeaveType()).equals("sickLeaveCount")) {
                    if (lt.getSickLeaveCount() < days)
                    {
                        int remainingcount = days - lt.getSickLeaveCount();
                        lt.setVacationCount(remainingcount);
                    }
                } else if ((leaverequest.getLeaveType()).equals("remoteWorkCount")) {
                    if (lt.getRemoteWorkCount() < days)
                    {
                        int remainingcount = days - lt.getRemoteWorkCount();
                        lt.setVacationCount(remainingcount);
                    }
                } else if ((leaverequest.getLeaveType()).equals("personalCount")) {
                    if (lt.getPersonalCount() < days)
                    {
                        int remainingcount = days - lt.getPersonalCount();
                        lt.setVacationCount(remainingcount);
                    }
                } else if ((leaverequest.getLeaveType()).equals("paidCount")) {
                    if (lt.getPaidCount() < days)
                    {
                        int remainingcount = days - lt.getPaidCount();
                        lt.setVacationCount(remainingcount);
                    }
                } else {
                    throw new RuntimeException("Not enough vacation days remaining");

                }

//                updatedLeaveType.setVacationCount(leaveTypeEntity.getVacationCount() - days);
            }

        }else{
            throw new RuntimeException("Employee not found");

        }


        lt.setEmpid(leaverequest.getEmployeeid());
        System.out.println(lt+" i am leavetype");
        // Update the leave type record
        leaveTypeController.updateLeaveType(lt);

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
    public  List<LeaveRequest> findByEmployee(@PathVariable Long employeeId){
        return  service.findByEmployee(employeeId);
    }

    @GetMapping("/test")
    public  Optional<Employee> findByEmployee1(){
        Optional<Employee>   o= employeeFeignController.getEmployeeByIdpost(7L);
        System.out.println(o.get());
        return Optional.of(o.get());
    }


}
