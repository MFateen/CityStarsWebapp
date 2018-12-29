package com.citystartravel.backend.entity.sparetype;

import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.security.CurrentUser;
import com.citystartravel.backend.security.UserPrincipal;
import com.citystartravel.backend.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sparetype")
public class SpareTypeController {
    
    @Autowired
    private SpareTypeService spareTypeService;

    private static final Logger logger = LoggerFactory.getLogger(SpareTypeController.class);

    @GetMapping(value = "/getAll")
    public PagedResponse<SpareType> getSpareTypees(@CurrentUser UserPrincipal currentUser,
                                                   @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return spareTypeService.getAllSpareTypes(currentUser, page, size);
    }

    @GetMapping("/get")
    public SpareType getSpareType(@CurrentUser UserPrincipal currentUser,
                      @RequestParam(value = "spareTypeID") Long id) {
        return spareTypeService.getSpareTypeById(id, currentUser);
    }

    @PostMapping("/create")
    public SpareType createSpareType(@CurrentUser UserPrincipal currentUser,
                         @RequestBody SpareTypeRequest spareTypeRequest) {
        return spareTypeService.createSpareType(spareTypeRequest);
    }
}
