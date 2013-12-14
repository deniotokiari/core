package by.deniotokiari.core.service;

import by.deniotokiari.core.annotations.ContractInfo;
import by.deniotokiari.core.annotations.db.DBContract;
import by.deniotokiari.core.annotations.db.DBTableName;
import by.deniotokiari.core.annotations.db.types.DBLong;
import by.deniotokiari.core.content.CoreContract;

@DBContract
@DBTableName(tableName = "REQUEST")
@ContractInfo(type = "vnd.android.cursor.dir/REQUEST", uri = "")  // TODO set uri
public class RequestContract implements CoreContract {

    @DBLong
    public static final String REQUEST_HASH = "request_hash";

    @DBLong
    public static final String START_TIME = "start_time";

    @DBLong
    public static final String END_TIME = "end_time";

}
