package com.citystartravel.backend.entity.sparetype;

import com.citystartravel.backend.payload.response.PagedResponse;
import com.citystartravel.backend.security.CurrentUser;
import com.citystartravel.backend.security.UserPrincipal;
import com.citystartravel.backend.util.Mapper;
import com.citystartravel.backend.util.UtilityMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpareTypeService {

    @Autowired
    private SpareTypeRepository spareTypeRepository;

    @Autowired
    private Mapper<SpareType, SpareTypeDto> mapper;

    private static final Logger logger = LoggerFactory.getLogger(SpareTypeService.class);

    private UtilityMethods<SpareType> utilityMethods = new UtilityMethods<>();

    public PagedResponse<SpareTypeDto> getAllSpareTypes(UserPrincipal currentUser, int page, int size) {
        PagedResponse<SpareType> pagedResponse = utilityMethods.getAll(spareTypeRepository,currentUser,page,size);
        return mapper.mapEntityPagesToDtoPages(pagedResponse,SpareTypeDto.class);
    }

    public SpareType getSpareTypeById(Long spareTypeId, @CurrentUser UserPrincipal currentUser) {
        return utilityMethods.getById(spareTypeRepository, currentUser, spareTypeId,"SpareType");
    }

    public SpareType createSpareType(SpareTypeDto spareTypeDto) {
        SpareType spareType = new SpareType();
        spareType.setName(spareTypeDto.getName());
        spareType = spareTypeRepository.save(spareType);
        logger.info("[CREATED] SpareType "+spareType.getName()+" Created.");
        return spareType;
    }

    // ---------------------------------- util ----------------------------------

}
