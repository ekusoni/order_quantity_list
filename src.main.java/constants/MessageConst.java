package constants;

public enum MessageConst {

    //認証
    I_LOGINED("ログインしました。"),
    E_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),


    //バリテーション
    E_NONAME("氏名を入力してください。"),
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOUSE_CODE("ユーザー番号を入力してください。"),
    E_USE_CODE_EXIST("入力されたユーザー番号の情報は既に存在しています。"),
    E_MATM_NONAME("材料名を入力してください。"),
    E_MATM_NOUNIT("単位を入力してください"),
    E_MATM_NAME_EXIST("入力された材料名は既に存在しています"),
    E_MAT_NOAMOUNT("正常な値を入力してください。"),
    E_COO_NONAME("料理名を入力してください。"),
    E_COO_NAME_EXIST("入力された料理名は既に存在しています"),
    E_MENU_NOMORNING("「朝の料理の種類」欄には0以上の数値を入力してください"),
    E_MENU_NONOON("「昼の料理の種類」欄には0以上の数値を入力してください"),
    E_MENU_EVENING("「夕の料理の種類」欄には0以上の数値を入力してください"),
    E_MENU_NOSTART("開始日を入力してください"),
    E_MENU_NOEND("終了日を入力してください"),
    E_MENU_NODATE("開始日が終了日を過ぎています");

    /**
     *文字列
     */
    private final String text;


    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text=text;
    }

    /*
     * 値(文字列)取得
     */
    public String getMessage() {
        return this.text;
    }
}
