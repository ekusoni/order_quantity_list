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
 * 料理テーブルのDTOモデル
 *
 *
 */
@Table(name=JpaConst.TABLE_COO)
@NamedQueries({
    @NamedQuery(
            name=JpaConst.Q_COO_GET_ALL,
            query=JpaConst.Q_COO_GET_ALL_DEF),
    @NamedQuery(
            name=JpaConst.Q_COO_COUNT,
            query=JpaConst.Q_COO_COUNT_DEF),
    @NamedQuery(
            name=JpaConst.Q_COO_COUNT_RESISTERED_BY_NAME,
            query=JpaConst.Q_COO_COUNT_RESISTERED_BY_NAME_DEF),
    @NamedQuery(
            name=JpaConst.Q_COO_GET_BY_NAME,
            query=JpaConst.Q_COO_GET_BY_NAME_DEF)

})

@Getter//全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter//全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor//引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor//全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Cooking {


    /**
     * id
     */
    @Id
    @Column(name=JpaConst.COO_COL_ID)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * 料理名
     */
    @Column(name=JpaConst.COO_COL_NAME)
    private String name;


}
