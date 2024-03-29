package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 材料情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter//全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter//全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class MaterialView {

    /**
     * id
     */
    private Integer id;

    /**
     * 量を指定する材料のid
     */
    private MaterialMasterView materialMaster;

    /**
     * 材料を使用する料理のid
     */
    private CookingView cooking;

    /**
     * 材料の量
     */
    private Integer amount;



}
