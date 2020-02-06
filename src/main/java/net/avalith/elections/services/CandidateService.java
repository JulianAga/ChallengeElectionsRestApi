package net.avalith.elections.services;

import net.avalith.elections.models.Candidate;
import net.avalith.elections.repositories.ICandidateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class CandidateService {

    @Autowired
    ICandidateDao iCandidateDao;

    @Transactional
    public void insert(Candidate candidate)
    {
        iCandidateDao.save(candidate);
    }

    @Transactional
    public List<Candidate> getAll()
    {
        return iCandidateDao.findAll();
    }

    @Transactional
    public void delete(Long id)
    {
        iCandidateDao.deleteById(id);
    }

    @Transactional
    public Optional<Candidate> findOne(Long id)
    {
        try
        {
            return iCandidateDao.findById(id);
        }	catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Provide correct election Id", ex);
        }

    }
    
}
