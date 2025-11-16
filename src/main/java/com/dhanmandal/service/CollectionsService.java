package com.dhanmandal.service;

import com.dhanmandal.dto.Receipt;
import com.dhanmandal.entity.Collections;
import com.dhanmandal.repository.CollectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Collections> collections = collectionsRepository.findAll();
        return collections.stream().map(Receipt::from).collect(Collectors.toList());
    }

}
