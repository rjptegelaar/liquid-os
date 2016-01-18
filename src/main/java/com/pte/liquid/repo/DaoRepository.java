package com.pte.liquid.repo;

import java.io.Serializable;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DaoRepository<T, K extends Serializable> extends PagingAndSortingRepository<T, K>,  QueryDslPredicateExecutor<T>{

}
