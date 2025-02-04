package ru.sleepy_sofa.cartridgeproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sleepy_sofa.cartridgeproject.fsm.StateMachine;
import ru.sleepy_sofa.cartridgeproject.fsm.StateMachineFactory;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.Event;
import ru.sleepy_sofa.cartridgeproject.fsm.enums.State;
import ru.sleepy_sofa.cartridgeproject.models.Cartridge;
import ru.sleepy_sofa.cartridgeproject.models.Office;
import ru.sleepy_sofa.cartridgeproject.repositories.CartridgeRepository;
import ru.sleepy_sofa.cartridgeproject.repositories.OfficeRepository;
import ru.sleepy_sofa.cartridgeproject.repositories.StateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CartridgeService {
    @Autowired
    private final CartridgeRepository cartridgeRepository;
    @Autowired
    private final StateRepository stateRepository;
    @Autowired
    private final OfficeRepository officeRepository;
    @Autowired
    private StateMachineFactory<State, Event> stateMachineFactory;

    @Autowired
    public CartridgeService(CartridgeRepository cartridgeRepository,
                            StateRepository stateRepository,
                            OfficeRepository officeRepository,
                            StateMachineFactory<State, Event> stateMachineFactory) {
        this.cartridgeRepository = cartridgeRepository;
        this.stateRepository = stateRepository;
        this.officeRepository = officeRepository;
        this.stateMachineFactory = stateMachineFactory;
    }

    public List<Cartridge> getAllCartridges() {
        return cartridgeRepository.findAll();
    }

    public Cartridge getCartridgeById(Long id) {
        return cartridgeRepository.findById(id).orElseThrow();
    }

    public void addCartridge(List<String> names) {
        List<Cartridge> cartridges = names.stream()
                .map(name -> {
                    Cartridge cartridge = new Cartridge();
                    cartridge.setName(name);
                    cartridge.setState(State.READY);
                    cartridge.setStateMachine(stateMachineFactory.create());
                    return cartridge;
                })
                .collect(Collectors.toList());
        cartridgeRepository.saveAll(cartridges);
    }

    public void deleteCartridge(List<Long> ids) {
        List<Cartridge> cartridges = ids.stream()
                .map(this::getCartridgeById)
                .collect(Collectors.toList());
        cartridgeRepository.deleteAll(cartridges);
    }

    public boolean triggerEvent(Long id, Event event) {
        Cartridge cartridge = cartridgeRepository.findById(id).orElseThrow();
        StateMachine<State, Event> stateMachine = stateMachineFactory.createWithState(cartridge.getState());
        cartridge.setStateMachine(stateMachine);
        if (cartridge.sendEvent(event)) {
            cartridgeRepository.save(cartridge);
            return true;
        }
        return false;
    }

    public List<Cartridge> getCartridgeByState(State state) {
        return stateRepository.findByState(state);
    }

    public List<Cartridge> getCartridgeByState(List<State> state) {
        return state.stream()
                .flatMap(st -> getCartridgeByState(st).stream())
                .collect(Collectors.toList());
    }

    public List<Cartridge> distinct(List<Cartridge> list) {
        return new ArrayList<>(list.stream()
                .collect(Collectors.toMap(
                        Cartridge::getName,
                        Cartridge -> Cartridge,
                        (exist, replace) -> replace))
                .values());
    }

    public boolean installCartridge(Long cartridgeId, Long officeId) {
        Cartridge cartridge = cartridgeRepository.findById(cartridgeId).orElseThrow();
        cartridge.setStateMachine(stateMachineFactory.create());
        Office office = officeRepository.findById(officeId).orElseThrow();

        if (triggerEvent(cartridgeId, Event.INSTALL)) {
            cartridge.setOffice(office);
            cartridgeRepository.save(cartridge);
            return true;
        }
        return false;
    }

    public boolean uninstallCartridge(Long cartridgeId, Long officeId) {
        Cartridge cartridge = cartridgeRepository.findById(cartridgeId).orElseThrow();
        Office office = officeRepository.findById(officeId).orElseThrow();
        if (!cartridge.getOffice().equals(office)) {
            Cartridge targetCartridge = cartridge;
            cartridge = office.getCartridges().stream()
                    .filter(item -> item.getName().equals(targetCartridge.getName()))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Таких картриджей в кабинете не установлено"));
        }
        if (triggerEvent(cartridgeId, Event.UNINSTALL)) {
            cartridge.setOffice(null);
            cartridgeRepository.save(cartridge);
            return true;
        }
        return false;
    }

    public boolean sendToRefuel(Long cartridgeId) {
        return triggerEvent(cartridgeId, Event.REFUEL);
    }

    public boolean returnFromRefuel(Long cartridgeId) {
        return triggerEvent(cartridgeId, Event.RETURN);
    }

}
