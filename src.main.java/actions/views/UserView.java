package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * 利用者情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter//全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor
@AllArgsConstructor
public class UserView {

    /**
     * id
     */
    private Integer id;

    /**
     * ユーザー番号
     */
    private String code;

    /**
     * 氏名
     */
    private String name;

    /**
     * パスワード
     */
    private String password;

    /**
     * 作成者権限があるかどうか(閲覧者:0,作成者:1)
     */
    private Integer authorFlag;

    /**
     * 削除された利用者かどうか(現役:0,削除済み:1)
     */
    private Integer deleteFlag;


}