public enum Table {
    CLIENTS("id", "first_name", "last_name", "birth_date", null),
    INTERNET_PLANS("id", "MB", "internet_speed_per_second", "description", null),
    MOBILE_INTERNET_PLANS("id", "MB", "internet_speed_per_second", "description", "connection_type_id"),
    PLANS("id", "duration_days", "price", "company_id", null),
    SOLD_PLANS("id", "client_id", "plan_id", "expired", "price"),
    TELECOMMUNICATION_COMPANIES("id", "name", "year_founded", "description", "founder"),
    TV_PLANS("id", "duration", "channels", "password", null);

    String column_1, column_2, column_3, column_4, column_5;

    Table(String column_1, String column_2, String column_3, String column_4, String column_5) {
        this.column_1 = column_1;
        this.column_2 = column_2;
        this.column_3 = column_3;
        this.column_4 = column_4;
        this.column_5 = column_5;
    }
}
