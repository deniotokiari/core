package by.deniotokiari.core.service;

import by.deniotokiari.core.annotations.ContractInfo;
import by.deniotokiari.core.annotations.db.DBContract;
import by.deniotokiari.core.annotations.db.DBTableName;
import by.deniotokiari.core.annotations.db.types.DBLong;
import by.deniotokiari.core.annotations.db.types.DBVarchar;
import by.deniotokiari.core.content.CoreContract;

@DBContract
@DBTableName(tableName = "REQUEST")
@ContractInfo(type = "vnd.android.cursor.dir/REQUEST",
        uri = "content://by.deniotokiari.core.content.CoreProvider/REQUEST")
public class RequestContract implements CoreContract {

    @DBVarchar
    public String requestHash;

    @DBLong
    public Long endTime;

    @DBVarchar
    public String processor;

    @DBVarchar
    public String source;

    @DBVarchar
    public String uri;

    public String getRequestHash() {
        return requestHash;
    }

    public Long getEndTime() {
        return endTime;
    }

    public String getProcessor() {
        return processor;
    }

    public String getSource() {
        return source;
    }

    public String getUri() {
        return uri;
    }

    public void setRequestHash(String requestHash) {
        this.requestHash = requestHash;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
