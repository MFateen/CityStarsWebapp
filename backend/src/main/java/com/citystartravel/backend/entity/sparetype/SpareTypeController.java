package com.citystartravel.backend.entity.sparetype;

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

@RestController
@RequestMapping("/api/sparetype")
public class SpareTypeController {
    
    @Autowired
    private SpareTypeService spareTypeService;

    private static final Logger logger = LoggerFactory.getLogger(SpareTypeController.class);

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getSpareTypes(@CurrentUser UserPrincipal currentUser,
                                            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        try{
            PagedResponse<SpareTypeDto> spareTypes = spareTypeService.getAllSpareTypes(currentUser, page, size);
            return ResponseEntity.ok(spareTypes);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    new ApiResponse(false,"Unable to fetch Spare Types."),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public SpareType getSpareType(@CurrentUser UserPrincipal currentUser,
                      @RequestParam(value = "spareTypeID") Long id) {
        return spareTypeService.getSpareTypeById(id, currentUser);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSpareType(@CurrentUser UserPrincipal currentUser,
                         @RequestBody SpareTypeDto spareTypeDto) {
        try{
            SpareType spareType = spareTypeService.createSpareType(spareTypeDto);
            return ResponseEntity.ok(spareType);
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity<>(
                    new ApiResponse(false,"Unable to create spare type."),HttpStatus.BAD_REQUEST);
        }
    }
}
