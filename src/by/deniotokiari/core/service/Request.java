package by.deniotokiari.core.service;

import android.content.ContentValues;
import by.deniotokiari.core.utils.HashUtils;

public class Request {

    private String mProcessor;
    private String mSource;
    private String mUri;
    private long mCacheExpiration;

    private boolean mIsNeedCache;
    private boolean mIsForceUpdate;

    public Request(Builder builder) {
        mProcessor = builder.mProcessor;
        mSource = builder.mSource;
        mUri = builder.mUri;
        mCacheExpiration = builder.mCacheExpiration;
        mIsNeedCache = builder.mIsNeedCache;
        mIsForceUpdate = builder.mIsForceUpdate;
    }

    private String getHash() {
        return HashUtils.stringToMD5(mProcessor + mSource + mUri);
    }

    public ContentValues getRequestValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.p
    }

    public static class Builder {

        private String mProcessor;
        private String mSource;
        private String mUri;
        private long mCacheExpiration;

        private boolean mIsNeedCache = false;
        private boolean mIsForceUpdate = true;

        public void setProcessor(String processor) {
            mProcessor = processor;
        }

        public void setSource(String source) {
            mSource = source;
        }

        public void setUri(String uri) {
            mUri = uri;
        }

        public void setCacheExpiration(long cacheExpiration) {
            mCacheExpiration = cacheExpiration;
        }

        public void setIsNeedCache(boolean isNeedCache) {
            isNeedCache = isNeedCache;
        }

        public void setIsForceUpdate(boolean isForceUpdate) {
            mIsForceUpdate = isForceUpdate;
        }

        public Request build() {
            return new Request(this);
        }

    }

}
