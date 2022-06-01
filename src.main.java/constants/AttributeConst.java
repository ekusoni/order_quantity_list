package constants;

/**
 * 画面の項目値等を定義するEnumクラス
 *
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面
    MAX_ROW("maxRow"),
    PAGE("page"),



    //入力ファーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中の利用者
    LOGIN_USE("login_user"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //利用者管理
    USER("user"),
    USERS("users"),
    USE_COUNT("users_count"),
    USE_ID("id"),
    USE_CODE("code"),
    USE_PASS("password"),
    USE_NAME("name"),
    USE_AUTHOR_FLAG("author_flag"),

    //作成者フラグ
    ROLE_AUTHOR(1),
    ROLE_VIEWER(0),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //材料管理
    MATERIAL("material"),
    MATERIALS("materials"),
    MAT_COUNT("materials_count"),
    MAT_ID("id"),
    MAT_NAME("name"),
    MAT_UNIT("unit"),

    //料理管理
    COOKING("cooking"),
    COOKINGS("cookings"),
    COO_ID("id"),
    COO_NAME("name"),

    //メニュー管理
    MENU("menu"),
    MENUS("menus"),
    MEN_ID("id"),
    MENUDATE_START("menudate_strat"),
    MUNUDATE_END("menudate_end");






    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text=text;
        this.i=null;
    }


    private AttributeConst(final Integer i) {
        this.text=null;
        this.i=i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}
