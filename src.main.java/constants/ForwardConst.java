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
    ACT_USE("User"),
    ACT_MATM("MaterialMaster"),
    ACT_COO("Cooking"),
    ACT_MENU("Menu"),
    ACT_AUTH("Auth"),

    //command
    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),
    CMD_NEXT("next"),
    CMD_ADDITION("addition"),
    CMD_INCREASE("increase"),
    CMD_GAIN("gain"),
    CMD_SEARCH("search"),
    CMD_TOP("top"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("topPage/index"),
    FW_USE_INDEX("users/index"),
    FW_USE_NEW("users/new"),
    FW_USE_SHOW("users/show"),
    FW_USE_EDIT("users/edit"),
    FW_MATM_INDEX("materialMasters/index"),
    FW_MATM_NEW("materialMasters/new"),
    FW_MATM_SHOW("materialMasters/show"),
    FW_MATM_EDIT("materialMasters/edit"),
    FW_COO_NEW("cookings/new"),
    FW_COO_INDEX("cookings/index"),
    FW_COO_NEXT("cookings/next"),
    FW_COO_SHOW("cookings/show"),
    FW_COO_EDIT("cookings/edit"),
    FW_COO_ADD("cookings/addition"),
    FW_COO_INC("cookings/increase"),
    FW_MEN_NEW("menus/new"),
    FW_MEN_INDEX("menus/index"),
    FW_MEN_SHOW("menus/show"),
    FW_MEN_NEXT("menus/next"),
    FW_MEN_EDIT("menus/edit"),
    FW_MEN_ADD("menus/addition"),


    FW_LOGIN("login/login");


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
