package ma.youcode.surveyit.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import ma.youcode.surveyit.dto.request.owner.OwnerCreateDTO;
import ma.youcode.surveyit.dto.request.owner.OwnerUpdateDTO;
import ma.youcode.surveyit.dto.response.owner.OwnerResponseDTO;
import ma.youcode.surveyit.entity.Owner;
import ma.youcode.surveyit.mapper.OwnerMapper;
import ma.youcode.surveyit.repository.OwnerRepository;
import ma.youcode.surveyit.service.interfaces.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImp implements OwnerService {

    @Autowired
    private OwnerRepository repository;
    @Autowired
    private OwnerMapper mapper;

    @Override
    public OwnerResponseDTO createOwner(OwnerCreateDTO dto) {
        Owner owner = mapper.toOwner(dto);
        repository.save(owner);
        return mapper.toResponseDTO(owner);
    }

    @Override
    public OwnerResponseDTO editOwner(OwnerUpdateDTO dto , Long id) {
        Owner owner = mapper.toOwner(dto);
        owner.setId(id);
        repository.save(owner);
        return mapper.toResponseDTO(owner);

    }

    @Override
    public void deleteOwner(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<OwnerResponseDTO> getAllOwners(int page , int size) {

        Pageable pageable = PageRequest.of(page , size);
        Page<Owner> ownerPage = repository.findAll(pageable);

        return ownerPage.map(mapper::toResponseDTO);

    }

    @Override
    public OwnerResponseDTO getOwner(Long id) {
        return mapper.toResponseDTO(repository.findById(id).get());
    }

    @Override
    public Owner getOwnerEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Owner not found."));
    }
}
