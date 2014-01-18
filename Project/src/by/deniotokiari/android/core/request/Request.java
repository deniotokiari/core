package by.deniotokiari.android.core.request;

import by.deniotokiari.android.core.utils.HashUtils;
import by.deniotokiari.android.core.utils.StringUtils;

import java.io.Serializable;

public class Request implements Serializable {

    private String mUri;
    private String mUrl;
    private String mResultSql;
    private String mProcessor;
    private String mSource;

    private boolean mIsNeedCache;

    private long mCacheExpiration;

    private RequestListener mRequestListener;
    private ISuccess mSuccess;

    public Request(IRequestBuilder builder) {
        mUri = builder.getUri();
        mUrl = builder.getUrl();
        mResultSql = builder.getResultSql();
        mProcessor = builder.getProcessor();
        mSource = builder.getSource();
        mIsNeedCache = builder.getIsNeedCache();
        mRequestListener = builder.getRequestListener();
        mSuccess = builder.getSuccess();
        mCacheExpiration = builder.getCacheExpiration();
    }

    public String getHash() {
        return HashUtils.getMd5((getProcessor() + getResultSql() + getSource() + getUri()).getBytes());
    }

    public long getCacheExpiration() {
        return mCacheExpiration;
    }

    public ISuccess getSuccess() {
        return mSuccess;
    }

    public String getUrl() {
        return mUrl;
    }

    public boolean isIsNeedCache() {
        return mIsNeedCache;
    }

    public RequestListener getRequestListener() {
        return mRequestListener;
    }

    public String getUri() {
        return mUri;
    }

    public String getResultSql() {
        return mResultSql;
    }

    public String getProcessor() {
        return mProcessor;
    }

    public String getSource() {
        return mSource;
    }

    public static interface IRequestBuilder {

        public String getUri();

        public String getUrl();

        public String getResultSql();

        public String getProcessor();

        public String getSource();

        public boolean getIsNeedCache();

        public RequestListener getRequestListener();

        public ISuccess getSuccess();

        public long getCacheExpiration();

    }

    public static class Builder implements IRequestBuilder {

        private ISuccess mISuccess;

        private String mUri;
        private String mUrl;
        private String mResultSql;
        private String mProcessor;
        private String mSource;

        private boolean mIsNeedCache;

        private long mCacheExpiration;

        private RequestListener mRequestListener;

        public <T> Builder setSuccess(ISuccess<T> success) {
            this.mISuccess = success;
            return this;
        }

        public Builder setUri(String uri) {
            this.mUri = uri;
            return this;
        }

        public Builder setUrl(String url) {
            this.mUrl = url;
            return this;
        }

        public Builder setResultSql(String resultSql, String[] args) {
            if (args != null) {
                this.mResultSql = StringUtils.fill(resultSql, "?", args);
            } else {
                this.mResultSql = resultSql;
            }
            return this;
        }

        public Builder setProcessor(String processor) {
            this.mProcessor = processor;
            return this;
        }

        public Builder setSource(String source) {
            this.mSource = source;
            return this;
        }

        public Builder setIsNeedCache(boolean isNeedCache) {
            this.mIsNeedCache = isNeedCache;
            return this;
        }

        public Builder setCacheExpiration(long cacheExpiration) {
            this.mCacheExpiration = cacheExpiration;
            return this;
        }

        public Builder setRequestListener(RequestListener requestListener) {
            this.mRequestListener = requestListener;
            return this;
        }

        @Override
        public long getCacheExpiration() {
            return mCacheExpiration;
        }

        @Override
        public String getUri() {
            return mUri;
        }

        @Override
        public String getUrl() {
            return mUrl;
        }

        @Override
        public String getResultSql() {
            return mResultSql;
        }

        @Override
        public String getProcessor() {
            return mProcessor;
        }

        @Override
        public String getSource() {
            return mSource;
        }

        @Override
        public boolean getIsNeedCache() {
            return mIsNeedCache;
        }

        @Override
        public RequestListener getRequestListener() {
            return mRequestListener;
        }

        @Override
        public ISuccess getSuccess() {
            return mISuccess;
        }

        public Request build() {
            return new Request(this);
        }

    }

}