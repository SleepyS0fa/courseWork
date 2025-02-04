package ru.sleepy_sofa.cartridgeproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sleepy_sofa.cartridgeproject.models.Office;
import ru.sleepy_sofa.cartridgeproject.responses.DataResponse;
import ru.sleepy_sofa.cartridgeproject.responses.ErrorResponse;
import ru.sleepy_sofa.cartridgeproject.responses.Response;
import ru.sleepy_sofa.cartridgeproject.services.OfficeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OfficeController {
    @Autowired
    private OfficeService officeService;

    @GetMapping(value = "/offices", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getOffices() {
        Response response;
        try {
            response = new DataResponse<Office>("success", officeService.getOffices());
        } catch (Exception e) {
            response = new ErrorResponse(e.getMessage());
        }
        return response;
    }

    @PostMapping(value = "/offices", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addOffices(@RequestParam(name = "name") List<String> names) {
        Response response;
        try {
            officeService.addOffice(names);
            response = new Response("success");
        } catch (Exception e) {
            response = new ErrorResponse(e.getMessage());
        }
        return response;
    }

    @PostMapping(value = "/offices/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteOffices(@RequestParam(name = "office_id") List<Long> ids) {
        Response response;
        try {
            officeService.deleteOffice(ids);
            return new Response("success");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }
}
