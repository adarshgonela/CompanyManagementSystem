export interface LeaveRequest {
  leaveRequestId: number;
  employee: number;  // Assuming employee is a number representing the employee ID
  leaveType: LeaveType;  // You should also define the LeaveType enum based on your backend
  startDate: string;  // LocalDate can be represented as a string (e.g., 'YYYY-MM-DD')
  endDate: string;  // LocalDate can be represented as a string (e.g., 'YYYY-MM-DD')
  numberOfDays: number;
  status: LeaveRequestStatus;  // You should define the LeaveRequestStatus enum
  reason?: string;  // Optional field, can be undefined if not provided
  requestedAt: string;  // LocalDateTime can be represented as a string (e.g., 'YYYY-MM-DDTHH:mm:ss')
  approvedAt?: string;  // Optional field, can be undefined if not approved
  rejectedAt?: string;  // Optional field, can be undefined if not rejected
  approvalStatus?: ApprovalStatus;  // Optional, approval status can be undefined if not set
}

// Define the enums (you should adapt the enums based on your backend code)
export enum LeaveType {
  SICK_LEAVE = 'SICK_LEAVE',
  ANNUAL_LEAVE = 'ANNUAL_LEAVE',
  UNPAID_LEAVE = 'UNPAID_LEAVE',
  // Add other leave types if necessary
}

export enum LeaveRequestStatus {
  PENDING = 'PENDING',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED',
  // Add other statuses if necessary
}

export enum ApprovalStatus {
  MANAGER_APPROVED = 'MANAGER_APPROVED',
  HR_APPROVED = 'HR_APPROVED',
  PENDING = 'PENDING',
  // Add other approval statuses if necessary
}
