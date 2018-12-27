package com.citystartravel.backend.entity.bus.event;

import com.citystartravel.backend.entity.bus.BusService;
import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.security.CurrentUser;
import com.citystartravel.backend.security.UserPrincipal;
import com.citystartravel.backend.util.Mapper;
import com.citystartravel.backend.util.UtilityMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusEventService {

    @Autowired
    private BusEventRepository busEventRepository;

    @Autowired
    private BusService busService;

    @Autowired
    private Mapper<BusEventRequest, BusEvent> mapperDtoToEntity;

    private static final Logger logger = LoggerFactory.getLogger(BusEventService.class);

    private UtilityMethods<BusEvent> utilityMethods = new UtilityMethods<>();

    public PagedResponse<BusEvent> getAllBusEvents(UserPrincipal currentUser, int page, int size) {
        return utilityMethods.getAll(busEventRepository, currentUser, page, size);
    }

    public List<BusEvent> getEventsForBus(Long busId) {
        return busEventRepository.findByBusId(busId, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public BusEvent getBusEventById(Long busId, @CurrentUser UserPrincipal currentUser) {
        return utilityMethods.getById(busEventRepository,currentUser,busId,"BusEvent");
    }

    public BusEvent createBusEvent(BusEvent busEvent, @CurrentUser UserPrincipal currentUser) {
        return busEventRepository.save(busEvent);
    }

    public boolean addBusEvent(BusEventRequest busEventRequest, @CurrentUser UserPrincipal currentUser) {

        BusEvent busEvent = new BusEvent(busService.getBusByIdEntity(busEventRequest.getBusId(),currentUser),
                                        busEventRequest.getText(),
                                        busEventRequest.getBusCondition(),
                                        busEventRequest.getBusEventType());
        busEvent = busEventRepository.save(busEvent);
        String eventLog = utilityMethods.generateEntityCreationMessage("BusEvent",String.valueOf(busEvent.getId()),currentUser);
        logger.info(eventLog);
        return true;
    }

    public BusEvent updateBusEvent(BusEvent busEvent) {
        return utilityMethods.update(busEventRepository, busEvent);
    }

    public void deleteBus(BusEvent busEvent) {
        utilityMethods.delete(busEventRepository, busEvent);
    }



}
