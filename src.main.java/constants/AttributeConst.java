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

    //ログイン中の従業員
    LOGIN_EMP("login_employee"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //利用者管理
    USER("user"),
    USERS("users"),
    USER_COUNT("user_count"),
    USER_ID("id"),
    USER_CODE("code"),
    USER_PASS("password"),
    USER_NAME("name"),
    USER_ADMIN_FLAG("admin_flag"),

    //作成者フラグ
    ROLE_AUTHOR(1),
    ROLE_VIEWER(0),

    //削除フラグ
    DEL_FLAG_TURE(1),
    DEL_FLAG_FALSE(0),

    //材料管理
    MATERIAL("material"),
    MATERIALS("materials"),
    MATERIAL_ID("id"),

    //料理管理
    COOKING("cooking"),
    COOKINGS("cookings"),
    COOKING_ID("id"),
    COOKING_NAME("name"),

    //メニュー管理
    MENU("menu"),
    MENUS("menus"),
    MENUS_ID("id"),
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
