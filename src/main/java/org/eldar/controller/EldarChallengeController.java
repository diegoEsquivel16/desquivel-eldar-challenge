package org.eldar.controller;

import org.eldar.exception.InvalidOperationException;
import org.eldar.model.Brand;
import org.eldar.model.ErrorResponse;
import org.eldar.model.InterestRateResponse;
import org.eldar.service.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping("/eldar_challenge")
public class EldarChallengeController {

    private final CreditCardService creditCardService;

    @Autowired
    public EldarChallengeController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("/interest_rate")
    public ResponseEntity<?> calculateInterestRateForOperation(
            @RequestParam("operation_value") Integer operationValue, @RequestParam("brand") String brandParam) {
        Brand brand;
        try {
            brand = Brand.valueOf(brandParam.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return new ResponseEntity<>(new ErrorResponse("Invalid Brand: " + brandParam), HttpStatus.BAD_REQUEST);
        }
        Double interestRate;
        try {
            interestRate = creditCardService.calculateInterestRateForOperation(operationValue, brand);
        } catch (InvalidOperationException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new InterestRateResponse(interestRate));
    }
}
