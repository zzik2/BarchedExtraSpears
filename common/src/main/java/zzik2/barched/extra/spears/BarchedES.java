package zzik2.barched.extra.spears;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zzik2.barched.extra.spears.registry.RegisterFactory;

public final class BarchedES {

    public static final String MOD_ID = "barched_extra_spears";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        RegisterFactory.init();
    }
}
