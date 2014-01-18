package by.deniotokiari.android.core.utils;

import java.util.ArrayList;
import java.util.List;

public class SQLiteQueryBuilder {

    private String sql = "";

    private List<String> select = new ArrayList<String>();
    private List<String> from = new ArrayList<String>();
    private List<String> where = new ArrayList<String>();
    private List<String> orderBy = new ArrayList<String>();

    private String limit;

    public String build() {
        sql += StringUtils.join(select, ", ");

        if (from.size() > 0) {
            sql += " " + StringUtils.join(from, ", ");
        }
        if (where.size() > 0) {
            sql += " " + StringUtils.join(where, " ");
        }
        if (orderBy.size() > 0) {
            sql += " " + StringUtils.join(orderBy, ", ");
        }
        if (limit != null) {
            sql += " " + limit;
        }

        return sql;
    }

    public SQLiteQueryBuilder limit(String count) {
        limit = "LIMIT " + count;
        return this;
    }

    public SQLiteQueryBuilder select(String row) {
        return select(row, null);
    }

    public SQLiteQueryBuilder select(String row, String as) {
        String result = "";

        if (select.size() == 0) {
            result = "SELECT ";
        }

        if (as == null) {
            result += row;
        } else {
            result += row + " AS " + as;
        }

        select.add(result);

        return this;
    }

    public SQLiteQueryBuilder from(String table) {
        return from(table, null);
    }

    public SQLiteQueryBuilder from(String table, String as) {
        String result = "";

        if (from.size() == 0) {
            result = "FROM ";
        }

        if (as == null) {
            result += table;
        } else {
            result += table + " AS " + as;
        }

        from.add(result);

        return this;
    }

    public SQLiteQueryBuilder where(String expression, String... args) {
        String result = "";

        if (where.size() == 0) {
            result = "WHERE ";
        }

        if (args == null) {
            result += expression;
        } else {
            result += "(" + expression + ")";
            for (String arg : args) {
                result = result.replaceFirst("\\?", arg);
            }
        }

        where.add(result);

        return this;
    }

    public SQLiteQueryBuilder in(String... args) {
        String result = "IN";

        result += " (" + StringUtils.join(args, ", ") + ")";

        where.add(result);

        return this;
    }

    public SQLiteQueryBuilder and() {
        where.add("AND");

        return this;
    }

    public SQLiteQueryBuilder or() {
        where.add("OR");

        return this;
    }

    public SQLiteQueryBuilder orderBy(String column, String order) {
        String result = "";

        if (orderBy.size() == 0) {
            result = "ORDER BY ";
        }

        if (order == null) {
            result += column;
        } else {
            result += column + " " + order;
        }

        orderBy.add(result);

        return this;
    }

}
