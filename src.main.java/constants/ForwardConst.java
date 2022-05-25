package constants;



/**
 * リクエストパラメータの変数名、変数値、jspファイルの名前等画面に関わる値を定義するEnumクラス
 * @author nyz54
 *
 */
public enum ForwardConst {
    //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_USER("User"),
    ACT_MAT("Material"),
    ACT_COO("Cooking"),
    ACT_MENU("Menu"),
    ACT_AUCH("Auch"),

    //command
    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_DESTROY("destroy"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("topPage/index"),
    FW_USE_INDEX("users/index"),
    FW_USE_NEW("users/new"),
    FW_USE_SHOW("users/show"),
    FW_MAT_NEW("materials/new"),
    FW_COO_NEW("cooking/new"),
    FW_COO_INDEX("cooking/edit"),
    FW_MEN_SELECT("menus/select"),
    FW_MEN_NEW("menus/new"),
    FW_MEN_INDEX("menus/index"),
    FW_MEE_SHOW("menus/show");

    private final String text;


    /**
     * コンストラクタ
     */
    private ForwardConst(final String text) {
        this.text=text;
    }

    /**
     * 値(文字列)取得
     */
    public String getValue() {
        return this.text;
    }



}
