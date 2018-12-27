package com.citystartravel.backend.entity.bus.event;

import com.citystartravel.backend.payload.response.ApiResponse;
import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.security.CurrentUser;
import com.citystartravel.backend.security.UserPrincipal;
import com.citystartravel.backend.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus/event")
class BusEventController {

    @Autowired
    private BusEventService busEventService;

    private static final Logger logger = LoggerFactory.getLogger(BusEventController.class);

    @GetMapping("/getAll")
    public ResponseEntity<?> getBusEvents(@CurrentUser UserPrincipal currentUser,
                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        try{
            PagedResponse<BusEvent> pagedResponse = busEventService.getAllBusEvents(currentUser, page, size);
            return new ResponseEntity<>(pagedResponse,HttpStatus.OK);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(
                    new ApiResponse(false,"Unable to getAll bus events."),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public BusEvent getBusEvent(@CurrentUser UserPrincipal currentUser,
                                @RequestParam(value = "id") Long id) {
        return busEventService.getBusEventById(id, currentUser);
    }

    @GetMapping("/getEventsForBus")
    public List<BusEvent> getBusEventsForBus(@CurrentUser UserPrincipal currentUser,
                                            @RequestParam(value = "id") Long id) {
        return busEventService.getEventsForBus(id);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBusEvent(@CurrentUser UserPrincipal currentUser,
                                       @RequestBody BusEventRequest busEventRequest) {
        try{

            if(busEventService.addBusEvent(busEventRequest, currentUser))
                return new ResponseEntity<>(
                        new ApiResponse(true,"Bus Event created successfully."),HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(
                    new ApiResponse(false,"Unable to create bus event."),HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(
                    new ApiResponse(false,"Unable to create bus event."),HttpStatus.BAD_REQUEST);
        }

    }

}
