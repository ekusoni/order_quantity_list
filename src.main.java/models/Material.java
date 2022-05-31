package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 材料データのDTOモデル
 *
 *
 */
@Table(name=JpaConst.TABLE_MAT)
@NamedQueries({
    @NamedQuery(
            name=JpaConst.Q_MAT_GET_ALL,
            query=JpaConst.Q_MAT_GET_ALL_DEF)
})

@Getter//全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter//全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する
@Entity
public class Material {

    /**
     * id
     */
    @Id
    @Column(name=JpaConst.MAT_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * 材料名
     */
    @Column(name=JpaConst.MAT_COL_NAME)
    private String name;

    /**
     * 単位
     */
    @Column(name=JpaConst.MAT_COL_UNIT,nullable=false)
    private String unit;



}
