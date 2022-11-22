package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import ch.zli.m223.model.Entry;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;

    @Transactional
    public Entry createEntry(Entry entry) {
        entityManager.persist(entry);
        return entry;
    }

    public List<Entry> findAll() {
        TypedQuery<Entry> query = entityManager.createQuery("FROM Entry", Entry.class);
        return query.getResultList();
    }

    public Entry getEntry(Long id) {
        Entry entry = entityManager.find(Entry.class, id);
        return entry;
    }

    @Transactional
    public void deleteEntry(Long id) {
        Entry entry = entityManager.find(Entry.class, id);
        entityManager.remove(entry);
    }

    @Transactional
    public Entry updaEntry(Entry entry) {
        return entityManager.merge(entry);
    }
}
