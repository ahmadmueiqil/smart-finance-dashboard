package com.smartfinance.Controllers;


import com.smartfinance.dto.ApiResponse;
import com.smartfinance.dto.DashboardDto;
import com.smartfinance.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardDto>> getDashboard(Authentication authentication ){

        DashboardDto dashboardDto = dashboardService.getDashboard(authentication);

        return ResponseEntity.ok(new ApiResponse<>("Fetched Successfully",
                dashboardDto,
                true));

    }
}
