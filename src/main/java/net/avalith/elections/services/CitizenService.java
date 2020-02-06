package net.avalith.elections.services;

import net.avalith.elections.models.Citizen;
import net.avalith.elections.repositories.ICitizenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class CitizenService {

    @Autowired
    ICitizenDao iCitizenDao;

    @Transactional
    public void insert(Citizen citizen)
    {
        iCitizenDao.save(citizen);
    }

    @Transactional
    public List<Citizen> getAll()
    {
        return iCitizenDao.findAll();
    }

    @Transactional
    public void delete(Long id)
    {
        iCitizenDao.deleteById(id);
    }

    @Transactional
    public Optional<Citizen> findOne(Long id)
    {
        try
        {
            return iCitizenDao.findById(id);
        }	catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Provide correct election Id", ex);
        }

    }
}
