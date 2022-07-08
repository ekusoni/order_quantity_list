package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 料理(量)情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter//全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter//全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class CookingSlaveView {

    /**
     * id
     */
    private Integer id;

    /**
     * メニューのid
     */
    private MenuView menu;

    /**
     * メニューで使用する料理のid
     */
    private CookingView cooking;

    /**
     * 料理の量
     */
    private Integer amount;

    /**
     * 時間帯
     */
    private String timeZone;

}