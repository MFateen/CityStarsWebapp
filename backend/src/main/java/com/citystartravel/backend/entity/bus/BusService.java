package com.citystartravel.backend.entity.bus;


import com.citystartravel.backend.entity.bus.event.BusEvent;
import com.citystartravel.backend.entity.bus.event.BusEventService;
import com.citystartravel.backend.entity.bus.event.BusEventType;
import com.citystartravel.backend.entity.spare.Spare;
import com.citystartravel.backend.entity.spare.SpareService;
import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.security.CurrentUser;
import com.citystartravel.backend.security.UserPrincipal;
import com.citystartravel.backend.util.AppConstants;
import com.citystartravel.backend.util.Mapper;
import com.citystartravel.backend.util.UtilityMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusEventService busEventService;

    @Autowired
    private SpareService spareService;

    @Autowired
    private Mapper<Bus, BusResponse> mapper;

    private static final Logger logger = LoggerFactory.getLogger(BusService.class);

    private UtilityMethods<Bus> utilityMethods = new UtilityMethods<>();

    // ---------------------------------- DTO CRUD ----------------------------------

    public PagedResponse<BusResponse> getAllBuses(UserPrincipal currentUser, int page, int size) {
        return mapBusPagesToDtoPages(utilityMethods.getAll(busRepository, currentUser, page, size));
    }

    public BusResponse getBusById(Long busId, @CurrentUser UserPrincipal currentUser) {
        return mapBusToDto(utilityMethods.getById(busRepository,currentUser,busId,"Bus"));
    }

    public BusResponse createBus(BusRequest busRequest, @CurrentUser UserPrincipal currentUser) {
        Bus bus = new Bus(busRequest.getName(),
                          busRequest.getPlatesLetters(),
                          busRequest.getPlatesNumbers(),
                          busRequest.getKm());
        String eventLog = utilityMethods.generateEntityCreationMessage("Bus",bus.getName(),currentUser);
        bus.addBusEvent(new BusEvent(BusEventType.GENERAL, AppConstants.NEW_BUS));
        BusResponse busResponse = mapBusToDto(busRepository.save(bus));
        logger.info(eventLog);
        return busResponse;
    }

    // ---------------------------------- events ----------------------------------
    public List<BusEvent> getBusEvents(Long id) {
        return busEventService.getEventsForBus(id);
    }

    // ---------------------------------- spares ----------------------------------
    public List<Spare> getBusSpares(Long id, @CurrentUser UserPrincipal currentUser) {
        return spareService.getSparesForBus(id, currentUser);
    }

    // ---------------------------------- Entity CRUD ----------------------------------

    public List<Bus> getAllBusesEntity(UserPrincipal currentUser, int page, int size) {
        return busRepository.findAll();
    }

    public Bus getBusByIdEntity(Long busId, @CurrentUser UserPrincipal currentUser) {
        return utilityMethods.getById(busRepository,currentUser,busId,"Bus");
    }


    public Bus updateBus(Bus bus) {
        return utilityMethods.update(busRepository, bus);
    }

    public void deleteBus(long id) {
        Bus bus = busRepository.getOne(id);
        utilityMethods.delete(busRepository, bus);
    }

    // ---------------------------------- util ----------------------------------

    private BusResponse mapBusToDto(Bus bus) {
        return mapper.mapEntityToDto(bus, BusResponse.class);
    }

    private PagedResponse<BusResponse> mapBusPagesToDtoPages(PagedResponse<Bus> busPagedResponse) {
        return mapper.mapEntityPagesToDtoPages(busPagedResponse, BusResponse.class);
    }

    public boolean addBusEvents(Bus bus, BusEvent busEvent, @CurrentUser UserPrincipal currentUser) {
        try{
            bus.addBusEvent(busEvent);
            busRepository.save(bus);
            return true;
        }
        catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }
}
