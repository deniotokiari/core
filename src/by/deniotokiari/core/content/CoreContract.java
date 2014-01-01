package by.deniotokiari.core.content;

import by.deniotokiari.core.annotations.db.DBAutoincrement;
import by.deniotokiari.core.annotations.db.DBPrimaryKey;
import by.deniotokiari.core.annotations.db.types.DBInteger;

import java.util.HashMap;

public interface CoreContract {
	
	@DBAutoincrement
	@DBInteger
	@DBPrimaryKey
	public static final String _ID = "_id";
	
}
