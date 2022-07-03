package constants;

public final class SqlQueries {

    public static final class Room {

        public static final String FIND_ALL_ROOMS = "select rooms.id, rooms.name as name, rooms.number as number, rooms.status,\n" +
                "       rooms.price as price, rooms.capacity as capacity, rct.name as class_name, rooms.class as class from rooms" +
                "    left outer join room_class rc on rooms.class = rc.id\n" +
                "    left outer join room_class_translation rct on rc.id = rct.class_id and language = ?";

        public static final String FIND_ROOM_BY_ID = "select rooms.id, rooms.name as name, rooms.number as number, rooms.status,\n" +
                "       rooms.price as price, rooms.capacity as capacity, rct.name as class_name, rooms.class as class from rooms\n" +
                "    left outer join room_class_translation rct on rooms.class = rct.class_id and language = ?\n" +
                "where rooms.id = ?";

        public static final String UPDATE_ROOM = "update rooms set number = ?, status = ?::room_status, name = ?, price = ?, capacity = ?, class = ? where id = ?";

        public static final String FIND_ALL_ROOM_CLASSES = "select class_id as id, name from room_class_translation where language = ?";

        public static final String FIND_ROOM_DATES_BY_ID = "select check_in_date, check_out_date from room_registry where room_id = ? and archived = false";

        public static final String FIND_OVERLAPPING_DATES_COUNT = "select count('*') as cnt from room_registry\n" +
                "where room_id = ? and archived = false and (daterange(?::date, ?::date, '[]') &&\n" +
                "      daterange(room_registry.check_in_date::date, room_registry.check_out_date::date, '[]') )";

        public static final String IS_ROOM_ASSIGNED_TO_ROOM_REQUEST = "select exists(select room_id from room_requests where room_id = ? and (daterange(?::date, ?::date, '[]') && daterange(check_in_date::date, check_out_date::date, '[]'))) as assigned";

        public static final String FIND_ROOM_HISTORY_BY_USER_ID = "select r.*, rct.name as class_name, room_registry.check_in_date, room_registry.check_out_date from room_registry\n" +
                "    left outer join rooms r on room_registry.room_id = r.id\n" +
                "    left outer join room_class_translation rct on r.class = rct.class_id and rct.language = ?\n" +
                "where user_id = ? order by -room_registry.id";

        public static final String FIND_SUITABLE_ROOM_FOR_REQUEST = "select rooms.id, rooms.name as name, rooms.number as number, rooms.status, rooms.price as price, rooms.capacity as capacity, rct.name as class_name, rooms.class as class from rooms\n" +
                "    left outer join room_registry rr on rooms.id = rr.room_id and archived = false\n" +
                "    left outer join room_class_translation rct on rooms.class = rct.class_id and language = ?\n" +
                "where rooms.id not in (select distinct room_id from room_requests where room_id is not null and daterange(?::date, ?::date, '[]') && daterange(room_requests.check_in_date::date, room_requests.check_out_date::date, '[]')) " +
                "and rooms.status <> 'unavailable' \n" +
                "group by rooms.id, rct.id\n" +
                "having count(room_id) filter\n" +
                "    (where daterange(?::date, ?::date, '[]') && daterange(rr.check_in_date::date, rr.check_out_date::date, '[]')) = 0";

        public static final String REMOVE_ASSIGNED_ROOM = "update room_requests set room_id = null, status = 'awaiting'\n" +
                "where room_id = ?\n" +
                "  and (daterange(?::date, ?::date, '[]') && daterange(room_requests.check_in_date::date, room_requests.check_out_date::date, '[]'))\n";

        public static final String FIND_DATA_FOR_ROOM_REGISTRY_REPORT = "select user_id, u.first_name, u.last_name, check_in_date, check_out_date, room_id from room_registry " +
                "join users u on room_registry.user_id = u.id";

        public static final String ARCHIVE_OLD_ROOM_REGISTRIES = "update room_registry set archived = true where check_out_date <= date(now())";

        public static final String UPDATE_ROOM_STATUS = "update rooms set status =\n" +
                "    case\n" +
                "        when id in (select room_id from room_registry where check_out_date = date(now()) and archived = false) then 'free'::room_status\n" +
                "        when id in (select room_id from room_registry where check_in_date = date(now()) and archived = false) then 'occupied'::room_status\n" +
                "        else 'free'::room_status\n" +
                "    end where status <> 'unavailable'";

        public static final String SET_ROOM_UNAVAILABLE = "update rooms set status = 'unavailable' where id = ?";

        public static final String REFUND_MONEY_FROM_BILLINGS = "update users set balance = balance +\n" +
                "(select sum(price) from billing\n" +
                "    join room_registry rr on billing.room_registry_id = rr.id\n" +
                "    join room_requests r on billing.request_id = r.id\n" +
                "    where paid = true and\n" +
                "    daterange(date(now()), ?::date, '[]') && daterange(rr.check_in_date::date, rr.check_out_date::date, '[]')\n" +
                "    and rr.room_id = ? and r.user_id = users.id\n" +
                ")\n" +
                "where users.id in (\n" +
                "    select user_id from room_registry\n" +
                "    inner join billing b on room_registry.id = b.room_registry_id\n" +
                "    where daterange(date(now()), ?::date, '[]') && daterange(check_in_date::date, check_out_date::date, '[]')\n" +
                "    and room_id = ?\n" +
                "    and archived = false and b.paid = true\n" +
                ")";

        public static final String REFUND_MONEY_FROM_ROOM_REGISTRY = "update users set balance = balance +\n" +
                "(\n" +
                "    select sum(r.price * extract(day from check_out_date::timestamp - check_in_date::timestamp))\n" +
                "    from room_registry\n" +
                "    join rooms r on room_registry.room_id = r.id\n" +
                "    left outer join billing b on room_registry.id = b.room_registry_id\n" +
                "    where daterange(date(now()), ?::date, '[]') && daterange(room_registry.check_in_date::date, room_registry.check_out_date::date, '[]')\n" +
                "    and room_id = ? and room_registry.user_id = users.id and b is null\n" +
                ")\n" +
                "where users.id in (\n" +
                "    select user_id from room_registry\n" +
                "    left outer join billing b on room_registry.id = b.room_registry_id\n" +
                "    where b is null and daterange(date(now()), ?::date, '[]') && daterange(check_in_date::date, check_out_date::date, '[]')\n" +
                "    and archived = false\n" +
                "    and room_id = ?\n" +
                ")";

        public static final String DELETE_REFUNDED_ROOM_REQUESTS = "delete from room_requests where id in\n" +
                "(select b.request_id from room_registry\n" +
                "    left outer join billing b on room_registry.id = b.room_registry_id\n" +
                "where daterange(date(now()), ?::date, '[]') && daterange(check_in_date::date, check_out_date::date, '[]') and room_registry.room_id = ?\n" +
                ")";

        public static final String DELETE_REFUNDED_BILLINGS = "delete from billing where id in\n" +
                "(select b.id from room_registry\n" +
                "    left outer join billing b on room_registry.id = b.room_registry_id\n" +
                "where daterange(date(now()), ?::date, '[]') && daterange(check_in_date::date, check_out_date::date, '[]') and room_registry.room_id = ?\n" +
                ")";

        public static final String DELETE_REFUNDED_ROOM_REGISTRIES = "delete from room_registry where\n" +
                "daterange(date(now()), ?::date, '[]') && daterange(check_in_date::date, check_out_date::date, '[]')\n" +
                " and room_registry.room_id = ? and archived = false";

        private Room(){}
    }

    public static final class RoomRequest {

        public static final String INSERT_ROOM_REQUEST = "insert into room_requests(user_id, capacity, room_class, check_in_date, check_out_date, comment, status, room_id, manager_comment)\n" +
                " values (?, ?, ?, ?, ?, ?, ?::request_status, ?, ?)";

        public static final String UPDATE_ROOM_REQUEST = "update room_requests set user_id = ?, capacity = ?, room_class = ?, check_in_date = ?, check_out_date = ?,\n" +
                "                         comment = ?, status = ?::request_status, room_id = nullif(?, 0), manager_comment = ? where id = ?";

        public static final String FIND_ROOM_REQUESTS_BY_USER_ID = "select room_requests.*, rct.name as class_name from room_requests\n" +
                "    left outer join room_class_translation rct on rct.class_id = room_requests.room_class and rct.language = ?\n" +
                "    where user_id = ? order by -room_requests.id";

        public static final String ADMIN_GET_ROOM_REQUESTS = "select room_requests.id, capacity, rct.name as class_name, comment, manager_comment, status, check_in_date, check_out_date, room_id, login, email, first_name, last_name from room_requests\n" +
                "    left outer join room_class_translation rct on room_requests.room_class = rct.class_id and rct.language = ?\n" +
                "    left outer join users u on room_requests.user_id = u.id\n" +
                "where room_requests.status = ?::request_status";

        public static final String ADMIN_GET_ROOM_REQUEST_BY_ID = "select room_requests.id, capacity, rct.name as class_name, comment, manager_comment, status, check_in_date, check_out_date, room_id, login, email, first_name, last_name from room_requests\n" +
                "    left outer join room_class_translation rct on room_requests.room_class = rct.class_id and rct.language = ?\n" +
                "    left outer join users u on room_requests.user_id = u.id\n" +
                "where room_requests.id = ?\n" +
                "order by room_requests.id";

        public static final String FIND_ROOM_REQUEST_BY_ID = "select room_requests.*, rct.name as class_name from room_requests\n" +
                "    left outer join room_class_translation rct on rct.class_id = room_requests.room_class and rct.language = ?\n" +
                "    where room_requests.id = ?";

        private RoomRequest(){}
    }

    public static final class User {

        public static final String FIND_USER_BY_ID = "select id, login, email, password, first_name, last_name, role, balance from users where id = ?";

        public static final String FIND_ALL_USERS = "select id, login, email, password, first_name, last_name, role, balance from users";

        public static final String INSERT_USER = "insert into users(login, email, password, first_name, last_name, balance, role) values (?, ?, ?, ?, ?, ?, ?)";

        public static final String UPDATE_USER = "update users set login = ?, email = ?, password = ?, first_name = ?, last_name = ?, role = ?, balance = ? where id = ?";

        public static final String FIND_BY_LOGIN = "select id, login, email, password, first_name, last_name, role, balance from users where login = ?";

        public static final String FIND_BY_EMAIL = "select id, login, email, password, first_name, last_name, role, balance from users where email = ?";

        public static final String FIND_BY_LOGIN_AND_PASSWORD = "select id, login, email, password, first_name, last_name, role, balance from users where login = ? and password = ?";

        public static final String FIND_USER_FOR_PASSWORD_CHANGE = "select id, login, email, password, first_name, last_name, role, balance from users where password = ? and id = ?";

        private User(){}
    }

    public static final class Billing {

        public static final String INSERT_BILLING = "insert into billing(request_id, price, pay_end_date, paid, room_registry_id) values (?, ?, default, ?, ?)";

        public static final String FIND_ALL_BILLING_BY_USER_ID = "select * from billing \n" +
                "    left outer join room_requests rr on billing.request_id = rr.id\n" +
                "where rr.user_id = ? order by -billing.id";

        public static final String UPDATE_BILLING = "update billing set request_id = ?, price = ?, pay_end_date = ?, paid = ?, room_registry_id = ? where id = ?;";

        public static final String GET_EXTENDED_BILLING_BY_ID = "select *, rr.id as request_id, rr.user_id as user_id from billing \n" +
                "    left outer join room_requests rr on billing.request_id = rr.id\n" +
                "where billing.id = ?";

        public static final String GET_BILLING_BY_ID = "select id, request_id, price, pay_end_date, paid, room_registry_id from billing where id = ?";

        public static final String DELETE_ROOM_REQUESTS_CONNECTED_TO_OLD_BILLING = "delete from room_requests where id in\n" +
                "(select request_id from billing where pay_end_date < date(now()) and paid = false)";

        public static final String DELETE_ROOM_REGISTRIES_CONNECTED_TO_OLD_BILLING = "delete from room_registry where id in\n" +
                "(select room_registry_id from billing where pay_end_date < date(now()) and paid = false)";

        public static final String DELETE_OLD_BILLINGS = "delete from billing where pay_end_date < date(now()) and paid = false";

        private Billing(){}
    }

    public static final class RoomRegistry{

        public static final String GET_ROOM_REGISTRY_BY_ID = "select id, user_id, room_id, check_in_date, check_out_date, archived from room_registry where id = ?";

        public static final String CREATE_ROOM_REGISTRY = "insert into room_registry(user_id, room_id, check_in_date, check_out_date, archived) values (?, ?, ?, ?, ?)";

        public static final String UPDATE_ROOM_REGISTRY = "update room_registry set user_id = ?, room_id = ?, check_in_date = ?, check_out_date = ?, archived = ? where id = ?";

        private RoomRegistry(){

        }
    }

    private SqlQueries(){

    }
}
