package by.deniotokiari.core.content;

import by.deniotokiari.core.annotations.db.DBAutoincrement;
import by.deniotokiari.core.annotations.db.DBPrimaryKey;
import by.deniotokiari.core.annotations.db.types.DBLong;

public interface CoreContract {
	
	@DBAutoincrement
	@DBLong
	@DBPrimaryKey
	public static final String _ID = "_id";
	
}
