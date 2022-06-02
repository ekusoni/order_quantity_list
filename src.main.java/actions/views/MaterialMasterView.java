package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter//全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter//全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class MaterialMasterView {

    /**
     * id
     */
    private Integer id;

    /**
     * 材料名
     */
    private String name;

    /**
     * 単位
     */
    private String unit;

}
