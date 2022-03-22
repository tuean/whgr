package com.tuean.whgr.func.majiang;

public enum MjCard {


    WAN_1       ("w1", "一万", 4),
    WAN_2       ("w2", "二万", 4),
    WAN_3       ("w3", "三万", 4),
    WAN_4       ("w4", "四万", 4),
    WAN_5       ("w5", "五万", 4),
    WAN_6       ("w6", "六万", 4),
    WAN_7       ("w7", "七万", 4),
    WAN_8       ("w8", "八万", 4),
    WAN_9       ("w9", "九万", 4),

    TIAO_1      ("t1", "一条", 4),
    TIAO_2      ("t2", "二条", 4),
    TIAO_3      ("t3", "三条", 4),
    TIAO_4      ("t4", "四条", 4),
    TIAO_5      ("t5", "五条", 4),
    TIAO_6      ("t6", "六条", 4),
    TIAO_7      ("t7", "七条", 4),
    TIAO_8      ("t8", "八条", 4),
    TIAO_9      ("t9", "九条", 4),

    TONG_1      ("d1", "一桶", 4),
    TONG_2      ("d2", "二桶", 4),
    TONG_3      ("d3", "三桶", 4),
    TONG_4      ("d4", "四桶", 4),
    TONG_5      ("d5", "五桶", 4),
    TONG_6      ("d6", "六桶", 4),
    TONG_7      ("d7", "七桶", 4),
    TONG_8      ("d8", "八桶", 4),
    TONG_9      ("d9", "九桶", 4),

    F_DONG      ("f1", "东风", 4),
    F_NAN       ("f2", "南风", 4),
    F_XI        ("f3", "西风", 4),
    F_BEI       ("f4", "北风", 4),
    F_ZHONG     ("f5", "红中", 4),
    F_FA        ("f6", "发财", 4),
    F_BAI       ("f7", "白板", 4),

    H_MEI       ("h1", "梅", 1),
    H_LAN       ("h2", "兰", 1),
    H_ZHU       ("h3", "竹", 1),
    H_JU        ("h4", "菊", 1),
    H_CHUN      ("h5", "春", 1),
    H_XIA       ("h6", "夏", 1),
    H_QIU       ("h7", "秋", 1),
    H_DONG      ("h8", "冬", 1),

    BAIDA       ("b0", "百搭", 4),
    CHUNBAI     ("c0", "纯白", 4)


    ;

    MjCard(String code, String value, Integer number) {
        this.code = code;
        this.value = value;
        this.number = number;
    }

    private String code;

    private String value;

    private Integer number;


    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public Integer getNumber() {
        return number;
    }
}
