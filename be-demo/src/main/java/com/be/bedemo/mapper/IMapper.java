package com.be.bedemo.mapper;

public interface IMapper<S, T> {
    T createFrom(S source);
}
