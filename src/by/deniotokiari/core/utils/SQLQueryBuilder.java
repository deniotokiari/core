package by.deniotokiari.core.utils;

public class SQLQueryBuilder {
	
	private String sql;

	private static final String templateSelect = "SELECT %s";
	private static final String templateFrom = "FROM %s";
	private static final String templateJoin = "%s JOIN %s ON %s";
	private static final String templateWhere = "WHERE %s";
	private static final String templateGroupBy = "GROUP BY %s";
	private static final String templateHaving = "HAVING %s";
	private static final String templateOrderBy = "ORDER BY %s";
	private static final String templateLimit = "LIMIT %s";

	private String select;
	private String from;
	private String join;
	private String where;
	private String groupBy;
	private String having;
	private String orderBy;
	private String limit;

	private String selectTitle;

	public SQLQueryBuilder() {
		sql = "";
	}

	private void build() {
		sql = "";
		if (select != null) {
			sql += select;
		}
		if (from != null) {
			sql += " " + from;
		}
		if (join != null) {
			sql += " " + join;
		}
		if (where != null) {
			sql += " " + where;
		}
		if (groupBy != null) {
			sql += " " + groupBy;
		}
		if (having != null) {
			sql += " " + having;
		}
		if (orderBy != null) {
			sql += " " + orderBy;
		}
		if (limit != null) {
			sql += " " + limit;
		}
	}

	private String getAsString(Object... objects) {
		String result = "";
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof String) {
				result += (String) objects[i] + ", ";
			} else if (objects[i] instanceof SQLQueryBuilder) {
				result += "(" + ((SQLQueryBuilder) objects[i]);
				if (((SQLQueryBuilder) objects[i]).selectTitle != null) {
					result += ") " + ((SQLQueryBuilder) objects[i]).selectTitle + ", ";
				} else {
					result += ", ";
				}
			}
		}
		result = result.substring(0, result.length() - 2);
		return result;
	}

	public SQLQueryBuilder select(String selectTitle, Object... columns) {
		select = String.format(templateSelect, getAsString(columns));
		this.selectTitle = selectTitle;
		return this;
	}

	public SQLQueryBuilder from(Object... from) {
		this.from = String.format(templateFrom, getAsString(from));
		return this;
	}

	public SQLQueryBuilder join(String type, String table, String condition) {
		join = String.format(templateJoin, type, table, condition);
		return this;
	}

	public SQLQueryBuilder where(Object selections) {
		where = String.format(templateWhere, selections);
		return this;
	}

	public SQLQueryBuilder groupBy(Object groupBy) {
		this.groupBy = String.format(templateGroupBy, groupBy);
		return this;
	}

	public SQLQueryBuilder having(Object having) {
		this.having = String.format(templateHaving, having);
		return this;
	}

	public SQLQueryBuilder orderBy(Object... ordersBy) {
		if (ordersBy == null) {
			return this;
		}
		orderBy = String.format(templateOrderBy, getAsString(ordersBy));
		return this;
	}
	
	public SQLQueryBuilder limit(long limit) {
		this.limit = String.format(templateLimit, String.valueOf(limit));
		return this;
	}

	public String getSql() {
		return toString();
	}

	@Override
	public String toString() {
		build();
		return sql;
	}
}
