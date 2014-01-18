package by.deniotokiari.android.core.contract;

import by.deniotokiari.android.core.annotation.contract.Contract;
import by.deniotokiari.android.core.annotation.field.dbField;
import by.deniotokiari.android.core.annotation.field.dbLong;
import by.deniotokiari.android.core.annotation.field.dbString;

@Contract
public class CoreRequestContract extends CoreContract {

    @dbField
    @dbLong
    public static final String TIME_END_CACHED = "time_end_cached";

    @dbField
    @dbString
    public static final String HASH = "hash";

}
