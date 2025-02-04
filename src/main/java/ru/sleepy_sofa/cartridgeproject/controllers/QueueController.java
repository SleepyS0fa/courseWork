package ru.sleepy_sofa.cartridgeproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sleepy_sofa.cartridgeproject.models.Queue;
import ru.sleepy_sofa.cartridgeproject.models.dto.SimplifiedQueueDTO;
import ru.sleepy_sofa.cartridgeproject.responses.DataResponse;
import ru.sleepy_sofa.cartridgeproject.responses.ErrorResponse;
import ru.sleepy_sofa.cartridgeproject.responses.Response;
import ru.sleepy_sofa.cartridgeproject.services.QueueService;

@RestController
@RequestMapping("/api/v1")
public class QueueController {

    @Autowired
    QueueService queueService;

    @GetMapping(value = "/queue", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getQueue() {
        try {
            return new DataResponse<>("success", queueService.getAllQueue());
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @PostMapping(value = "/queue/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addQueueItem(@RequestParam(name = "cartridge_id") Long cartridgeId, @RequestParam(name = "office_id") Long officeId) {
        try {
            queueService.addQueue(cartridgeId, officeId);
            return new Response();
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }

    @PostMapping(value = "/queue/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteQueueItem(@RequestParam(name = "cartridge_id") Long cartridgeId, @RequestParam(name = "office_id") Long officeId) {
        try {
            queueService.deleteQueue(cartridgeId, officeId);
            return new Response();
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage());
        }
    }
}
