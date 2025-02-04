package ru.sleepy_sofa.cartridgeproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sleepy_sofa.cartridgeproject.models.Cartridge;
import ru.sleepy_sofa.cartridgeproject.models.Office;
import ru.sleepy_sofa.cartridgeproject.models.Queue;
import ru.sleepy_sofa.cartridgeproject.models.dto.SimplifiedQueueDTO;
import ru.sleepy_sofa.cartridgeproject.repositories.QueueRepository;

import java.util.List;

@Service
public class QueueService {

    @Autowired
    private QueueRepository queueRepository;
    @Autowired
    private CartridgeService cartridgeService;
    @Autowired
    private OfficeService officeService;

    public QueueService(QueueRepository queueRepository, CartridgeService cartridgeService, OfficeService officeService) {
        this.queueRepository = queueRepository;
        this.cartridgeService = cartridgeService;
        this.officeService = officeService;
    }

    public List<SimplifiedQueueDTO> getAllQueue() {
        List<Queue> queues = queueRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
        return queues.stream()
                .map(queue -> new SimplifiedQueueDTO(
                        queue.getId(),
                        queue.getOffice().getName(),
                        queue.getOffice().getId(),
                        queue.getCartridge().getName(),
                        queue.getCartridge().getId()
                ))
                .toList();
    }

    public void addQueue(Long cartridgeId, Long officeId) {
        Cartridge cartridge = cartridgeService.getCartridgeById(cartridgeId);
        Office office = officeService.getOfficeById(officeId);
        queueRepository.save(new Queue(cartridge, office));
    }

    public void deleteQueue(Long cartridgeId, Long officeId) {
        Cartridge cartridge = cartridgeService.getCartridgeById(cartridgeId);
        Office office = officeService.getOfficeById(officeId);
        Queue queue = queueRepository.findByCartridgeAndOffice(cartridge, office).orElseThrow();
        queueRepository.delete(queue);
    }
}
