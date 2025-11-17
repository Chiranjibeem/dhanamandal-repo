package com.dhanmandal.service;

import com.dhanmandal.dto.Collection;
import com.dhanmandal.dto.Receipt;
import com.dhanmandal.entity.Collections;
import com.dhanmandal.repository.CollectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CollectionsService {

    @Autowired
    private CollectionsRepository collectionsRepository;

    public Receipt collectMoney(Receipt receipt) {
        Collections collections = collectionsRepository.save(Collections.from(receipt));
        return Receipt.from(collections);
    }

    public List<Receipt> getCollections(){
        List<Collections> collections = collectionsRepository.findAll(Sort.by(Sort.Direction.DESC, "collectionDate"));
        return collections.stream().map(Receipt::from).collect(Collectors.toList());
    }

    public Receipt findById(String id) {
        Collections collections = collectionsRepository.findById(id).get();
        return Receipt.from(collections);
    }

    public void deleteById(String id) {
        collectionsRepository.deleteById(id);
    }

    public List<Receipt> searchByName(String name){
        List<Collections> collections = collectionsRepository.findByNameContainingIgnoreCase(name);
        return collections.stream().map(Receipt::from).collect(Collectors.toList());
    }
}
