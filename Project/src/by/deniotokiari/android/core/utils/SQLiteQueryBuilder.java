package by.deniotokiari.android.core.utils;

import java.util.ArrayList;
import java.util.List;

public class SQLiteQueryBuilder {

    private String sql;

    private List<String> select = new ArrayList<String>();
    private List<String> from = new ArrayList<String>();
    private List<String> where = new ArrayList<String>();

    private String limit;

    public String build() {
        return "";
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

    public SQLiteQueryBuilder where(String expression) {
        return this;
    }

}
