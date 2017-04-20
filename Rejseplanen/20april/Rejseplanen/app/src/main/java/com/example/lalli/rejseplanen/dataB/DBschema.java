package com.example.lalli.rejseplanen.dataB;

/**
 * Created by Lalli on 24-03-2017.
 */

public class DBschema {

    public static final class UserTable {
        public static final String NAME = "users";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String FIRSTNAME = "firstname";
            public static final String LASTNAME = "lastname";
            public static final String mobile = "mobile";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String CreditCardNR = "creditcardnr";
            public static final String CRC = "crc";

            public static final String CREDIT = "credit";
        }

    }

    public static final class BankTable {
        public static final String NAME = "bank";

        public static final class Cols {
            public static final String CreditCardNR = "creditcardnr";
            public static final String CRC          = "crc";
            public static final String FIRSTNAME    = "firstname";
            public static final String LASTNAME     = "lastname";
            public static final String BALANCE      = "balance";
        }

    }




}
