package by.deniotokiari.core.source;

import java.io.Serializable;
import java.util.ArrayList;

import by.deniotokiari.core.receiver.SourceResultReceiver;
import by.deniotokiari.core.receiver.SourceResultReceiver.STATUS;
import by.deniotokiari.core.utils.AppUtils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;

public class Request<Source, Result> {

	public static final String KEY_REQUEST = "request:request";
	
	public static final String FROM_URI = "request:fromUri";
	/** -------- **/
	public static final String KEY_URI = "request:uri";
	public static final String KEY_SOURCE_KEY = "request:sourceKey";
	public static final String KEY_PROCESSOR_KEY = "request:processorKey";

	public static final String FROM_ENTITY = "request:fromEntity";
	/** -------- **/
	public static final String KEY_ENTITY = "rquest:entity";
	public static final String KEY_ENTITY_TYPE = "request:entityType";

	public static final String KEY_IS_NEED_CACHE = "request:isNeedCache";

	public static enum ENTITY_TYPE {

		URI, PARCELABLE, PARCELABLE_ARRAY, PARCELABLE_ARRAY_LIST;

	}

	private Bundle mBundle;
	
	public Request(Intent intent) {
		mBundle = intent.getParcelableExtra(KEY_REQUEST);
	}

	public Request(Source source, String sourceKey, String processorKey,
			boolean isNeedCache) {
		setArgsToBundle(source, sourceKey, processorKey, isNeedCache);
	}

	public void setBundleToInten(Intent intent) {
		intent.putExtra(KEY_REQUEST, mBundle);
	}
	
	@SuppressWarnings("unchecked")
	protected void setArgsToBundle(Source source, String sourceKey,
			String processorKey, boolean isNeedCache) {
		mBundle = new Bundle();

		if (source instanceof Parcelable) {
			mBundle.putParcelable(KEY_ENTITY, (Parcelable) source);
			mBundle.putInt(KEY_ENTITY_TYPE, ENTITY_TYPE.PARCELABLE.ordinal());
		} else if (source instanceof Parcelable[]) {
			mBundle.putParcelableArray(KEY_ENTITY, (Parcelable[]) source);
			mBundle.putInt(KEY_ENTITY_TYPE,
					ENTITY_TYPE.PARCELABLE_ARRAY.ordinal());
		} else if (source instanceof ArrayList<?>) {
			mBundle.putParcelableArrayList(KEY_ENTITY,
					(ArrayList<? extends Parcelable>) source);
			mBundle.putInt(KEY_ENTITY_TYPE,
					ENTITY_TYPE.PARCELABLE_ARRAY_LIST.ordinal());
		} else if (source instanceof String) {
			mBundle.putString(KEY_URI, (String) source);
			mBundle.putInt(KEY_ENTITY_TYPE, ENTITY_TYPE.URI.ordinal());
		}

		mBundle.putString(KEY_PROCESSOR_KEY, processorKey);
		mBundle.putBoolean(KEY_IS_NEED_CACHE, isNeedCache);
		mBundle.putString(KEY_SOURCE_KEY, sourceKey);
	}

	protected String getUri() {
		return mBundle.getString(KEY_URI);
	}

	protected String getSourceKey() {
		return mBundle.getString(KEY_SOURCE_KEY);
	}

	protected String getProcessorKey() {
		return mBundle.getString(KEY_PROCESSOR_KEY);
	}

	@SuppressWarnings("unchecked")
	protected Source getEntyti() {
		switch (getEntityType()) {
		case PARCELABLE:
			return mBundle.getParcelable(KEY_ENTITY);
		case PARCELABLE_ARRAY:
			return (Source) mBundle.getParcelableArray(KEY_ENTITY);
		case PARCELABLE_ARRAY_LIST:
			return (Source) mBundle.getParcelableArrayList(KEY_ENTITY);
		default:
			return null;
		}
	}

	protected boolean isNeedCache() {
		return mBundle.getBoolean(KEY_IS_NEED_CACHE);
	}

	protected ENTITY_TYPE getEntityType() {
		return ENTITY_TYPE.values()[mBundle.getInt(KEY_ENTITY_TYPE)];
	}

	protected void sendStatus(SourceResultReceiver.STATUS status,
			ResultReceiver resultReceiver, Bundle bundle) {
		if (resultReceiver != null) {
			resultReceiver.send(status.ordinal(), bundle);
		}
	}

	@SuppressWarnings("unchecked")
	public void executeRequest(Context context, ResultReceiver resultReceiver) {
		sendStatus(STATUS.START, resultReceiver, mBundle);
		try {
			Source source = null;
			if (getEntityType() == ENTITY_TYPE.URI) {
				ISource<Source> dataSource = (ISource<Source>) AppUtils
						.get(context, getSourceKey());
				try {
					source = dataSource.getSource(getUri());
				} catch (Exception e) {
					mBundle.putSerializable(SourceResultReceiver.ERROR_KEY, e);
					sendStatus(STATUS.ERROR, resultReceiver, mBundle);
				}
				if (source == null) {
					mBundle.putSerializable(SourceResultReceiver.ERROR_KEY,
							new NullPointerException(
									"Result from data source is null"));
					sendStatus(STATUS.ERROR, resultReceiver, mBundle);
					return;
				}
			} else {
				source = getEntyti();
			}
			IProcessor<Source, Result> processor = (IProcessor<Source, Result>) AppUtils
					.get(context, getProcessorKey());
			if (processor != null) {
				Result result = processor.process(source);
				if (result == null) {
					mBundle.putSerializable(SourceResultReceiver.ERROR_KEY,
							new NullPointerException(
									"Result from data processor is null"));
					sendStatus(STATUS.ERROR, resultReceiver, mBundle);
					return;
				}
				if (isNeedCache()) {
					boolean isCached = false;
					isCached = processor.cache(result, context);
					if (isCached) {
						sendStatus(STATUS.CACHED, resultReceiver, mBundle);
					} else {
						mBundle.putSerializable(SourceResultReceiver.ERROR_KEY,
								new Exception("Can't cache result"));
						sendStatus(STATUS.ERROR, resultReceiver, mBundle);
						return;
					}
				} else {
					if (result instanceof Parcelable) {
						mBundle.putParcelable(SourceResultReceiver.RESULT_KEY,
								(Parcelable) result);
					} else if (result instanceof Parcelable[]) {
						mBundle.putParcelableArray(
								SourceResultReceiver.RESULT_KEY,
								(Parcelable[]) result);
					} else if (result instanceof ArrayList<?>) {
						mBundle.putParcelableArrayList(
								SourceResultReceiver.RESULT_KEY,
								(ArrayList<? extends Parcelable>) result);
					} else if (result instanceof Serializable) {
						mBundle.putSerializable(
								SourceResultReceiver.RESULT_KEY,
								(Serializable) result);
					} else if (result instanceof String) {
						mBundle.putString(SourceResultReceiver.RESULT_KEY,
								(String) result);
					}
				}
			}
			sendStatus(STATUS.DONE, resultReceiver, mBundle);
		} catch (Exception e) {
			e.printStackTrace();
			mBundle.putSerializable(SourceResultReceiver.ERROR_KEY, e);
			sendStatus(STATUS.ERROR, resultReceiver, mBundle);
		}
	}
}
