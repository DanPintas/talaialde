package es.dasaur.talaialde.management.tractors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TractorServiceImpl 
        implements TractorService{
    
    @Autowired
    private JpaTractorRepository repo;
    
    @Override
    public List<JpaTractor> findAllTractors() {
        return repo.findByDeletedFalse();
    }
    
    @Override
    @Transactional(readOnly = false)
    public JpaTractor saveTractor(JpaTractor c) {
        return repo.save(c);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeTractor(JpaTractor c) {
        c.setDeleted(true);
        repo.save(c);
    }

}
