package com.portfolio.web.apis;

import com.portfolio.domain.common.PhoneCaseRegisterCommand;
import com.portfolio.domain.common.PhoneCaseSearchCommand;
import com.portfolio.domain.common.ProductSearchCommand;
import com.portfolio.domain.common.response.SimpleResponseData;
import com.portfolio.domain.model.product.phonecase.PhoneCaseService;
import com.portfolio.web.payload.PhoneCaseRegisterPayload;
import com.portfolio.web.results.ApiResult;
import com.portfolio.web.results.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PhoneCaseApi extends AbstractBaseController {

    private final PhoneCaseService phoneCaseService;

    @GetMapping("/api/public/case")
    public ResponseEntity<ApiResult> getProducts(
            Pageable pageable, HttpServletRequest request, String type) {
        ProductSearchCommand command = ProductSearchCommand.builder()
                .pageable(pageable)
                .type(type)
                .build();
        addTriggeredBy(command, request);
        phoneCaseService.findAll(command);
        return Result.ok();
    }

    @PostMapping("/api/admin/case")
    public ResponseEntity<ApiResult> registerPhoneCase(
            @RequestBody PhoneCaseRegisterPayload payload,
            HttpServletRequest request) {
        try {
            PhoneCaseRegisterCommand command = payload.toCommand();
            addTriggeredBy(command, request);
            Long id = phoneCaseService.registerByAdmin(command);
            return Result.ok(String.valueOf(id));
        } catch (Exception e) {
            log.error("Unexpected error at POST /api/admin/case");
            return Result.failure(e.getMessage());
        }
    }

    @PutMapping("/api/admin/case/{id}/on-sale")
    public ResponseEntity<ApiResult> updateSaleStatus(
            @PathVariable Long id, @RequestParam boolean sale, HttpServletRequest request) {
        try {
            PhoneCaseSearchCommand command = PhoneCaseSearchCommand.builder()
                    .sale(sale)
                    .id(id)
                    .build();
            addTriggeredBy(command,request);
            SimpleResponseData responseData = phoneCaseService.updateSaleStatus(command);
            return Result.ok(ApiResult.data(responseData));
        } catch (Exception e) {
            log.error("Unexpected error at POST /api/admin/case/{id}/on-sale id = {}", id);
            return Result.failure(e.getMessage());
        }

    }

}
