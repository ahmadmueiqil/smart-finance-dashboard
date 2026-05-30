package com.smartfinance.Controllers;


import com.smartfinance.dto.ApiResponse;
import com.smartfinance.dto.BillDto;
import com.smartfinance.dto.CreateBillRequest;
import com.smartfinance.dto.BillPaymentRequest;
import com.smartfinance.entities.Bill;
import com.smartfinance.services.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;


    @PostMapping("/bill")
    public ResponseEntity<ApiResponse<Object>> createBill(@Valid @RequestBody CreateBillRequest request,
                                                          Authentication authentication){

        billService.createBill(request,
                authentication);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                "Bill created successfuly",
                                null,
                                true
                        )
                );

    }


    @GetMapping("/bills")
    public ResponseEntity<ApiResponse<Page<BillDto>>> getAllBills(Authentication authentication,
                                                                  @RequestParam(required = false) Bill.Status status,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size){

        Page<BillDto> bills = billService.getAllBills(authentication, status, page, size);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Bills Fetched",
                        bills,
                        true
                )
        );
    }

    @PostMapping("/bill/payments")
    public ResponseEntity<ApiResponse<Object>> billPayment(@Valid @RequestBody BillPaymentRequest request,
                                                           Authentication authentication){

        billService.billPayment(request,
                authentication);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Bill Payed Successfuly",
                        null,
                        true
                )
        );
    }

}
