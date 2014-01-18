package com.example.Test;

import by.deniotokiari.android.core.provider.CoreProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Provider extends CoreProvider {

    private Class<?>[] mContracts = {
            TestEntity.class
    };

    @Override
    public List<Class<?>> getContracts() {
        return Arrays.asList(mContracts);
    }

}
