package net.avalith.elections.services;

import net.avalith.elections.models.Election;
import net.avalith.elections.repositories.IElectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class ElectionService {

    @Autowired
    IElectionDao iElectionDao;

    @Transactional
    public void insert(Election election)
    {
        iElectionDao.save(election);
    }

    @Transactional
    public List<Election> getAll()
    {
        return iElectionDao.findAll();
    }

    @Transactional
    public void delete(Long id)
    {
        iElectionDao.deleteById(id);
    }

    @Transactional
    public Optional<Election> findOne(Long id)
    {
        try
        {
            return iElectionDao.findById(id);
        }	catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Provide correct election Id", ex);
        }

    }
}
