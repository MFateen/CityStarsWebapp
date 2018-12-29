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

    private static final Logger logger = LoggerFactory.getLogger(SpareTypeService.class);

    private UtilityMethods<SpareType> utilityMethods = new UtilityMethods<>();

    @Autowired
    private Mapper<SpareType, SpareTypeDtoResponse> mapper;

    /*public PagedResponse<SpareTypeDtoResponse> getAllSpareTypes(UserPrincipal currentUser, int page, int size) {
        return mapSpareTypePagesToDtoPages(utilityMethods.getAll(spareTypeRepository,currentUser,page,size));

    }*/

    public PagedResponse<SpareType> getAllSpareTypes(UserPrincipal currentUser, int page, int size) {
        return utilityMethods.getAll(spareTypeRepository,currentUser,page,size);

    }

    public SpareType getSpareTypeById(Long spareTypeId, @CurrentUser UserPrincipal currentUser) {
        return utilityMethods.getById(spareTypeRepository, currentUser, spareTypeId,"SpareType");
    }

    public SpareTypeDtoResponse getSpareTypeDtoById(Long spareTypeId, @CurrentUser UserPrincipal currentUser) {
        SpareType spareType = utilityMethods.getById(spareTypeRepository, currentUser, spareTypeId,"SpareType");
        return mapper.mapEntityToDto(spareType, SpareTypeDtoResponse.class);
    }

    public SpareType createSpareType(SpareTypeRequest spareTypeRequest) {
        SpareType spareType = new SpareType();
        spareType.setName(spareTypeRequest.getName());
        logger.info("[CREATED] SpareType "+spareType.getName()+" Created.");
        return spareTypeRepository.save(spareType);
    }

    // ---------------------------------- util ----------------------------------
    private PagedResponse<SpareTypeDtoResponse> mapSpareTypePagesToDtoPages(PagedResponse<SpareType> spareTypePagedResponse) {
        return mapper.mapEntityPagesToDtoPages(spareTypePagedResponse, SpareTypeDtoResponse.class);
    }
}
