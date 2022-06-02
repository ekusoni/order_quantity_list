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
    E_MATM_NAME_EXIST("入力された材料名は既に存在しています");


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
