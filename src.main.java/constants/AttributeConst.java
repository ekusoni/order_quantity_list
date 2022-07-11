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

    //登録途中の料理
    DURING_REGISTRATION("d_registration"),

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

    //材料(材料名、単位)管理
    MATERIALM("materialMaster"),
    MATERIALMS("materialMasters"),
    MATERIALMSS("materialMasters_select"),
    MATM_COUNT("materialMasters_count"),
    MATM_ID("id"),
    MATM_NAME("name"),
    MATM_UNIT("unit"),
    MAT_COL_MATERIALMASTER_ID("materialMaster_id"),
    SEARCHMATERIALMS("searchMaterialMasters"),
    MATM_WORD("word"),

    //材料(量)管理
    MAT_ID("id"),
    MAT_AMOUNT("materialAmount"),
    MATERIALS("materials"),
    MAT_TENTATIVE("materialTentative"),
    MAT_TENTATIVE_LENGTH("tentativeLength"),
    SEARCH_MATERIALS("searchMaterials"),

    //料理管理
    COOKING("cooking"),
    COOKINGS("cookings"),
    COO_COUNT("cookings_count"),
    COO_ID("id"),
    COO_NAME("name"),
    SEARCH_COOKINGS("searchCookings"),
    COO_WORD("word"),

    //料理(量)管理
    COOKINGSL("cookingSlave"),
    COOKINGSLS("cookingSlaves"),
    COS_ID("id"),
    COOKINGSL_AMOUNT("cookingSlave_amount"),
    COOKINGSL_TIMEZONE("cookingSlave_timeZone"),
    COS_COL_MORNING_COOKING_ID("morning_cooking_id"),
    COS_COL_NOON_COOKING_ID("noon_cooking_id"),
    COS_COL_EVENING_COOKING_ID("evening_cooking_id"),
    COS_COL_COOKING_ID("cooking_id"),
    COS_TENTATIVE("cookingSlaveTentative"),
    COS_TENTATIVE_LENGTH("tentativeLength"),



    //メニュー管理
    MENU("menu"),
    MENUS("menus"),
    MEN_ID("id"),
    MEN_COUNT("menus_count"),
    MENU_DATE_START("start_date"),
    MENU_DATE_END("end_date"),
    MENU_MORNING_AMOUNT("morning_amount"),
    MENU_NOON_AMOUNT("noon_amount"),
    MENU_EVENING_AMOUNT("evening_amount");







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
