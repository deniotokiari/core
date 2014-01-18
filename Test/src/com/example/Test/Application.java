package com.example.Test;

import by.deniotokiari.android.core.CoreApplication;

public class Application extends CoreApplication {

    @Override
    public void registerServices() {
        registerService(new TestSource());
        registerService(new TestProcessor());
    }

}
