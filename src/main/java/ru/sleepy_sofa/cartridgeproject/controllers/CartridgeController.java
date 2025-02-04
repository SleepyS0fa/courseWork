package ru.sleepy_sofa.cartridgeproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.State;
import ru.sleepy_sofa.cartridgeproject.models.Cartridge;
import ru.sleepy_sofa.cartridgeproject.responses.DataResponse;
import ru.sleepy_sofa.cartridgeproject.responses.ErrorResponse;
import ru.sleepy_sofa.cartridgeproject.responses.Response;
import ru.sleepy_sofa.cartridgeproject.services.CartridgeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CartridgeController {
    @Autowired
    CartridgeService cartridgeService;

    @GetMapping(value = "/cartridges", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getCartridges(@RequestParam(required = false) State state, @RequestParam(required = false) String unique) {
        Response response;
        try {
            List<Cartridge> data;
            if (state != null)
                data = cartridgeService.getCartridgeByState(state);
            else
                data = cartridgeService.getAllCartridges();
            if (unique != null && !unique.isEmpty())
                data = cartridgeService.distinct(data);
            response = new DataResponse<Cartridge>("success", data);
        } catch (Exception e) {
            response = new ErrorResponse(e.getMessage());
        }
        return response;
    }

    @PostMapping(value = "/cartridges", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addCartridges(@RequestParam(name = "name") List<String> names) {
        try {
            cartridgeService.addCartridge(names);
            return new DataResponse<Cartridge>("success", null);
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/cartridges/delete")
    public Response deleteCartridges(@RequestParam(name = "cartridge_id") List<Long> ids) {
        try {
            cartridgeService.deleteCartridge(ids);
            return new Response();
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/cartridges/installed")
    public Response installCartridges(@RequestParam(name = "cartridge_id") Long cartridgeId, @RequestParam(name = "office_id") Long officeId) {
        try {
            boolean isSuccess = cartridgeService.installCartridge(cartridgeId, officeId);
            return isSuccess ? new Response() : new ErrorResponse("Переход запрещен");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/cartridges/deinstalled")
    public Response uninstallCartridges(@RequestParam(name = "cartridge_id") Long cartridgeId, @RequestParam(name = "office_id") Long officeId) {
        try {
            boolean isSuccess = cartridgeService.uninstallCartridge(cartridgeId, officeId);
            return isSuccess ? new Response() : new ErrorResponse("Переход запрещен");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/cartridges/sendToRefuel")
    public Response sendToRefuelCartridges(@RequestParam(name = "cartridge_id") Long cartridgeId) {
        try {
            boolean isSuccess = cartridgeService.sendToRefuel(cartridgeId);
            return isSuccess ? new Response() : new ErrorResponse("error", "Переход запрещен");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @PostMapping("/cartridges/returnFromRefuel")
    public Response returnFromRefuelCartridges(@RequestParam(name = "cartridge_id") Long cartridgeId) {
        try {
            boolean isSuccess = cartridgeService.returnFromRefuel(cartridgeId);
            return isSuccess ? new Response() : new ErrorResponse("error", "Переход запрещен");

        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }
}
