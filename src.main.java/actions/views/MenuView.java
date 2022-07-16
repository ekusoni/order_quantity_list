package actions.views;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * メニューについて画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class MenuView {

    /**
     * id
     */
    private Integer id;

    /**
     * メニューの開始日
     */
    private LocalDate startDate;

    /**
     * メニューの終了日
     */
    private LocalDate endDate;

    /**
     * メニューのトップ画面選定
     */
    private String topDisplay;

}
