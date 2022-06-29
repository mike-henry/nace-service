package com.demo.nace.service;

import com.demo.nace.service.data.NaceRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nace")

public class NaceServiceController {

  private final NaceService service;

  public NaceServiceController(NaceService service) {
    this.service = service;
  }

  @GetMapping("/get-record/{order}")
  @Operation(summary = "Get a NACE Information  by Order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
      @ApiResponse(responseCode = "404", description = "The Record was not found" )
  })
  public NaceRecord getCount(@PathVariable("order") Long order) {
    return service.getRecordByOrder(order);
  }
}
