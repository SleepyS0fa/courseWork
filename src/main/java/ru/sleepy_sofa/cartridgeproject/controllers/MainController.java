package ru.sleepy_sofa.cartridgeproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.State;
import ru.sleepy_sofa.cartridgeproject.responses.DataResponse;
import ru.sleepy_sofa.cartridgeproject.responses.ErrorResponse;
import ru.sleepy_sofa.cartridgeproject.responses.Response;
import ru.sleepy_sofa.cartridgeproject.services.CartridgeService;
import ru.sleepy_sofa.cartridgeproject.services.OfficeService;
import ru.sleepy_sofa.cartridgeproject.services.QueueService;

import java.util.Arrays;

@RestController
public class MainController {
    @Autowired
    private CartridgeService cartridgeService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private QueueService queueService;

    @Autowired
    public MainController(CartridgeService cartridgeService, OfficeService officeService, QueueService queueService) {
        this.cartridgeService = cartridgeService;
        this.officeService = officeService;
        this.queueService = queueService;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public Resource index() {
        return new ClassPathResource("static/html/index.html");
    }

    @GetMapping(value = "/template", produces = MediaType.TEXT_HTML_VALUE)
    public Resource getTemplate() {
        return new ClassPathResource("templates/index.hbs");
    }

    @GetMapping(value = "/template/{param}", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    public Response getTemplateByParam(@PathVariable String param) {
        Response response = new ErrorResponse();
        try {
            switch (param) {
                case "ready_cartridges" ->
                        response = new DataResponse<>(cartridgeService.getCartridgeByState(State.READY));
                case "installed_cartridges" ->
                        response = new DataResponse<>(cartridgeService.getCartridgeByState(State.INSTALLED));
                case "empty_cartridges" ->
                        response = new DataResponse<>(cartridgeService.getCartridgeByState(State.EMPTY));
                case "refuel_cartridges" ->
                        response = new DataResponse<>(cartridgeService.getCartridgeByState(State.REFUELING));
                case "all_offices" -> response = new DataResponse<>(officeService.getOffices());
                case "not_installed_cartridges" ->
                        response = new DataResponse<>(cartridgeService.getCartridgeByState(Arrays.asList(State.READY, State.EMPTY)));
                case "queue_cartridges" -> response = new DataResponse<>(queueService.getAllQueue());
                case "possible_queue_cartridges" ->
                        response = new DataResponse<>(cartridgeService.getCartridgeByState(Arrays.asList(State.EMPTY, State.REFUELING, State.REFUELING)));
            }
            return response;
        } catch (Exception e) {
            return response;
        }
    }
}
